#-------------------------------------------------------------
# Name:       downloader.py
# Purpose:    Downloads ctlg and reimg image files using wget
# Author:     Mohamad Yassine
# Created:    15/05/2014
# Copyright:   (c) The Comet Project
# Python Version:   2.7.6
#-------------------------------------------------------------

import datetime
import os
import re

url = "http://kanaloa.ipac.caltech.edu/ibe/data/ptf/dev_ims/ptf_l2_img/"
print "The website "+ url + " requires password"
username = raw_input("Username: ") # input Username
password = raw_input("Password: ") # input Password

#Creating Directories and Subdirectories to save our files
Dir = "Files"
ft = "fits"
cg = "ctlg"
if not os.path.exists(Dir): 
	os.makedirs(Dir)
if not os.path.exists(Dir+os.sep+ft): 
	os.makedirs(Dir+os.sep+ft)
if not os.path.exists(Dir+os.sep+cg): 
	os.makedirs(Dir+os.sep+cg)

flink = open("links.txt","r")
logger = open("log-downloader.txt","w")
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Logger initialized\n")
logger.flush()

for link in flink:
	# Getting File name 
	name = link.split("/")[-1]
	logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Downloading "+link+"\n")
	logger.flush()
	
	ref = re.search(r'.*refimg\.fits',name) # Get Refimg Link
	ctlg = re.search(r'.*sexcat\.ctlg',name) # Get Ctlg link
	if not (ref is None):
		# Downloading Ref files and store it in Ctlg folder
		os.system("wget --user="+username+" --password="+password+" -P "+Dir+os.sep+ft+os.sep+" "+link)
	elif not (ctlg is None):
		# Downloading Ctlg file and store it in Ctlg folder
		os.system("wget --user="+username+" --password="+password+" -P "+Dir+os.sep+cg+os.sep+" "+link)

	logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Downloading "+link+" Successful\n")
	logger.flush()
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Execution completed Successful\n")
logger.close()
flink.close()