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
        self.left_eye_x = []; #r
        self.left_eye_y = []; #r
        self.right_eye_x = []; #r
        self.right_eye_y = []; #r


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
        _, frame = self.video.read()

        frame = cv2.resize(frame, None, fx=1.5, fy=1.5, interpolation=cv2.INTER_AREA)
        face_frame = self.detect_faces(frame, self.face_cascade)
        if face_frame is not None:
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
        self.right_eye_y=[]
        self.right_eye_x=[]
        self.left_eye_y=[]
        self.left_eye_x=[]

    def start(self):
        "Launches the video recording function using a thread"
        video_thread = threading.Thread(target=self.stream)
        video_thread.start()

    def stop(self):
        return
