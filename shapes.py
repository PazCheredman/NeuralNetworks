import numpy as np
import cv2
import random
import math

def circle (x,y,r):
    img=np.zeros((100,100,1),np.uint8) ## creating img in matrix - matrix 100x100, 1 color=black
    cv2.circle(img, (x,y), r, (255,255,255) , 1)
    cv2.imshow("circle", img )
    cv2.waitKey(200)
    return img

def randomCircle():
    x=random.randint(10,90)
    y=random.randint(10,90)
    r=min(100-x,100-y, x, y)
    return x,y,r

def triangle(x1,x2,x3): ##this function receive 3 points : x,y,z
    img=np.zeros((100,100,1),np.uint8)
    cv2.line(img,x1,x2,(255,255,255),1)
    cv2.line(img,x1,x3,(255,255,255),1)
    cv2.line(img,x2,x3,(255,255,255),1)
    cv2.imshow("triangle", img )
    cv2.waitKey(200)
    return img

def randomTriangle(): ##this function returns 3 points
    while(True):
        x1=random.randint(0,99)
        y1=random.randint(0,99)
        x2=random.randint(0,99)
        y2=random.randint(0,99)
        x3=random.randint(0,99)
        y3=random.randint(0,99)
        if(x2==x1):
            continue
        m=(y2-y1)/(x2-x1)
        n= y1-m*x1
        d=abs((m*x3-y3*n)/(math.sqrt(m*m+1)))
        if(d>10):
            break
   # pts=np.array( [[x1,y1], [x2,y2], [x3,y3]], np.int32)
    # pts = pts.reshape((-1,1,2))
    return  (x1,y1),(x2,y2),(x3,y3)

def rectrangle(p1,p2,angle):
    img=np.zeros((100,100,1),np.uint8)
    cv2.rectangle(img,p1,p2,(255,255,255), 1)
   # rows,cols = img.shape
    M = cv2.getRotationMatrix2D(( (p1[0]+p2[0])/2,(p1[1]+p2[1])/2 ),-angle,1)
    dst = cv2.warpAffine(img,M,(100,100))
    cv2.imshow("rectrangle", dst)
    cv2.waitKey(200)
    return dst

def randomRectrangle():
    while(True):
        x1=random.randint(0,90)
        y1=random.randint(0,90)
        w=random.randint(10,100-x1)
        h=random.randint(10,100-y1)
        x2=x1+w
        y2=y1+h
        angle1=random.randint(0,30)
        angle=angle1*math.pi/180
        mx=(x1+x2)/2
        my=(y1+y2)/2
        px1=(x1-mx)*math.cos(angle)-(y1-my)*math.sin(angle) +mx
        px2=(x2-mx)*math.cos(angle)-(y2-my)*math.sin(angle) +mx
        px3=(x2-mx)*math.cos(angle)-(y1-my)*math.sin(angle) +mx
        px4=(x1-mx)*math.cos(angle)-(y2-my)*math.sin(angle) +mx
        py1=(x1-mx)*math.sin(angle)+(y1-my)*math.cos(angle) +my
        py2=(x2-mx)*math.sin(angle)+(y2-my)*math.cos(angle) +my
        py3=(x2-mx)*math.sin(angle)+(y1-my)*math.cos(angle) +my
        py4=(x1-mx)*math.sin(angle)+(y2-my)*math.cos(angle) +my
        
        print("px1= {} px2= {} py1={} py2={}".format(px1,px2,py1,py2))
        if ((min(px1,px2,px3,px4,py1,py2,py3,py4)<=0) or (max(px1,px2,px3,px4,py1,py2,py3,py4)>=100)) :
            continue
        else: 
            break
        
    return (x1,y1),(x2,y2), angle1

def printImg(file,img):
    with open(file, "w") as f:
        for y in range(img.shape[0]):
            for x in range(img.shape[1]):
                if img[x,y] != 0 :
                    f.write("*")
                else : 
                    f.write("-")
            f.write("\n")
    f.close()


for i in range(20):
   # rectrangle:
    p1,p2, angle= randomRectrangle()
    img=rectrangle(p1,p2,angle)
    printImg("group19\\rectrangle"+str(i)+".txt", img)

    #triangle:
    x1,x2,x3= randomTriangle()
    img= triangle(x1,x2,x3)
    printImg("group19\\triangle"+str(i)+".txt", img)

    #circle:    
    x,y,r= randomCircle()
    img= circle(x,y,r)
    printImg("group19\\circle"+str(i)+".txt", img)
    


