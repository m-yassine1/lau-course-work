#-------------------------------------------------------------
# Name:       output.py
# Purpose:    Generates an output file with a certain syntax
# Author:     Mohamad Yassine
# Created:    15/05/2014
# Copyright:   (c) The Comet Project
# Python Version:   2.7.8
#-------------------------------------------------------------

import datetime
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

url = "http://kanaloa.ipac.caltech.edu/ibe/data/ptf/dev_ims/ptf_l2_img/"
print "The website "+ url + " requires password"
password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
username = raw_input("Username: ") # input Username
password = raw_input("Password: ") # input Password

# Creating password manager so we don't prompt keep on asking for username and password.
password_mgr.add_password("Please Login", url, username, password)

handler = urllib2.HTTPBasicAuthHandler(password_mgr)

# create "opener" (OpenerDirector instance)
opener = urllib2.build_opener(handler)	

logger = open("log-output.txt","w")
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Logger initialized\n")
logger.flush()

fout = open("output.txt","w")
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] output.txt initialized\n")
logger.flush()

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
row = []
for i in range(0,leng):
	s = fo.readline() # Read a line of input
	line = s.split() # Store in an array after seperating by space
	cometname = line[1] # Get comet name
	fits = line[-1].split('/')[-1] # Get /2012/08/27/f2/c11/p5/v1/PTF_201208273816_i_p_scie_t090931_u013932800_f02_p022596_c11.fits => PTF_201208273816_i_p_scie_t090931_u013932800_f02_p022596_c11.fits
	temp = fits.split('_') # Split the name by _ so we can change it PTF_201208273816_i_p_scie_t090931_u013932800_f02_p022596_c11.fits => [PTF , 201208273816 , i , p , scie , t090931 , u013932800 , f02 , p022596 , c11.fits]
	temp[2] = 'c' # Change i -> c
	temp[-1] = temp[-1].replace("fits","ctlg") # Change fits => ctlg
	row.append((cometname+" "+line[6]+" "+line[7]+" "+line[4]+" "+fits+" "+'_'.join(temp))+" ")

fo.seek(0,0)
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
	links.append(s) # Create link
i=0
for link in links:
	try:
		fout.write(str(i+1)+" ")
		# Creating password manager so we don't prompt keep on asking for username and password.
		password_mgr.add_password("Please Login", link, username, password)

		handler = urllib2.HTTPBasicAuthHandler(password_mgr)
		# create "opener" (OpenerDirector instance)
		opener = urllib2.build_opener(handler)	
		# use the opener to fetch a URL
		opener.open(link)
		
		# Install the opener.
		# Now all calls to urllib2.urlopen use our opener.
		urllib2.install_opener(opener)
		req = urllib2.urlopen(link) # openning request
		html = req.read() # reading HTML file
		parser.feed(html) # parsing html file
		
		fout.write(row[i]) # Appending to the file
		fout.flush()
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Adding Fits from "+link+" index "+str(i+1)+"\n")
		logger.flush()

		#Adding Fits Link to the file
		fout.write(parser.downloadlinksFits[0].split("/")[-1]+" ")
		if(len(parser.downloadlinksFits) > 1):
			for j in range(1,len(parser.downloadlinksFits)):
				logger.write("Also found "+parser.downloadlinksFits[j]+"\n")
				logger.flush()
		fout.flush()
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Adding Ctlg from "+link+" index "+str(i+1)+"\n")
		logger.flush()
		
		#Adding Ctlg Link to the file
		fout.write(parser.downloadlinksCtlg[0].split("/")[-1]+" ")
		if(len(parser.downloadlinksCtlg) > 1):
			for j in range(1,len(parser.downloadlinksCtlg)):
				logger.write("Also found "+parser.downloadlinksCtlg[j]+"\n")
				logger.flush()
		
		req.close()
		parser.downloadlinksCtlg = [] # Reset of Ctlg list
		parser.downloadlinksFits = [] # Reset of Fits list
		print "*"
	except IOError, e:
		#Some URLs do not exist
		logger.write("["+str(datetime.datetime.now().date())+" "+str(datetime.datetime.now().time()).split('.')[0]+"] Error index "+str(i+1)+"! link: "+link+" "+str(e)+"\n")
	fout.write("\n")
	fout.flush()
	i+=1
logger.write("["+str(datetime.datetime.now().date()) +" "+str(datetime.datetime.now().time()).split('.')[0]+"] Execution completed Successful\n")
fout.close()
logger.close()
parser.close()