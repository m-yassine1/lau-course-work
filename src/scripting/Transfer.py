#---------------------------------------------------------------
# Name:       transfer.py
# Purpose:    Downloads ctlg and reimg image files using urlib2
# Author:     Mohamad Yassine
# Created:    15/05/2014
# Copyright:   (c) The Comet Project
# Python Version:   2.7.6
#---------------------------------------------------------------

import datetime
import os
import re
import urllib2
from htmlentitydefs import name2codepoint
from HTMLParser import HTMLParser

#HTML Parser
class MyHTMLParser(HTMLParser):
	# Using lists in case there are more than one file
	downloadlinksFits = []  
	downloadlinksCtlg = []
	def handle_starttag(self, tag, attrs):
		if(tag=="a"):
			if attrs[0][0]=="href":
				#Extracting links that contain refimg or sexcat using regulare expression
				link1 = re.search(r'.*refimg\.fits',attrs[0][1])
				link2 = re.search(r'.*sexcat\.ctlg',attrs[0][1])
				if not (link1 is None):
					#adding Fits file found to a list
					self.downloadlinksFits.append(attrs[0][1])
				elif not (link2 is None):
					#adding Ctlg file found to a list
					self.downloadlinksCtlg.append(attrs[0][1])
parser = MyHTMLParser()

#Creating Directory to store everything in
Dir = "Space"
ft = "fits"
cg = "ctlg"
if not os.path.exists(Dir): 
	os.makedirs(Dir)
if not os.path.exists(Dir+os.sep+ft): 
	os.makedirs(Dir+os.sep+ft)
if not os.path.exists(Dir+os.sep+cg): 
	os.makedirs(Dir+os.sep+cg)
url = "http://kanaloa.ipac.caltech.edu/ibe/data/ptf/dev_ims/ptf_l2_img/"
logger = open("log-transfer.txt","w")
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Logger initialized\n")
logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Created Directories\n")
logger.flush()

# create a password manager
password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()

print "The website "+ url + " requires password"
username = raw_input("Username: ") # input Username
password = raw_input("Password: ") # input Password

# Creating password manager so we don't prompt keep on asking for username and password.
password_mgr.add_password("Please Login", url, username, password)

handler = urllib2.HTTPBasicAuthHandler(password_mgr)

# create "opener" (OpenerDirector instance)
opener = urllib2.build_opener(handler)	

# File Length
fo = open("comet-list.ascii","r")
def file_len(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
    return i + 1

logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Extracing Elements from file comet-list.ascii\n")
logger.flush()
# Extracting from File
leng = file_len("comet-list.ascii")
plist=[]
flist=[]
for i in range(0,leng):
	s = fo.readline() # Read a line of input
	line = s.split()[-1] # Get last element after we split the string by spaces /2012/08/26/f2/c4/p5/v1/PTF_201208262396_i_p_scie_t054503_u013924559_f02_p022694_c04.fits
	fmatch = re.search(r'f[1-2]',line) # Extracting f2
	pmatch = re.search(r'p[0-9]+_c[0-9]+\.fits',line) # Extracting last part of the string p022694_c04.fits
	flist.append(fmatch.group(0))
	plist.append(pmatch.group(0))
fo.close()

logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Generating Links\n")
logger.flush()
# Generating Links
links=[];
for i in range(0,len(plist)):
	s = plist[i].split('_') # splitting p022694_c04.fits => [p022694,c04]
	num1 = int(re.search(r'[0-9]+',s[0]).group()) # Extracting from p022694 => 22694 the int is used to remove trailing 0s
	num2 = int(re.search(r'[0-9]+',s[1]).group()) # Extracting from c04.fits => 4 the int is used to remove trailing 0s
	s = url+"d"+str(num1)+"/"+flist[i]+"/c"+str(num2)+"/"
	if s not in links:
		links.append(s) # Create link

logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Downloads beginning\n")
logger.flush()
# Test links and Download files

list_fits = []
list_ctlg = []
for link in links:
	try:
		# use the opener to fetch a URL
		opener.open(link)

		# Install the opener.
		# Now all calls to urllib2.urlopen use our opener.
		urllib2.install_opener(opener)

		req = urllib2.urlopen(link) # openning request
		html = req.read() # reading HTML file
		parser.feed(html) # parsing html file
		
		# Downloading Fits files
		i=0
		for fits in parser.downloadlinksFits:
			# Check for duplicates for Refimg by checking if it is already in the list
			if fits in list_fits:
				continue
			list_fits.append(fits)
			filereq = urllib2.urlopen(link+fits) # fetching link for download
			logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Downloading of "+fits+" begun\n")
			logger.flush()
			data = filereq.read() # Downloading Fits file
			#Check For Duplicates for fits
			if i == 0: #in case 
				logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Writing "+fits+" to a file\n")
				logger.flush()
				with open(Dir+os.sep+ft+os.sep+fits, "wb") as code:
					code.write(data) # Writting the data to a file
			else:
				logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Download of "+fits+" Begun <= More Files at "+link+"\n")
				logger.flush()
				with open(Dir+os.sep+ft+os.sep+fits, "wb") as code:
					code.write(data) # Writting the data to a file
			logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Download of "+fits+" Successful!\n")
			logger.flush()
			i+=1 # increment i to check for duplicates
		
		# Downloading Ctlg files
		i=0
		for ctlg in parser.downloadlinksCtlg:
			# Check for duplicates for sexCtlg by checking if it is already in the list
			if ctlg in list_ctlg:
				continue
			list_ctlg.append(ctlg)

			filereq = urllib2.urlopen(link+ctlg) # fetching link for download
			logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Downloading of "+ctlg+" begun\n")
			logger.flush()
			data = filereq.read() # Downloading Ctlg file
			# Check For Duplicates for Ctlg
			if i == 0:
				logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Writing "+ctlg+" to a file\n")
				logger.flush()
				with open(Dir+os.sep+cg+os.sep+ctlg, "wb") as code:
					code.write(data) # Writting the data to a file
			else:
				logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Download of "+ctlg+" Begun <= More Files at "+link+"\n")
				logger.flush()
				with open(Dir+os.sep+cg+os.sep+ctlg, "wb") as code:
					code.write(data) # Writting the data to a file
				logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Download of "+ctlg+" Successful\n")
				logger.flush()
			i+=1 # increment i to check for duplicates 
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Download Completed from"+link+"\n")
		logger.flush()

		parser.downloadlinksCtlg = [] # Reset of the Ctlg array
		parser.downloadlinksFits = [] # Reset of Fits array
		print "*"
	except IOError, e:
		#Some URLs do not exist
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Error! link: "+link+" "+str(e)+"\n")	

logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Execution completed Successful\n")
parser.close()
logger.close()