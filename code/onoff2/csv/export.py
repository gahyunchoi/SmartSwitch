import pymongo
from pymongo import MongoClient
import os
import csv
from datetime import datetime,date,time,timedelta


# Count total Num
conn = pymongo.MongoClient()
db = conn.devices

total_count_cursor = db.tasks.aggregate(
   [
      {
         "$project": {
            "deviceName": 1,
            "numberOf": { "$size": "$HumidityLog" }
         }
      }
   ]
)
total_num = 0
for dic in total_count_cursor:
    total_num += dic["numberOf"]

print total_num






# Num of 
cursor = db.tasks.aggregate(
   [
      {
         "$project": {
            "deviceName": 1,
            "numberOf": "$ElecLog"
         }
      }
   ]
)

with open('exportdb.csv','wb') as myfile:
    wr = csv.writer(myfile,quoting=csv.QUOTE_ALL)   
    for dic in cursor:
        for i in xrange(0,len(dic['numberOf'])): 
            print i
            print dic['numberOf'][i]['Elec']
            print dic['numberOf'][i]['changedTime']
            #wr.writerow([total_num,3,'on','off'])
