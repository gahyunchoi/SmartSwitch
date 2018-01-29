import pymongo
from pymongo import MongoClient
import os
import csv
from datetime import datetime,date,time,timedelta


class Holidays:
    def __is_working_day__(self, dt, holidays):
        return dt.weekday() < 5 and dt.date() not in holidays

    def __get_xday_before_workingDate__(self, now, holidays, pastDay=0):
        while not pastDay == -1:
            if self.__is_working_day__(now, holidays):
                pastDay -= 1
                if pastDay == -1: break
            now = now - timedelta(days=1)
        return now.date()

    def __get_before_workingDate__(self, now, holidays):
        while not self.__is_working_day__(now, holidays):
            now = now - timedelta(days=1)
        return now.date()

    def __get_after_workingDate__(self, now, holidays):
        while not self.__is_working_day__(now, holidays):
            now = now + timedelta(days=1)
        return now.date()


class KoreanBDay(Holidays):
    thisYear = datetime.now().year
    holidays = []

    def __init__(self):
        self.thisYear = datetime.now().year

        self.holidays = [
            datetime(self.thisYear, 1, 1).date(),   
            datetime(self.thisYear, 2, 7).date(),   
            datetime(self.thisYear, 2, 8).date(),   
            datetime(self.thisYear, 2, 9).date(),    
            datetime(self.thisYear, 3, 1).date(),   
            datetime(self.thisYear, 5, 5).date(),  
            datetime(self.thisYear, 5, 14).date(), 
            datetime(self.thisYear, 6, 6).date(),    
            datetime(self.thisYear, 8, 15).date(),     
            datetime(self.thisYear, 9, 14).date(),  
            datetime(self.thisYear, 9, 15).date(),     
            datetime(self.thisYear, 9, 16).date(),    
            datetime(self.thisYear, 10, 3).date(),     
            datetime(self.thisYear, 10, 9).date(),   
            datetime(self.thisYear, 12, 25).date(),   

            datetime(self.thisYear, 2, 10).date(), 
        ]

    def print_year(self):
        print self.thisYear

    def reset_year(self):
        self.thisYear = datetime.now().year

    def is_working_day(self, dt, pastDay=0):
        return self.__is_working_day__(dt - timedelta(days=pastDay), self.holidays)

    def get_before_workingDate(self, now, pastDay=0):
        return self.__get_before_workingDate__(now - timedelta(days=pastDay), self.holidays)

    def get_xday_before_workingDate(self, now, pastDay=0):
        return self.__get_xday_before_workingDate__(now, self.holidays, pastDay)

    def get_after_workingDate(self, now, pastDay=0):
        return self.__get_after_workingDate__(now - timedelta(days=pastDay), self.holidays)

    def print_holidays(self):
        print self.holidays





        
def Week(self):
    weekno = self.weekday()
    if weekno<5:
        return 0
    else:
        return 1
        








starttime = datetime.combine(date.today()-timedelta(days=30),time.min)
endtime = datetime.combine(date.today()-timedelta(days=1),time.min)






conn = pymongo.MongoClient()
db = conn.devices
cursor = db.tasks.find()
flag = starttime
laststatusLog = 0
        #wr.writerow([dic['deviceName'],dic['statusLog'][i]['changedTime']])
        

with open('exportdb.csv','wb') as myfile:
    wr = csv.writer(myfile,quoting=csv.QUOTE_ALL)   
    #wr.writerow([2016,3,'on','off'])
    for dic in cursor:
        j = 0
        starttime = datetime.combine(date.today()-timedelta(days=29),time.min)
        endtime = datetime.combine(date.today()-timedelta(days=0),time.min)
        for i in xrange(0,len(dic['statusLog'])): 
            if dic['statusLog'][i]['changedTime']<endtime and starttime<dic['statusLog'][i]['changedTime']:
                if j==0:
                    j+=1
                    #print dic['statusLog'][i]['changedTime']
                    flag = dic['statusLog'][i]['changedTime']
                    while starttime<dic['statusLog'][i]['changedTime']:
                        #print starttime
                        laststatusLog = dic['statusLog'][i]['status']
                        if dic['statusLog'][i]['status']=='1':
                            wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),0])
                            #wr.writerow([starttime,0])
                            #wr.writerow([0])
                        else:
                            wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),1])  
                            #wr.writerow([starttime,1])
                            #wr.writerow([1])  
                        starttime += timedelta(minutes=10)
                else:
                    if dic['statusLog'][i]['changedTime']-flag>timedelta(minutes=10):
                        while starttime<dic['statusLog'][i]['changedTime']:
                            flag = starttime
                            laststatusLog = dic['statusLog'][i]['status']
                            #print starttime
                            if dic['statusLog'][i]['status']=='1':
                                wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),0])
                                #wr.writerow([0])
                                #wr.writerow([starttime,0])
                            else: 
                                wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),1]) 
                                #wr.writerow([1])              
                                #wr.writerow([starttime,1])   
                            starttime += timedelta(minutes=10)
                            wr.writerow([1])
                            #wr.writerow([starttime,1])
        j+=1
        while starttime<endtime:
            if laststatusLog=='0':
                wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),0])
                #wr.writerow([0])
                #wr.writerow([starttime,0])
            else:
                wr.writerow([dic['deviceName'],starttime.time(),Week(starttime),1])
                #wr.writerow([1])
                #wr.writerow([starttime,1])
            #print starttime
            starttime+=timedelta(minutes=10)
        #for c in range(0,1008):
            #print starttime + timedelta(minutes=c*10)
            #wr.writerow([dic['deviceName'],starttime + timedelta(minutes=c*10)])
            