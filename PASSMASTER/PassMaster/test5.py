from __future__ import print_function, division
import numpy as np
import cv2
import pyaudio
import wave
import threading
import time
import subprocess
import os
import urllib3
import json
import base64

class VideoRecorder():
    "Video class based on openCV"
    def __init__(self, name="temp_video.avi", fourcc="MJPG", sizex=640, sizey=480, camindex=0, fps=30):
        self.video = cv2.VideoCapture(0)
        self.open = True
        self.device_index = camindex
        self.fps = fps                  # fps should be the minimum constant rate at which the camera can
        self.fourcc = fourcc            # capture images (with no decrease in speed over time; testing is required)
        self.frameSize = (sizex, sizey) # video formats and sizes also depend and vary according to the camera used
        self.video_filename = name
        self.video_cap = cv2.VideoCapture(self.device_index)
        self.video_writer = cv2.VideoWriter_fourcc(*self.fourcc)
        self.video_out = cv2.VideoWriter(self.video_filename, self.video_writer, self.fps, self.frameSize)
        self.frame_counts = 1
        self.start_time = time.time()

        self.face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
        self.eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml')
        detector_params = cv2.SimpleBlobDetector_Params()
        detector_params.filterByArea = True
        detector_params.maxArea = 1500
        self.detector = cv2.SimpleBlobDetector_create(detector_params)
        self.left_eye_x = [];  # r
        self.left_eye_y = [];  # r
        self.right_eye_x = [];  # r
        self.right_eye_y = [];  # r

    def record(self):
        "Video starts being recorded"
        # counter = 1
        timer_start = time.time()
        timer_current = 0
        while self.open:
            ret, video_frame = self.video_cap.read()
            if ret:
                self.video_out.write(video_frame)

                face_frame = self.detect_faces(video_frame, self.face_cascade)
                if face_frame is not None:
                    self.video_out.write(video_frame)
                    eyes = self.detect_eyes(face_frame, self.eye_cascade)

                    if eyes[0] is not None:
                        eye = eyes[0]
                        threshold = r = 50
                        eye = self.cut_eyebrows(eye)
                        keypoints = self.blob_process(eye, threshold, self.detector)
                        for keypoint in keypoints:
                            self.left_eye_x.append(keypoint.pt[0])
                            self.left_eye_y.append(keypoint.pt[1])

                    if eyes[1] is not None:
                        eye = eyes[1]
                        threshold = r = 50
                        eye = self.cut_eyebrows(eye)
                        keypoints = self.blob_process(eye, threshold, self.detector)
                        for keypoint in keypoints:
                            self.right_eye_x.append(keypoint.pt[0])
                            self.right_eye_y.append(keypoint.pt[1])

                cv2.imshow('frame', video_frame)
                # print(str(counter) + " " + str(self.frame_counts) + " frames written " + str(timer_current))
                self.frame_counts += 1
                # counter += 1
                # timer_current = time.time() - timer_start
                time.sleep(1/self.fps)
                gray = cv2.cvtColor(video_frame, cv2.COLOR_BGR2GRAY)
                cv2.waitKey(1)


            else:
                break

    #############################
    def detect_faces(self, img, cascade):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        coords = cascade.detectMultiScale(gray_frame, 1.3, 5)
        if len(coords) > 1:
            biggest = (0, 0, 0, 0)
            for i in coords:
                if i[3] > biggest[3]:
                    biggest = i
            biggest = np.array([i], np.int32)
        elif len(coords) == 1:
            biggest = coords
        else:
            return None
        for (x, y, w, h) in biggest:
            frame = img[y:y + h, x:x + w]
        return frame

    def detect_eyes(self, img, cascade):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        eyes = cascade.detectMultiScale(gray_frame, 1.3, 5)  # detect eyes
        width = np.size(img, 1)  # get face frame width
        height = np.size(img, 0)  # get face frame height
        left_eye = None
        right_eye = None
        for (x, y, w, h) in eyes:
            if y > height / 2:
                pass
            eyecenter = x + w / 2  # get the eye center
            if eyecenter < width * 0.5:
                left_eye = img[y:y + h, x:x + w]
            else:
                right_eye = img[y:y + h, x:x + w]
        return left_eye, right_eye

    def cut_eyebrows(self, img):
        height, width = img.shape[:2]
        eyebrow_h = int(height / 4)
        img = img[eyebrow_h:height, 0:width]  # cut eyebrows out (15 px)

        return img

    def blob_process(self, img, threshold, detector):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        _, img = cv2.threshold(gray_frame, threshold, 255, cv2.THRESH_BINARY)
        img = cv2.erode(img, None, iterations=2)
        img = cv2.dilate(img, None, iterations=4)
        img = cv2.medianBlur(img, 5)
        keypoints = detector.detect(img)

        return keypoints

    def nothing(self, x):
        pass

    def distancetest(self):
        cap = cv2.VideoCapture(0)  # 실시간

        while 1:
            ret, img = cap.read()
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            faces = self.face_cascade.detectMultiScale(gray, 1.3, 5)

            for (x, y, w, h) in faces:
                if (w < 140):
                    print('더 가까이 와야함')
                else:
                    print('가능')
                    return;

            cv2.imshow('', img)

    def getlist(self):
        return

    def clearlist(self):
        self.right_eye_y=[]
        self.right_eye_x=[]
        self.left_eye_y=[]
        self.left_eye_x=[]

        #####################################
    def stop(self):
        "Finishes the video recording therefore the thread too"
        if self.open:
            self.open=False
            self.video_out.release()
            self.video_cap.release()
            cv2.destroyAllWindows()

    def start(self):
        "Launches the video recording function using a thread"
        video_thread = threading.Thread(target=self.record)
        video_thread.start()

    def stream(self):
        while True:
            _, frame = self.video.read()
            ret, jpeg = cv2.imencode('.jpg', frame)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

            return jpeg.tobytes()

class AudioRecorder():
    "Audio class based on pyAudio and Wave"
    def __init__(self, filename="temp_audio.wav", rate=16000, fpb=1024, channels=1):
        self.open = True
        self.rate = rate #
        self.frames_per_buffer = fpb #
        self.channels = channels #
        self.format = pyaudio.paInt16 #
        self.audio_filename = filename
        self.audio = pyaudio.PyAudio() #
        self.stream = self.audio.open(format=self.format,
                                      channels=self.channels,
                                      rate=self.rate,
                                      input=True,
                                      frames_per_buffer = self.frames_per_buffer)
        self.audio_frames = []

    def record(self):
        "Audio starts being recorded"
        self.stream.start_stream()
        while self.open:
            data = self.stream.read(self.frames_per_buffer)
            self.audio_frames.append(data)
            if not self.open:
                break

    def stop(self):
        "Finishes the audio recording therefore the thread too"
        if self.open:
            self.open = False
            self.stream.stop_stream()
            self.stream.close()
            self.audio.terminate()
            waveFile = wave.open(self.audio_filename, 'wb')
            waveFile.setnchannels(self.channels)
            waveFile.setsampwidth(self.audio.get_sample_size(self.format))
            waveFile.setframerate(self.rate)
            waveFile.writeframes(b''.join(self.audio_frames))
            waveFile.close()

            ########
            openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition"
            accessKey = "f6c0a846-ccbc-41ad-9745-623a54c728e1"
            audioFilePath = 'temp_audio.wav'
            languageCode = "korean"

            file = open(audioFilePath, "rb")
            audioContents = base64.b64encode(file.read()).decode("utf8")
            file.close()

            requestJson = {
                "access_key": accessKey,
                "argument": {
                    "language_code": languageCode,
                    "audio": audioContents
                }
            }
            with open('result.json', 'w', encoding="utf-8") as make_file:
                json.dump(requestJson, make_file, ensure_ascii=False, indent="\t")

            http = urllib3.PoolManager()
            response = http.request(
                "POST",
                openApiURL,
                headers={"Content-Type": "application/json; charset=utf-8"},
                body=json.dumps(requestJson)
            )

            b = response.data.decode('utf-8')

            with open('result1.json', 'w', encoding="utf-8") as make_file:
                json.dump(b, make_file, ensure_ascii=False, indent="\t")

            info = json.loads(b)

            print(info["return_object"]["recognized"])

        #####

    def start(self):
        "Launches the audio recording function using a thread"
        audio_thread = threading.Thread(target=self.record)
        audio_thread.start()

def start_AVrecording(filename="test"):
    global video_thread
    global audio_thread
    video_thread = VideoRecorder()
    audio_thread = AudioRecorder()
    audio_thread.start()
    video_thread.start()
    return filename

def start_video_recording(filename="test"):
    global video_thread
    video_thread = VideoRecorder()
    video_thread.start()
    return filename

def start_audio_recording(filename="test"):
    global audio_thread
    audio_thread = AudioRecorder()
    audio_thread.start()
    return filename

def stop_AVrecording(filename="test"):
    audio_thread.stop()
    frame_counts = video_thread.frame_counts
    elapsed_time = time.time() - video_thread.start_time
    recorded_fps = frame_counts / elapsed_time
    print("total frames " + str(frame_counts))
    print("elapsed time " + str(elapsed_time))
    print("recorded fps " + str(recorded_fps))
    video_thread.stop()

    # Makes sure the threads have finished
    while threading.active_count() > 1:
        time.sleep(1)

    # Merging audio and video signal
    if abs(recorded_fps - 6) >= 0.01:    # If the fps rate was higher/lower than expected, re-encode it to the expected
        print("Re-encoding")
        cmd = "ffmpeg -r " + str(recorded_fps) + " -i temp_video.avi -pix_fmt yuv420p -r 6 temp_video2.avi"
        subprocess.call(cmd, shell=True)
        print("Muxing")
        cmd = "ffmpeg -y -ac 2 -channel_layout stereo -i temp_audio.wav -i temp_video2.avi -pix_fmt yuv420p " + filename + ".avi"
        subprocess.call(cmd, shell=True)
    else:
        print("Normal recording\nMuxing")
        cmd = "ffmpeg -y -ac 2 -channel_layout stereo -i temp_audio.wav -i temp_video.avi -pix_fmt yuv420p " + filename + ".avi"
        subprocess.call(cmd, shell=True)
        print("..")

def file_manager(filename="test"):
    "Required and wanted processing of final files"
    local_path = os.getcwd()
    # if os.path.exists(str(local_path) + "/temp_audio.wav"):
    #     os.remove(str(local_path) + "/temp_audio.wav")
    # if os.path.exists(str(local_path) + "/temp_video.avi"):
    #     os.remove(str(local_path) + "/temp_video.avi")
    # if os.path.exists(str(local_path) + "/temp_video2.avi"):
    #     os.remove(str(local_path) + "/temp_video2.avi")
    # if os.path.exists(str(local_path) + "/" + filename + ".avi"):
    #     os.remove(str(local_path) + "/" + filename + ".avi")

# if __name__ == '__main__':
#     start_AVrecording()
#     time.sleep(10)
#     stop_AVrecording()
#     file_manager()

import cv2
import numpy as np
import time
import threading
from flask import Flask, render_template, Response


class EyeDetect:
    def __init__(self):
        self.video = cv2.VideoCapture(0)
        self.face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
        self.eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml')
        detector_params = cv2.SimpleBlobDetector_Params()
        detector_params.filterByArea = True
        detector_params.maxArea = 1500
        self.detector = cv2.SimpleBlobDetector_create(detector_params)
        self.left_eye_x = [];  # r
        self.left_eye_y = [];  # r
        self.right_eye_x = [];  # r
        self.right_eye_y = [];  # r

    def __del__(self):
        self.video.release()
        cv2.destroyAllWindows()

    def detect_faces(self, img, cascade):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        coords = cascade.detectMultiScale(gray_frame, 1.3, 5)
        if len(coords) > 1:
            biggest = (0, 0, 0, 0)
            for i in coords:
                if i[3] > biggest[3]:
                    biggest = i
            biggest = np.array([i], np.int32)
        elif len(coords) == 1:
            biggest = coords
        else:
            return None
        for (x, y, w, h) in biggest:
            frame = img[y:y + h, x:x + w]
        return frame

    def detect_eyes(self, img, cascade):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        eyes = cascade.detectMultiScale(gray_frame, 1.3, 5)  # detect eyes
        width = np.size(img, 1)  # get face frame width
        height = np.size(img, 0)  # get face frame height
        left_eye = None
        right_eye = None
        for (x, y, w, h) in eyes:
            if y > height / 2:
                pass
            eyecenter = x + w / 2  # get the eye center
            if eyecenter < width * 0.5:
                left_eye = img[y:y + h, x:x + w]
            else:
                right_eye = img[y:y + h, x:x + w]
        return left_eye, right_eye

    def cut_eyebrows(self, img):
        height, width = img.shape[:2]
        eyebrow_h = int(height / 4)
        img = img[eyebrow_h:height, 0:width]  # cut eyebrows out (15 px)

        return img

    def blob_process(self, img, threshold, detector):
        gray_frame = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        _, img = cv2.threshold(gray_frame, threshold, 255, cv2.THRESH_BINARY)
        img = cv2.erode(img, None, iterations=2)
        img = cv2.dilate(img, None, iterations=4)
        img = cv2.medianBlur(img, 5)
        keypoints = detector.detect(img)

        return keypoints

    def nothing(self, x):
        pass

    def main(self):
        cap = cv2.VideoCapture(0)
        cv2.namedWindow('image')
        cv2.createTrackbar('threshold', 'image', 0, 255, self.nothing)
        while True:
            _, frame = cap.read()
            face_frame = self.detect_faces(frame, self.face_cascade)
            if face_frame is not None:
                eyes = self.detect_eyes(face_frame, self.eye_cascade)

                for eye in eyes:
                    if eye is not None:
                        threshold = r = cv2.getTrackbarPos('threshold', 'image')
                        eye = self.cut_eyebrows(eye)
                        keypoints = self.blob_process(eye, threshold, self.detector)
                        eye = cv2.drawKeypoints(eye, keypoints, eye, (0, 0, 255),
                                                cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)
            cv2.imshow('image', frame)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
        cap.release()
        cv2.destroyAllWindows()

    def stream(self):
        while True:
            _, frame = self.video.read()
            ret, jpeg = cv2.imencode('.jpg', frame)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

            return jpeg.tobytes()

    def distancetest(self):
        cap = cv2.VideoCapture(0)  # 실시간

        while 1:
            ret, img = cap.read()
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            faces = self.face_cascade.detectMultiScale(gray, 1.3, 5)

            for (x, y, w, h) in faces:
                if (w < 140):
                    print('더 가까이 와야함')
                else:
                    print('가능')
                    return;

            cv2.imshow('', img)

    def getlist(self):
        return

    def clearlist(self):
        self.right_eye_y = []
        self.right_eye_x = []
        self.left_eye_y = []
        self.left_eye_x = []

    def start(self):
        "Launches the video recording function using a thread"
        video_thread = threading.Thread(target=self.stream)
        video_thread.start()

    def stop(self):
        return


# from konlpy.tag import Okt, Kkma
# kkma=Kkma()
# print(kkma.pos("무엇을 위하여 열심히 하였는가 조용하다"))
e = EyeDetect()
e.main()
#
# start_AVrecording()
# time.sleep(3)
# stop_AVrecording()
# time.sleep(3)
# start_AVrecording()
# time.sleep(3)
# stop_AVrecording()
# time.sleep(3)
# start_AVrecording()
# time.sleep(3)
# stop_AVrecording()