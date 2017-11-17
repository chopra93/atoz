import re;
import MySQLdb;
db = MySQLdb.connect("localhost","root","root","ifsc")
cursor = db.cursor()
with open('state.txt') as f:
	for line in f:
		txt=re.split(r'\t+', line.rstrip('\n'))
		try:
			detail="INSERT INTO `state_information`(`name`,`enabled`)values(\""+txt[0]+"\",1);"
			print detail
			cursor.execute(detail)
			db.commit()
			openfile = open("output-state.sql", "a")
			openfile.write(detail+"\n")
			openfile.close()
		except:
			db.rollback()
			print "error"
db.close()
