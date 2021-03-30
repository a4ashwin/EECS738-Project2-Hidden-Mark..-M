# EECS738---Project-2--- Hidden Mark.. M..

 Prerequisites
 Python 8, IntelliJ

 Introduction:
 In this project, we are going to build a Hidden Markov Model to be able to programmatically
	1. Generate new text from the text corpus
	2. Perform text prediction given a sequence of words
 
 Datasets used:
 1. Shakespeare plays
	This is a dataset comprised of all of Shakespeare's plays. It includes the following:
	1. The first column is the Data-Line, it just keeps track of all the rows there are.
	2. The second column is the play that the lines are from.
	3. The third column is the actual line being spoken at any given time.
	4. The fourth column is the Act-Scene-Line from which any given line is from.
	5. The fifth column is the player who is saying any given line.
	6. The sixth column is the line being spoken.


 Idea:
 We are going to implement the Hidden Markov Model algorithm which will predict the text.
	

 Process:
 - Download the data from https://www.kaggle.com/kingburrito666/shakespeare-plays
 - Load the dataset into Java Arraylist.
 - Drop the columns that are not significant in predicting the words.
 - 

 Result:
 We have successfully implemented the Hidden Markov model algorithm to predict the text data based on a couple of preceding words. 

 Built With:
 Java 8
 IntelliJ

 Authors:
 Ashwin Rathore

 License:
 This project is created for Course EECS 738 Assignment for University of Kansas.

 Acknowledgments:
 https://www.kaggle.com/kingburrito666/shakespeare-plays
 http://www.adeveloperdiary.com/data-science/machine-learning/introduction-to-hidden-markov-model/
 https://medium.com/@Ayra_Lux/hidden-markov-models-part-2-the-decoding-problem-c628ba474e69
