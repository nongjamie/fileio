# fileio

Task | Time
----------------------------------------|----------:
Copy a file one byte at a time | 3.358419 sec
Copy the file using a byte array of size 1 KB | 0.008515 sec
Copy a file using a byte array of size 4 KB | 0.004244 sec
Copy a file using a byte array of size 64KB | 0.001927 sec
Copy a file by using BufferedReader and PrintWriter to copy lines of text | 0.081241 sec

###Why "Copy a file one byte at a time" is the slowest?

Ans : Because it copy one byte by one byte but the another tasks copy more than one byte at a time , so copy one byte should be the slowest.

**I use MacbookPro to test the task.
