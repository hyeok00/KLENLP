from flask import Flask, render_template, request
from pymysql import *
from datetime import datetime
import board_dao
import connection

app = Flask(__name__)

@app.route('/')
def board():
    return render_template('write.html')

@app.route('/write')
def board_write():
    return render_template('write.html')

@app.route('/end_write', methods=['POST'])
def board_endwrite():
    title=request.form['input_title']
    context=request.form['input_context']
    writer=request.form['writer']
    password=request.form['password']

    now = datetime.now()
    formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')
    print(formatted_date)

    board_dao.save_article(title,context,writer,password,formatted_date)
    return render_template('write.html')

if __name__ == '__main__':
    app.run(debug=True)