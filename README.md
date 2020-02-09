# CoreJavaXMLParcing
This project is created to learn core java, parcing, serialization, threads, jdbc etc.
This project is to check the incoming file name and file data. If the file name is same within the particular time slot the reject the incoming file.
If the file is new then parce the file add some rules I have added the rules like length of all the columns which are to be inserted in the databasae.
If the length is within the range the add the data into the valid data folder and into database too.
If the data is invalid then add the data into invalid data folder.
Moniter Thread will check the incoming files after every 30 seconds.
