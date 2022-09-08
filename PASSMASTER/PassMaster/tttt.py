from konlpy.tag import Okt,Kkma,Hannanum,_komoran
import pandas as pd
from collections import Counter

kk = Kkma()
data = pd.read_csv('word_p.csv', encoding='cp949')
X_data = data['word']
Y_data = data['value']

def countword(param_sentence): # 1번

    sentence = param_sentence
    temp = kk.pos(sentence)
    joinword = ["/".join(temp[i]) for i in range(len(temp))]

    pos_word = 0
    pos_word_list=[] ##
    neg_word = 0
    neg_word_list = [] ##
    other_word = 0;
    other_word_list = [] ##

    nounlist = []
    word=[]
    for i in range(len(joinword)):
        splitpos = joinword[i].split('/')
        if (splitpos[1] == 'NNG' or splitpos[1] == 'VV' or splitpos[1] == 'MAG' or splitpos[1] == 'VA'):
            # 일반명사, 동사, 부사, 형용사
            nounlist.append(joinword[i])
            if(splitpos[1] =='NNG' or splitpos[1] =='MAG'):
                word.append(splitpos[0])

    for i in range(len(nounlist)):
        if nounlist[i] in list(X_data):
            idx = list(X_data).index(nounlist[i])
            if Y_data[idx] == 'POS':
                pos_word += 1
                pos_word_list.append(X_data[idx]) ##
            elif Y_data[idx] == 'NEG':
                neg_word += 1
                neg_word_list.append(X_data[idx]) ##
            else:
                other_word += 1
                other_word_list.append(X_data[idx]) ##
        else:
            other_word += 1
            other_word_list.append(nounlist[i])  ##

    print(nounlist)
    print('전체단어 수:', pos_word + neg_word + other_word)
    print('긍정단어 수:', pos_word)
    print(pos_word_list)
    print('부정단어 수:', neg_word)
    print(neg_word_list)
    print('중립단어 수:', other_word)
    print(other_word_list)

    result=''
    temp=[]
    if pos_word >neg_word:
        if pos_word > other_word:
            for i in range(len(pos_word_list)):
                s=pos_word_list[i].split('/')
                if s[0] is not 'VV':
                    temp.append(s[0])
            result='긍정적'
        else:
            for i in range(len(other_word_list)):
                s=other_word_list[i].split('/')
                if s[0] is not 'VV':
                    temp.append(s[0])
            result='중립적'
    else:
        if neg_word >= other_word:
            for i in range(len(neg_word_list)):
                s=neg_word_list[i].split('/')
                if s[0] is not 'VV':
                    temp.append(s[0])
            result = '부정적'
        else:
            for i in range(len(other_word_list)):
                s=other_word_list[i].split('/')
                if s[0] is not 'VV':
                    temp.append(s[0])
            result = '중립적'

    print(temp)
    if len(temp)>3:
        temp=temp[0:3]

    temp=", ".join(temp);
    result=temp+'과 같은 ' + result +'성향의 단어를 많이 사용했습니다. \n 실제 AI면접에서는 긍정적 단어를 많이 사용하시는것이 좋습니다'

    cnt=Counter(word)
    most_freq_word=cnt.most_common(2)
    if(most_freq_word[0][1] != most_freq_word[1][1]):
        result=result+"\n또한 '"+ str(most_freq_word[0][0]) +"' 단어를 가장 많이 사용하였습니다. \n 한가지 단어를 여러번 사용하는것은 바람직하지 않습니다.."
    print(result)
#-------------------------------------------------


sentence = '만약을 위해 추가 질문 있음을 염두에 두자 잘 안다고 세세한 것까지 전부 말 해버리지 말고 만약을 대비해 적당히 여유를 두는 것이 좋다'

countword(sentence)

