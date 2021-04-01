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
 Hidden Markov Models (HMMs) are a class of probabilistic graphical model that allow us to predict a sequence of unknown (hidden) variables from a set of observed variables. 	
 In text predicting, HMM can be used as word/words can be predicted by the help of previous word that have been provided. The predicted word will be a hidden state/variables and the provided words will be the observed variable.
 In this project, we are going to predict the word based on at most two preceding words that are. Also, we will consider the cases like only one preceding word is provided or no preceding words are provided. The idea is to extract the text data given in the Shakespeare_data.csv file's "playerLine" column, then process the words in the sentences to train the Hidden Markov model based on the probability of occurance of a word after a sequence of words.
 
 Detailed steps:
 - First we import the data from Shakespeare_data.csv file and extract the "playerLine" column's values one by one.
 - We prepare sentences from these values, if one value has "," in between, we will split the sentences and consider them to be different sentences because we cannot predict the word after the "," in real life based on the words before ",".
 - Then we will remove the special characters like "?", ":", ";", ".", etc. from the end of the sentences.
 - Then we will process these sequence of words to train the HMM model.
 - Our model takes a sentence and does the following processing.
	1. If the word is first word in the sentence, it will add it to the list of First words and maintain a count of it.
	2. If a word is at second position in the sentecne, it will add it to another list which have words preceded by a single word. It also maintains the probability of occurrance of that word corresponding to the first word.
	3. If a word is after two positions in the sentecne, it will add it to another list which have words preceded by a pair of words. It also maintains the probability of occurrance of that particular word corresponding to that specific pair of words.
	4. If a word is preceded by more than 2 words, we will consider only the closest two preceding words and discard the rest of the words.
 - When we send a text to the trained model, it will take at most the last two words of the sentence and pass it to the model. The model then checks the existing pairs and if it finds a match, it will return the word with the highest probability of occurance while training. If more than on word have the same probability of occurance while training, the first word will be predicted by default. The process is same for only one preceding word. If there are no preceding words provided, then the model predicts the word from the list of first words randomly.

 Result:
 We have successfully implemented the Hidden Markov model algorithm to predict the text data based on a couple of preceding words. Also, we have generated a text corpus with the help of implemented model as shown below:
 
==========Predicted words=========
Input: are WE     Predicted: all
Input: haste was     Predicted: hot
Input: such beastly     Predicted: shameless
Input: the north     Predicted: of
Input: betwixt that     Predicted: holmedon
Input: thou makest     Predicted: me
Input:  cat     Predicted: open
Input: says john     Predicted: duke
Input:       Predicted: 
==========New Text Corpus=========
presently you have been all this can i speak not a word with you take the chain and bid that strumpet egregious ransom him to his side unknown himself best knows but strangely-visited people negligent student! learn her by heart behalf of the world here comes the townsmen on procession cassius aspect to the king is not so with civil and uncivil arms and strength could equal them break your own semblance endanger my soul gratis? at a distance enchanting all that lives must die to-morrow? solus thing hereafter proud of their division troilus methinks mounted the troyan walls is't                                                                                                   

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
