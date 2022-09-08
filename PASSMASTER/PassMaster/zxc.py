testcase = eval(input())
print(testcase)

for i in range(testcase):
    result=0;
    K,N,M=[int(x) for x in input().split()]
    # K = eval(input()) # 한번에 이동가능한 정류장 수
    # N = eval(input()) # 종점의 번호 0번부터 시작
    # M = eval(input()) # 충전기가 설치되어있는 정류장의 수
    busstop=[] # 충전기가 설치되어 있는 정류장 번호 리스트
    templist=[]

    busstop.append([int(y) for y in input().split()])
    for i in range(len(busstop)-1):
        if busstop[i+1]-busstop[i] <=K:
            templist.append(busstop[i+1]-busstop[i])
        else:
            result=-1

    for i in range(len(templist)):
        print
    print("#",i+1," ",result)