import re;
import MySQLdb;
db = MySQLdb.connect("localhost","root","","ifsc")
cursor = db.cursor()
with open('pincode_state_city.txt') as f:
	for line in f:
		txt=re.split(r'\t+', line.rstrip('\n'))
		try:
			detail="INSERT INTO `pincode_information`(`pincode`,`state`,`district`,`city`)values(\""+txt[0]+"\",\""+txt[3]+"\",\""+txt[2]+"\",\""+txt[1]+"\");"
			print detail
			cursor.execute(detail)
			db.commit()
			openfile = open("output-pincode_state_city.sql", "a")
			openfile.write(detail+"\n")
			openfile.close()
		except:
			db.rollback()
			print "error"
db.close()
