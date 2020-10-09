#!/bin/python
import urllib2
import re
from HTMLParser import HTMLParser
from htmlentitydefs import name2codepoint

class MyHTMLParser(HTMLParser):
	downloadlinks = [] 
	def handle_starttag(self, tag, attrs):
		if(tag=="a"):
			if attrs[0][0] == "href":
				link1 = re.search(r'.*_3[5-9][0-9]\.mp3',attrs[0][1])
				if not (link1 is None):
					downloadlinks.append(attrs[0][1])

parser = MyHTMLParser()
url2 = "https://archive.org/"
url = "https://archive.org/details/Armin_van_Buuren_A_State_of_Trance_001-499"
req = urllib2.urlopen(url)
text = req.read()
parser.feed(text)

Dir= "asot"
if not os.path.exists(Dir): 
	os.makedirs(Dir)

for link in parser.downloadlinks:
	req = urllib2.urlopen(url1+link)
	with open(Dir+os.sep+link.split("/")[-1], "w") as code:
		code.write(req.read) # Writting the data to a filed