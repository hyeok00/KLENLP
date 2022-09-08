# -*-coding:utf-8-*-
import jpype
from flask import Flask, render_template, request, Response
import pyaudio
import urllib3
import json
import base64
import pyttsx3
import time
import cv2
from random import *
import numpy as np
from konlpy.tag import Okt, Kkma
import pandas as pd
import math
import wave
import random
import threading
import subprocess
import os
import concurrent.futures
import gc

import pymysql

app = Flask(__name__)

db = pymysql.connect(host='localhost', user='root', passwd='root', db='pass_master', charset='utf8')
cursor = db.cursor()
office_name = []
issue_print = []
question_1_print = []
question_2_print = []
input_index = 0

kk = Kkma()
okt = Okt()

word_data = pd.read_csv('word_p.csv', encoding='cp949')
X_data = word_data['word']
Y_data = word_data['value']

over_file = open('over2.csv', encoding='cp949')
rdr = over_file.readlines()
over = [line.rstrip() for line in rdr]

# distance = [[] * i for i in range(5)];  # 2차원 각각 질문에 대한 눈동자 움직임이 담겨있음

temp_text = ""
global a, b
global left_x, left_y, right_x, right_y

left_x = []
left_y = []
right_x = []
right_y = []


class VideoR:
    def __init__(self):
        self.video = cv2.VideoCapture(0)
        #self.face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
        #self.eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml')
        #detector_params = cv2.SimpleBlobDetector_Params()
        #detector_params.filterByArea = True
        #detector_params.maxArea = 1500
        #self.detector = cv2.SimpleBlobDetector_create(detector_params)
        self.left_x = [];  # r
        self.left_y = [];  # r
        self.right_x = [];  # r
        self.right_y = [];  # r

    def __del__(self):
        self.video.release()
        cv2.destroyAllWindows()

    def stream(self):
        while True:
            _, frame = self.video.read()
            global a, b
            a = _
            b = frame
            try:
                ret, jpeg = cv2.imencode('.jpg', frame)
            except cv2.error:
                jpeg=[]
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

            try:
                return jpeg.tobytes()
            except:
                return;

e = VideoR()


#####
class VideoRecorder():
    "Video class based on openCV"

    def __init__(self, name="temp_video.avi", fourcc="MJPG", sizex=640, sizey=480, camindex=0, fps=30):
        self.open = True
        self.device_index = camindex
        self.fps = fps  # fps should be the minimum constant rate at which the camera can
        self.fourcc = fourcc  # capture images (with no decrease in speed over time; testing is required)
        self.frameSize = (sizex, sizey)  # video formats and sizes also depend and vary according to the camera used
        self.video_filename = name
        self.video_cap = cv2.VideoCapture(self.device_index+cv2.CAP_DSHOW)
        self.video_writer = cv2.VideoWriter_fourcc(*self.fourcc)
        self.video_out = cv2.VideoWriter(self.video_filename, self.video_writer, self.fps, self.frameSize)
        self.frame_counts = 1
        self.start_time = time.time()
        self.threshhold=45
        self.face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
        self.eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml')
        detector_params = cv2.SimpleBlobDetector_Params()
        detector_params.filterByArea = True
        detector_params.maxArea = 1500
        self.detector = cv2.SimpleBlobDetector_create(detector_params)
        self.left_eye_x = []  # r
        self.left_eye_y = []  # r
        self.right_eye_x = []  # r
        self.right_eye_y = []  # r

    def __del__(self):
        self.video_cap.release()
        cv2.destroyAllWindows()

    def getframe(self):

        return

    def record(self):
        "Video starts being recorded"
        # counter = 1
        timer_start = time.time()
        timer_current = 0
        while self.open:
            global a, b
            ret = a
            video_frame = b

            if ret:
                self.video_out.write(video_frame)

                face_frame = self.detect_faces(video_frame, self.face_cascade)
                if face_frame is not None:
                    self.video_out.write(video_frame)
                    eyes = self.detect_eyes(face_frame, self.eye_cascade)

                    if eyes[0] is not None:
                        eye = eyes[0]
                        threshold = self.threshhold
                        eye = self.cut_eyebrows(eye)
                        keypoints = self.blob_process(eye, threshold, self.detector)
                        # print("왼쪽 눈", len(keypoints))
                        if len(keypoints)==0:
                            print('왼쪽 눈 인식X')
                        for keypoint in keypoints:
                            self.left_eye_x.append(keypoint.pt[0])
                            self.left_eye_y.append(keypoint.pt[1])
                            print('왼쪽 눈 x :',keypoint.pt[0], 'y:' ,keypoint.pt[1])
                    # else:
                    #     self.left_eye_x.append(0)
                    #     self.left_eye_y.append(0)

                    if eyes[1] is not None:
                        eye = eyes[1]
                        threshold = self.threshhold
                        eye = self.cut_eyebrows(eye)
                        keypoints = self.blob_process(eye, threshold, self.detector)
                        # print("eye[1]", keypoints)
                        if len(keypoints) == 0:
                            print('오른쪽 눈 인식X')
                        for keypoint in keypoints:
                            self.right_eye_x.append(keypoint.pt[0])
                            self.right_eye_y.append(keypoint.pt[1])
                            print('오른쪽 눈 x :', keypoint.pt[0], 'y:', keypoint.pt[1])
                    # else:
                    #     self.right_eye_x.append(0)
                    #     self.right_eye_y.append(0)

                #cv2.imshow('frame', video_frame)
                # print(str(counter) + " " + str(self.frame_counts) + " frames written " + str(timer_current))
                self.frame_counts += 1
                # counter += 1
                # timer_current = time.time() - timer_start
                time.sleep(1 / self.fps)
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
        self.right_eye_y.clear()
        self.right_eye_x.clear()
        self.left_eye_y.clear()
        self.left_eye_x.clear()

        #####################################

    def stop(self):
        "Finishes the video recording therefore the thread too"
        if self.open:
            self.open = False
            self.video_cap.release()
            cv2.destroyAllWindows()

        global left_x, left_y, right_x, right_y
        left_x = self.left_eye_x
        left_y = self.left_eye_y
        right_x = self.right_eye_x
        right_y = self.right_eye_y

    def start(self):
        "Launches the video recording function using a thread"
        video_thread = threading.Thread(target=self.record)
        video_thread.start()

    def stream(self):
        while True:
            _, frame = self.video_cap.read()
            ret, jpeg = cv2.imencode('.jpg', frame)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

            return jpeg.tobytes()


class AudioRecorder():
    "Audio class based on pyAudio and Wave"

    def __init__(self, filename="temp_audio.wav", rate=16000, fpb=1024, channels=1):
        self.open = True
        self.rate = rate  #
        self.frames_per_buffer = fpb  #
        self.channels = channels  #
        self.format = pyaudio.paInt16  #
        self.audio_filename = filename
        self.audio = pyaudio.PyAudio()  #
        self.stream = self.audio.open(format=self.format,
                                      channels=self.channels,
                                      rate=self.rate,
                                      input=True,
                                      frames_per_buffer=self.frames_per_buffer)
        self.audio_frames = []

    def __del__(self):
        return;

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
            openApiURL = "-"
            accessKey = "-"
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
            global temp_text
            print(info)

            print(info['result'])
            if info['result'] is -1:
                temp_text = "ASR_NOTOKEN"
            else:
                try:
                    temp_text = info["return_object"]["recognized"]
                except KeyError:
                    temp_text = "ASR_NOTOKEN"

            print('답변?',temp_text)
            return


        #######

    def start(self):
        "Launches the audio recording function using a thread"
        audio_thread = threading.Thread(target=self.record)
        audio_thread.start()


def start_AVrecording(filename="test"):
    global video_thread
    global audio_thread
    video_thread = VideoRecorder()
    audio_thread = AudioRecorder()
    # executor = concurrent.futures.ThreadPoolExecutor(max_workers=4)
    # executor.submit(video_thread.record())
    # executor.submit(audio_thread.start())
    # ##수정 중
    # global threads
    # threads=[]
    # video_t=threading.Thread(target=video_thread.record())
    # video_t.start()
    # audio_t=threading.Thread(target=audio_thread.start())
    # audio_t.start()
    # threads.append(video_t)
    # threads.append(audio_t)

    ## 수정 끝

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

    num= threading.active_count()
    print('비디오 끝나기전', num)
    audio_thread.stop()
    while num==threading.active_count():
        print('대기중 1')
        time.sleep(1)

    num = threading.active_count()
    print('비디오 끝', num)

    video_thread.stop()

    while num==threading.active_count():
        print('대기중 2')
        time.sleep(1)

    print('오디오끝')
    gc.collect()
    # while threading.active_count() > 3:
    #     print(threading.active_count())
    #     time.sleep(1)




class Pass_master():
    def __init__(self):
        self.answer_data = ["", "", "", "", ""]
        self.distance=[[] * i for i in range(5)]

    def initialize(self):
        self.answer_data = ["", "", "", "", ""]
        global left_x, left_y, right_x, right_y
        self.distance = [[] * i for i in range(5)];


p = Pass_master();


# 메인 페이지
@app.route('/')
def action():
    p.initialize()
    e.__init__()
    return render_template('index.html')


# 마이크 테스트 페이지
@app.route('/mic_test', methods=['POST'])
def mic_test():

    #p.initialize()
    office_name.append(request.form['office_name'])

    sql = "select * from job_korea4 where office_name IN('" + office_name[0] + "')"
    cursor.execute(sql)
    result = cursor.fetchmany(1)

    if not result:
        print("없음")
        sql = "select office_name from job_korea4 order by rand() LIMIT 1"
        cursor.execute(sql)
        result = cursor.fetchmany(1)
        s = ''.join(result[0])
        print("대체회사:" + s)
        office_name[0] = s

    null_check = 2
    return render_template('mic_test.html', null_check=null_check)


# 마이크 테스트 시작
@app.route('/mic_test_start', methods=['POST'])
def mic_test_start():
    null_check = 1
    FORMAT = pyaudio.paInt16
    CHANNELS = 1
    RATE = 16000
    CHUNK = 1024
    RECORD_SECONDS = 10
    WAVE_OUTPUT_FILENAME = "mic.wav"

    audio = pyaudio.PyAudio()
    input_index = 0
    check = 0
    while (check == 0):
        try:

            stream = audio.open(format=pyaudio.paInt16, channels=CHANNELS, rate=RATE, input=True,
                                input_device_index=input_index, frames_per_buffer=CHUNK)
            check = 1

        except:

            # start Recording
            print("예외!")
            input_index = input_index + 1

    if (check == 1):
        print("통과!")
        print("recording...")

        frames = []

        for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
            data = stream.read(CHUNK)

            frames.append(data)

        print("finished recording")

        # stop Recording

        stream.stop_stream()

        stream.close()

        audio.terminate()

        waveFile = wave.open(WAVE_OUTPUT_FILENAME, 'wb')

        waveFile.setnchannels(CHANNELS)

        waveFile.setsampwidth(audio.get_sample_size(FORMAT))

        waveFile.setframerate(RATE)

        waveFile.writeframes(b''.join(frames))

        waveFile.close()

        openApiURL = "-"
        accessKey = "-"
        audioFilePath = 'mic.wav'
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

    if (info["return_object"]["recognized"] == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1

    return render_template('mic_test.html', null_check=null_check)


# 자기소개 페이지
@app.route('/intro', methods=['POST'])
def _intro():
    null_check = 2

    return render_template('intro.html', null_check=null_check)


# 자기소개 시작
@app.route('/intro_start', methods=['POST'])
def _intro_start():
    # start_AVrecording()
    engine = pyttsx3.init()
    engine.say('자기소개를 해주세요. 제한시간은 60초 입니다.')
    engine.runAndWait()

    start_AVrecording()
    print("22222")
    time.sleep(10)
    stop_AVrecording()
    print("3333")
    print("temp_text=", temp_text)
    null_check = 1

    if (temp_text == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1
        p.answer_data[0] = temp_text;
        # print_text.append(temp_text)

    return render_template('intro.html', null_check=null_check)


# 지원동기 페이지
@app.route('/motive', methods=['POST'])
def _motive():
    null_check = 2

    global left_x, left_y, right_x, right_y

    if len(left_y) > len(right_y):
        for i in range(len(right_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[0].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))
    else:
        for i in range(len(left_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[0].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))

    left_x = []
    left_y = []
    right_x = []
    right_y = []

    return render_template('motive.html', null_check=null_check)


# 지원동기 시작
@app.route('/motive_start', methods=['POST'])
def _motive_start():
    engine = pyttsx3.init()
    engine.say('지원동기를 말해주세요. 제한시간은 60초 입니다.')
    engine.runAndWait()

    start_AVrecording()
    time.sleep(10)
    stop_AVrecording()
    print("temp_text=", temp_text)
    null_check = 1
    # WAVE_OUTPUT_FILENAME = "question_1.wav"

    if (temp_text == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1
        p.answer_data[1] = temp_text;
        # print_text.append(temp_text)

    return render_template('motive.html', null_check=null_check)


# 이슈 페이지
@app.route('/issue', methods=['POST'])
def _issue():
    if(len(issue_print) == 1):
        del(issue_print[0])

    global left_x, left_y, right_x, right_y

    if len(left_y) > len(right_y):
        for i in range(len(right_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[1].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))
    else:
        for i in range(len(left_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[1].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))

    left_x = []
    left_y = []
    right_x = []
    right_y = []

    null_check = 2
    sql = "select * from issue_list order by rand() limit 1"
    cursor.execute(sql)
    rows = cursor.fetchone()

    issue = rows[0]

    db.commit()

    temp_issue_print = issue + "에 대해 어떻게 생각하시나요?"
    issue_print.append(temp_issue_print)
    # issue_print.append("저출산 대한민국에 대해 어떻게 생각하시나요?")
    return render_template('issue.html', issue_print=issue_print[0], null_check=null_check)


# 이슈 시작
@app.route('/issue_start', methods=['POST'])
def _issue_start():
    engine = pyttsx3.init()
    engine.say(issue_print[0])
    engine.runAndWait()

    start_AVrecording()
    time.sleep(10)
    stop_AVrecording()
    print("temp_text=", temp_text)
    null_check = 1
    # WAVE_OUTPUT_FILENAME = "question_1.wav"

    if (temp_text == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1
        p.answer_data[2] = temp_text;
        # print_text.append(temp_text)

    return render_template('issue.html', null_check=null_check)


# 기업 맞춤 질문_1 페이지
@app.route('/question_1', methods=['POST'])
def _question_1():
    if (len(question_1_print) == 1):
        del (question_1_print[0])
    global left_x, left_y, right_x, right_y

    if len(left_y) > len(right_y):
        for i in range(len(right_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[2].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))
    else:
        for i in range(len(left_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[2].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))

    left_x = []
    left_y = []
    right_x = []
    right_y = []

    null_check = 2
    sql = "select * from job_korea4 where office_name = '" + office_name[0] + "' order by rand() limit 1"
    cursor.execute(sql)
    rows = cursor.fetchone()

    question = rows[3]
    question_1_print.append(question)
    # question_1_print.append("가장 존경하는 인물이 누구인가요?")
    db.commit()

    return render_template('question_1.html', question=question, null_check=null_check)


# 기업 맞춤 질문_1 시작 페이지
@app.route('/question_1_start', methods=['POST'])
def _question_1_start():
    # start_AVrecording()
    engine = pyttsx3.init()
    engine.say(question_1_print[0])
    engine.runAndWait()

    start_AVrecording()
    time.sleep(10)
    stop_AVrecording()
    print("temp_text=", temp_text)
    null_check = 1
    # WAVE_OUTPUT_FILENAME = "question_1.wav"

    if (temp_text == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1
        p.answer_data[3] = temp_text;
        # print_text.append(temp_text)

    return render_template('question_1.html', null_check=null_check)


# 기업 맞춤 질문_2 페이지
@app.route('/question_2', methods=['POST'])
def _question_2():
    if (len(question_2_print) == 1):
        del (question_2_print[0])
    global left_x, left_y, right_x, right_y

    if len(left_y) > len(right_y):
        for i in range(len(right_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[3].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))
    else:
        for i in range(len(left_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[3].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))

    left_x = []
    left_y = []
    right_x = []
    right_y = []

    null_check = 2
    sql = "select * from job_korea4 where office_name = '" + office_name[0] + "' order by rand() limit 1"
    cursor.execute(sql)
    rows = cursor.fetchone()

    question = rows[3]
    question_2_print.append(question)
    # question_2_print.append("살면서 가장 힘들었던 순간이 언제인가요?")

    db.commit()

    return render_template('question_2.html', question=question, null_check=null_check)


# 기업 맞춤 질문_2 시작 페이지
@app.route('/question_2_start', methods=['POST'])
def _question_2_start():
    engine = pyttsx3.init()
    engine.say(question_2_print[0])
    engine.runAndWait()

    start_AVrecording()
    time.sleep(10)
    stop_AVrecording()
    print("temp_text=", temp_text)

    if (temp_text == "ASR_NOTOKEN"):
        null_check = 0

    else:
        null_check = 1
        p.answer_data[4] = temp_text
        # print_text.append(temp_text)

    return render_template('question_2.html', null_check=null_check)


@app.route('/final', methods=['POST'])
def final():
    jpype.attachThreadToJVM()
    e.__del__()

    global left_x, left_y, right_x, right_y

    if len(left_y) > len(right_y):
        for i in range(len(right_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[4].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))
    else:
        for i in range(len(left_y) - 1):
            x = (left_x[i] + right_x[i]) / 2
            y = (left_y[i] + right_y[i]) / 2
            x2 = (left_x[i + 1] + right_x[i + 1]) / 2
            y2 = (left_y[i + 1] + right_y[i + 1]) / 2
            p.distance[4].append(math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)))

    distance=p.distance
    left_x = []
    left_y = []
    right_x = []
    right_y = []

    lpm_list = []
    pos_rate_list = []
    neg_rate_list = []
    result_list = []

    # 수정1
    pos_all_list = []
    neg_all_list = []
    # 수정1 끝

    print_text = p.answer_data
    for num in range(len(print_text)):
        intro_temp = str(print_text[num])

        # 긍부정 분석
        sentence = intro_temp
        temp = kk.pos(sentence)
        joinword = ["/".join(temp[i]) for i in range(len(temp))]

        pos_word = 0
        pos_word_list = []
        neg_word = 0
        neg_word_list = []
        other_word = 0
        other_word_list = []

        nounlist = []
        word = []

        for i in range(len(joinword)):
            splitpos = joinword[i].split('/')
            if (splitpos[1] == 'NNG' or splitpos[1] == 'MAG' or splitpos[1] == 'VA'):
                # 일반명사, 부사, 형용사
                nounlist.append(joinword[i])
                # if (splitpos[1] == 'NNG' or splitpos[1] == 'MAG'):
                #     word.append(splitpos[0])


        nounlist = list(set(nounlist))

        for i in range(len(nounlist)):
            if nounlist[i] in list(X_data):
                idx = list(X_data).index(nounlist[i])
                if Y_data[idx] == 'POS':
                    pos_word += 1
                    pos_word_list.append(X_data[idx])
                elif Y_data[idx] == 'NEG':
                    neg_word += 1
                    neg_word_list.append(X_data[idx])
                else:
                    other_word += 1
                    other_word_list.append(X_data[idx])
            else:
                other_word += 1
                other_word_list.append(nounlist[i])

        result = ''
        temp = []
        if pos_word > neg_word:
            if pos_word > other_word:
                for i in range(len(pos_word_list)):
                    s = pos_word_list[i].split('/')
                    # if s[0] is not 'VV':
                    #     temp.append(s[0])
                    if (s[1] == 'VA'):
                        s[0] = s[0] + '다'
                    temp.append(s[0])
                result = '긍정적'
            else:
                for i in range(len(other_word_list)):
                    s = other_word_list[i].split('/')
                    # if s[0] is not 'VV':
                    #     temp.append(s[0])
                    if (s[1] == 'VA'):
                        s[0] = s[0] + '다'
                    temp.append(s[0])
                result = '중립적'
        else:
            if neg_word >= other_word:
                for i in range(len(neg_word_list)):
                    s = neg_word_list[i].split('/')
                    # if s[0] is not 'VV':
                    #     temp.append(s[0])
                    if (s[1] == 'VA'):
                        s[0] = s[0] + '다'
                    temp.append(s[0])
                result = '부정적'
            else:
                for i in range(len(other_word_list)):
                    s = other_word_list[i].split('/')
                    # if s[0] is not 'VV':
                    #     temp.append(s[0])
                    if (s[1] == 'VA'):
                        s[0] = s[0] + '다'
                    temp.append(s[0])
                result = '중립적'

        if len(temp) > 3:
            temp = temp[0:3]

        temp = ", ".join(temp)
        result = temp + ' 등 같은 ' + result + '성향의 단어를 가장 많이 사용했습니다.'

        for i in over:
            if i in str(print_text[num]):
                result = result + "\n" + i + "은(는) 겹말이기 때문에 특별히 강조할만한 내용이 아니라면 사용하지 않을 것을 권장합니다."

        if (len(temp) == 0):
            result = ""

        result = result.split('\n')
        result_list.append(result)
        # result = 2번 배열

        pos_rate = 0;
        neg_rate = 0;

        if (pos_word + neg_word != 0):
            pos_rate = int((pos_word / (pos_word + neg_word)) * 100)
            neg_rate = int((neg_word / (pos_word + neg_word)) * 100)

        else:
            pos_rate = -1
            neg_rate = -1

        if ((pos_rate + neg_rate) == 99):
            pos_rate = pos_rate + 1

        pos_rate_list.append(pos_rate)
        neg_rate_list.append(neg_rate)

        # 넘겨줄거는 pos_rate_list, neg_rate_list

        # 수정 3
        for i in range(len(pos_word_list)):
            p_temp = pos_word_list[i].split('/')
            if (p_temp[1] == 'VA'):
                p_temp[0] = p_temp[0] + '다'
                print(p_temp[0])

            pos_word_list[i] = p_temp[0]

        for i in range(len(neg_word_list)):
            n_temp = neg_word_list[i].split('/')
            if (n_temp[1] == 'VA'):
                n_temp[0] = n_temp[0] + '다'
            neg_word_list[i] = n_temp[0]

        # 수정 3 끝

        pos_all_list.append(pos_word_list)
        neg_all_list.append(neg_word_list)

        # 말속도 분석

        intro_text = print_text[num].replace(" ", "")
        lpm_list.append(len(intro_text))

        # 넘겨줄거 : lpm_list

    gaze_list = [[], [], [], [], []]
    axis_x = [[], [], [], [], []]

    for i in range(5):
        length = len(distance[i]) - 1
        if (length < 1):
            length = 0
        list_x = np.linspace(1, 10, length)
        axis_x[i] = list(list_x)
        for j in range(length):
            gaze_list[i].append(distance[i][j])

    length = []
    length.append(len(gaze_list[0]))
    length.append(len(gaze_list[1]))
    length.append(len(gaze_list[2]))
    length.append(len(gaze_list[3]))
    length.append(len(gaze_list[4]))

    for i in range(5):
        if (length[i] == 0):
            gaze_list[i].append(0)
            gaze_list[i].append(0)
            gaze_list[i].append(0)
            gaze_list[i].append(0)
            axis_x[i].append(1)
            axis_x[i].append(2)
            axis_x[i].append(3)
            axis_x[i].append(4)
            length[i] = 4

    print_gaze = []
    print_axis = []

    id_num = ""
    print_length = 0

    return render_template('final.html', result_list=result_list, lpm_list=lpm_list, gaze_list=gaze_list, length=length,
                           axis_x=axis_x, print_gaze=print_gaze, print_axis=print_axis, id_num=id_num,
                           print_length=print_length, pos_rate_list=pos_rate_list, neg_rate_list=neg_rate_list,
                           pos_all_list=pos_all_list, neg_all_list=neg_all_list, print_text=p.answer_data)

def gen(camera):
    while True:
        try:
            frame = camera.stream()
            if(frame is not None):
                yield (b'--frame\r\n'
                         b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        except RuntimeError:
            print()


@app.route('/video_feed')
def video_feed():
    return Response(gen(e),
                    mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    app.run()
