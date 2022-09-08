from flask import Flask, render_template, request, Response
from flask_googlecharts import LineChart
import random
app = Flask(__name__)
arr=[]
for i in range(100,500):
    arr.append(i);

temp=[]
for i in range(60):
    temp.append(random.randrange(0,len(arr))) #arr대신 distance[0],distance[1] ....
temp.sort()

result=[]
for i in range(60):
    result.append(arr[temp[i]])

@app.route('/')
def action():
    return render_template('index99.html', arr=result)


if __name__ == '__main__':
    app.run()
