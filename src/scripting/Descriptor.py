#---------------------------------------------------------------
# Name:       Descriptor.py
# Purpose:    Downloads description for comets and stores them
# Author:     Mohamad Yassine
# Created:    22/05/2014
# Copyright:   (c) The Comet Project
# Python Version:   2.7.6
#---------------------------------------------------------------

import os
import re
import datetime
import urllib2

# This progeam uses The Horizon Batch interface to get data about each comet
# It generates a new a new comet ascii file with added references taken
# From the URL which are True Anomaly Angle (quantity 41) and the R.A & Dec (quantity 1).

logger = open("log-Descriptor.txt","w")
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Logger initialized\n")
logger.flush()

f1 = open("comet-list1.ascii","w") # Open two files for 2 results obtained
f2 = open("comet-list2.ascii","w") # Open two files for 2 results obtained

fo = open("comet-list.ascii","r")
def file_len(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1
# Getting the next date and verifying it
def dataVerify(date):
	end31 = [1, 3, 5, 7, 8, 10, 12] # Months End on => 31
	end30 = [4, 6, 9, 11] # Months End on => 30
	mydate = date.split("/")
	mydate[0] = int(mydate[0])
	mydate[1] = int(mydate[1])	
	mydate[2] = int(mydate[2])
	if(mydate[1] in end31): # Checking for months that end in 31
		if(mydate[2] == 31):
			if(mydate[1] == 12):
				mydate[1] = 1
				mydate[2] = 1
				mydate[0]+=1
			else:
				mydate[1]+=1
				mydate[2] = 1
		else:
			mydate[2]+=1
	elif(mydate[1] in end30): # Checking for months that end in 30
		if(mydate[2] == 30):
			mydate[1]+=1
			mydate[2] = 1
		else:
			mydate[2]+=1		
	else:# Checking for Leap year
		if(mydate[0] % 4 == 0 and mydate[0] % 100 != 0 or mydate[0] % 400 == 0):# Leap year
			if(mydate[2] == 29):
				mydate[2] = 1
				mydate[1] = 3
			else:
				mydate[1]+=1
		else:
			if(mydate[2] == 28):
				mydate[2] = 1
				mydate[1] = 3
			else:
				mydate[1]+=1
	return str(mydate[0])+"/"+str(mydate[1])+"/"+str(mydate[2])

logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Extracing Elements from file comet-list.ascii\n")
logger.flush()
# Extracting from File
leng = file_len("comet-list.ascii")

#Getting Comets and Dates
mylist = []
mystringlistfirst = []
mystringlistlast = []
for i in range(0,leng):
	s = fo.readline() # Read a line of input
	temp = s.split()
	line = temp[-1] # Get last element after we split the string by spaces /2012/08/26/f2/c4/p5/v1/PTF_201208262396_i_p_scie_t054503_u013924559_f02_p022694_c04.fits
	comet = temp[1] # Get Comet name
	mystringlistfirst.append(temp[0]+" "+temp[1]) # <= Adding the First part of the naming
	temp[0] = ''
	temp[1] = ''
	mystringlistlast.append(' '.join(temp)) # <= Adding the Second part of the naming
	elements = line.split("/")
	date = elements[1]+"/"+elements[2]+"/"+elements[3] # <= Getting the Date
	mylist.append(comet+" "+date) # <= Placing Date and Comet in a list
fo.close()

# Ephemeris Type [change] : 	OBSERVER
# Target Body [change] : 	Comet Name <= COMMAND='DES=Comet code;CAP<Year(getting closest Year);NOFLAG(Strictly the same name)'
# Observer Location [change] : 	Palomar Mountain--PTF [I41](Code: I41)
# Time Span [change] : 	Start=2012-09-13, Stop=2012-09-14, Step=1 d (in this particular case)
# Table Settings [change] : 	QUANTITIES=1,41 (1: RA & DEC/41: True anomaly angle)
i = 0 # To keep track of the rows
for comet in mylist:
	try:
		cometlist = comet.split(" ")
		initialdate = cometlist[1] # Get Start Date
		nextdate = dataVerify(cometlist[1]) # Get Stop Date
		cometsplit = cometlist[0].split("/")
		if(len(cometsplit[0]) > 1): # Get Comet code from the beginning
			# Attach dates and and comet to the url
			url = "http://ssd.jpl.nasa.gov/horizons_batch.cgi?batch=1&COMMAND=%27DES="+cometsplit[0]+"%3bCAP<"+initialdate.split("/")[0]+"%3bNOFRAG%27&MAKE_EPHEM=%27YES%27%20&TABLE_TYPE=%27OBSERVER%27&START_TIME=%27"+initialdate+"%27&STOP_TIME=%27"+nextdate+"%27&STEP_SIZE=%271%20d%27%20&QUANTITIES=%271,41%27&CSV_FORMAT=%27YES%27&CENTER=%27I41%27"
			req = urllib2.urlopen(url)
			text = req.read()
			text = text.split("\n") # Splitting Text file by \n
			text = filter(None,text) # Filtering empty elements 
			checker = False # <= To know when to add result 
			resultlist = [] # <= Store Lines in a list
			for line in text:
				result1 = re.search(r'\$\$SOE',line) # <= Get Start results
				result2 = re.search(r'\$\$EOE',line) # <= Get End results
				if not (result1 is None):
					checker = True
				if not (result2 is None):
					checker = False
					break
				if checker:
					resultlist.append(line) # As long as the Date is between result1 and result2 I append them to a list
			#parsing result
			# Seperate obtained results based on comma
			resultant1 = resultlist[1].split(",") # First Date
			resultant2 = resultlist[2].split(",") # Second Date
			Ra1 = resultant1[3] # Get Ra from first line
			Ra2 = resultant2[3] # Get Ra from Second line
			Dec1 = resultant1[4] # Get Dec from first line
			Dec2 = resultant2[4] # Get Dec from Second line
			Tanomaly1 = resultant1[-2] # Get True Angle Anomoly from first line
			Tanomaly2 = resultant2[-2] # Get True Angle Anomoly from Second line

			f1.write(mystringlistfirst[i]+" "+Ra1+" "+Dec1+" "+Tanomaly1+" "+mystringlistlast[i]+"\n")
			f2.write(mystringlistfirst[i]+" "+Ra2+" "+Dec2+" "+Tanomaly2+" "+mystringlistlast[i]+"\n")
		else: # Get Comet names that have one Character as Description
			cometcode1 = re.search(r'[A-Z]/[0-9]{4}',cometlist[0]) # Get first part C/2007VO53Spacewatch => C/2007
			cometcode2 = re.search(r'[A-Z]{1,3}([0-9]{1,4}|K|J)',cometlist[0]) # Get Second part C/2007VO53Spacewatch => VO53
			url = "http://ssd.jpl.nasa.gov/horizons_batch.cgi?batch=1&COMMAND=%27DES="+cometcode1.group()+"%20"+cometcode2.group()+"%3bCAP<"+initialdate.split("/")[0]+"%27&MAKE_EPHEM=%27YES%27%20&TABLE_TYPE=%27OBSERVER%27&START_TIME=%27"+initialdate+"%27&STOP_TIME=%27"+nextdate+"%27&STEP_SIZE=%271%20d%27%20&quantityTIES=%271,41%27&CSV_FORMAT=%27YES%27&CENTER=%27I41%27"
			req = urllib2.urlopen(url)
			text = req.read()
			text = text.split("\n") # Splitting Text file by \n
			text = filter(None,text) # Filtering empty elements 
			checker = False # <= To know when to add result 
			resultlist = [] # <= Store Lines in a list
			for line in text:
				result1 = re.search(r'\$\$SOE',line) # <= Get Start results
				result2 = re.search(r'\$\$EOE',line) # <= Get End results
				if not (result1 is None):
					checker = True
				if not (result2 is None):
					checker = False
					break
				if checker:
					resultlist.append(line) # As long as the Date is between result1 and result2 I append them to a list
			#parsing result
			# Seperate obtained results based on comma
			resultant1 = resultlist[1].split(",") # First Date
			resultant2 = resultlist[2].split(",") # Second Date
			Ra1 = resultant1[3] # Get Ra from first line
			Ra2 = resultant2[3] # Get Ra from Second line
			Dec1 = resultant1[4] # Get Dec from first line
			Dec2 = resultant2[4] # Get Dec from Second line
			Tanomaly1 = resultant1[-2] # Get True Angle Anomoly from first line
			Tanomaly2 = resultant2[-2] # Get True Angle Anomoly from Second line

			f1.write(mystringlistfirst[i]+" "+Ra1+" "+Dec1+" "+Tanomaly1+" "+mystringlistlast[i]+"\n")
			f2.write(mystringlistfirst[i]+" "+Ra2+" "+Dec2+" "+Tanomaly2+" "+mystringlistlast[i]+"\n")
			
		i+=1
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Added "+cometlist[0]+"\n")
		logger.flush()
		req.close()
	except IOError, e:
		#Some URLs do not exist
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Error! "+comet+" "+str(e)+"\n")
		logger.flush()
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Execution completed Successful\n")
logger.close()
f1.close()
f2.close()