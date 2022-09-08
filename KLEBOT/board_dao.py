import pymysql
import connection


def get_board():
    conn = connection.get_connection()

    sql = '''
        select post_id, title, writer, regtime, views, good, bad, state from article
        order by post_id desc
    '''

    cursor = conn.cursor()
    cursor.execute(sql)
    row = cursor.fetchall()

    data_list = []

    for obj in row:
        data_dic = {
            'post_id': obj[0],
            'title': obj[1],
            'writer': obj[2],
            'regtime': obj[3],
            'views': obj[4],
            'good': obj[5],
            'bad': obj[6],
            'state': obj[7]
        }
        data_list.append(data_dic)

    conn.close

    return data_list


def save_article(input_title, input_context, input_writer, input_password,formatted_date):
    conn = connection.get_connection()
    cursor = conn.cursor()
    # SQL query 작성
    # sql = """INSERT INTO article(post_id, title, post_context, writer, pwd, post_ip, regtime, state, post_status, views, answer, good, bad)
    #          VALUES('NULL','input_title', 'input_context, input_writer, input_pwd','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL');"""



    sql = '''INSERT INTO article(title, post_context, writer, pwd, post_ip, regtime, state, post_status, views, good, bad)
    '''+ 'VALUES("'+input_title+'", "'+input_context+'", "'+input_writer+'", "'+input_password+'","'+str(1234)+'","'+formatted_date+'","N","Y",0,0,0);'

    print(sql)

    # # SQL query 실행
    cursor.execute(sql)

    # 데이터 변화 적용
    conn.commit()
    conn.close()
