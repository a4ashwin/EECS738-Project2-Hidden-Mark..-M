import java.io.*;
import java.util.*;
import java.util.stream.*;

//Class to implement the Hidden Markov model to predict the text and words.
public class Hmm {
	public static void main(String g[]){
		//Words to predict the next word
		String[] input = {"are", "WE"};
		String[] input2 = {"haste", "was"};
		String[] input3 = {"such", "beastly"};
		String[] input4 = {"the", "north"};
		String[] input5 = {"betwixt", "that"};
		String[] input6 = {"thou", "makest"};
		String[] input7= {"", "cat"};
		String[] input8 = {"says", "john"};
		String[] input9 = {"", ""};
		Hmm hmm=new Hmm();
		//method to train the model
		hmm=Hmm.getTrainedModel();
		ArrayList<String> predictedWords = new ArrayList<>();
		//Calling nextWord method to predict the word
		predictedWords.add(hmm.predictWord(input));
		predictedWords.add(hmm.predictWord(input2));
		predictedWords.add(hmm.predictWord(input3));
		predictedWords.add(hmm.predictWord(input4));
		predictedWords.add(hmm.predictWord(input5));
		predictedWords.add(hmm.predictWord(input6));
		predictedWords.add(hmm.predictWord(input7));
		predictedWords.add(hmm.predictWord(input8));
		predictedWords.add(hmm.predictWord(input9));
		System.out.println("Input: " + input[0] + " "+ input[1]+ "     Predicted: "+ predictedWords.get(0));
		System.out.println("Input: " + input2[0] + " "+ input2[1]+ "     Predicted: "+ predictedWords.get(1));
		System.out.println("Input: " + input3[0] + " "+ input3[1]+ "     Predicted: "+ predictedWords.get(2));
		System.out.println("Input: " + input4[0] + " "+ input4[1]+ "     Predicted: "+ predictedWords.get(3));
		System.out.println("Input: " + input5[0] + " "+ input5[1]+ "     Predicted: "+ predictedWords.get(4));
		System.out.println("Input: " + input6[0] + " "+ input6[1]+ "     Predicted: "+ predictedWords.get(5));
		System.out.println("Input: " + input7[0] + " "+ input7[1]+ "     Predicted: "+ predictedWords.get(6));
		System.out.println("Input: " + input8[0] + " "+ input8[1]+ "     Predicted: "+ predictedWords.get(7));
		System.out.println("Input: " + input9[0] + " "+ input9[1]+ "     Predicted: "+ predictedWords.get(8));

		//Generating 100 predicted words as new text Corpus.
		System.out.println("==========New Text==========");
		String newText = generateNewText(hmm);
		System.out.println(newText);
		try {
			//Saving the generated text and predicted words to the output file.
			FileWriter fw = new FileWriter("C:\\Stuff\\KU Study\\EECS 738 Machine Learning\\Projects\\EECS738-Project2-Hidden-Mark..-M\\data\\result\\output.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("==========Predicted words=========");
			bw.newLine();
			bw.write("Input: " + input[0] + " "+ input[1]+ "     Predicted: "+ predictedWords.get(0));
			bw.newLine();
			bw.write("Input: " + input2[0] + " "+ input2[1]+ "     Predicted: "+ predictedWords.get(1));
			bw.newLine();
			bw.write("Input: " + input3[0] + " "+ input3[1]+ "     Predicted: "+ predictedWords.get(2));
			bw.newLine();
			bw.write("Input: " + input4[0] + " "+ input4[1]+ "     Predicted: "+ predictedWords.get(3));
			bw.newLine();
			bw.write("Input: " + input5[0] + " "+ input5[1]+ "     Predicted: "+ predictedWords.get(4));
			bw.newLine();
			bw.write("Input: " + input6[0] + " "+ input6[1]+ "     Predicted: "+ predictedWords.get(5));
			bw.newLine();
			bw.write("Input: " + input7[0] + " "+ input7[1]+ "     Predicted: "+ predictedWords.get(6));
			bw.newLine();
			bw.write("Input: " + input8[0] + " "+ input8[1]+ "     Predicted: "+ predictedWords.get(7));
			bw.newLine();
			bw.write("Input: " + input9[0] + " "+ input9[1]+ "     Predicted: "+ predictedWords.get(8));
			bw.newLine();
			bw.write("==========New Text Corpus=========");
			bw.newLine();
			bw.write(newText);
			bw.newLine();
			bw.close();
		}catch (Exception e){
			System.out.println("IOException caught");
		}

	}
	//This method takes an inital word from the already existing data that the model is trained on and then after that, it predicts
	//the second word based on the first word, and then it predicts the next words based on the two previous words.
	private static String generateNewText(Hmm hmm) {
		String s="";
		String newText = "";
		//Takes a random word from the text that the model is trained on.
		String word1 = hmm.predictWord(s);
		newText += word1;
		String word2 = "";
		String newWord = null;
		//generates the next 99 words based on the previous two words of the predicting word.
		for (int i = 1; i < 100; i++) {
			newWord = hmm.predictWord(new String[]{word2, word1});
			newText += " "+newWord;
			word2=word1;
			word1=newWord;
		}
		return newText;
	}


	private Map<String, Long> firstWords = new HashMap<>();
	private Map<String, List<String>> secondWords = new HashMap<>();
	private Map<List<String>, List<String>> thirdWords = new HashMap<>();

	private Map<String, Double> firstWordsProbabilities = new HashMap<>();
	//secondWordsProbabilities is the Map where we store all the predicted words based on a single previous word.
	private Map<String, Map<String, Double>> secondWordsProbabilities = new HashMap<>();
	//twoWordsProbabilities is the Map where we store all the predicted words based on a single previous word.
	private Map<List<String>, Map<String, Double>> thirdWordsProbabilities = new HashMap<>();

	//Constructor of the model
	public Hmm() {

	}

	//This method will train the model on the existing data and then return the trained model.
	public static Hmm getTrainedModel(){
		Hmm model = new Hmm();
		model.startTraining();
		return model;
	}

	//This method predicts the next word based on 1 or 2 previous words provided.
	public String predictWord(String...previousWords){
		// result list that has the predicted words
		List<String> predictedWords = null;

		// words 1. and word 2.
		String w1 = null, w2 = null;
		// length of the array
		int lengthOfPreviousWords = previousWords.length;

		// if the input text contains more than 2 words, we choose the last 2 and convert them to lower case
		if (lengthOfPreviousWords > 2){
			w1 = previousWords[lengthOfPreviousWords - 2].trim().toLowerCase();
			w2 = previousWords[lengthOfPreviousWords - 1].trim().toLowerCase();
			// if exactly 2 words are given
		}else if(lengthOfPreviousWords == 2) {
			w1 = previousWords[0].trim().toLowerCase();
			w2 = previousWords[1].trim().toLowerCase();
			// if there's only one word given in the input text
		}else if (lengthOfPreviousWords == 1){
			w1 = previousWords[0].trim().toLowerCase();
		}
		// Now we will take a Map which will have the word and the probability of that word to be the next word for the given input word.
		Map<String, Double> wordProbabilityMap;
		// if only the first word is provided in the input. Cases like a sentence is just started.
		if (w1 != null && w2 == null){
			//We will pass the word to the get method and the method will return a Map with the next words
			// and their probabilities based on their occurrance while training.
			wordProbabilityMap = secondWordsProbabilities.get(w1);
			// if it is available
			if (wordProbabilityMap != null) {
				// getResultList sorts the predicted words based on their probability and makes a list and return it.
				predictedWords = getResultList(wordProbabilityMap);
			}
		}
		// if both the first word and second word are provided in the input.
		if (w1 != null && w2 != null ){
			//twoWordsProbabilities is the list where we store all the predicted words based on a single previous word.
			//We will pass both the word to the get method and the method will return a Map with the next words
			// and their probabilities based on their occurrance while training.
			wordProbabilityMap = thirdWordsProbabilities.get(convertWordsToList(w1, w2));
			if (wordProbabilityMap != null){
				// getResultList sorts the predicted words based on their probability and makes a list and return it.
				predictedWords = getResultList(wordProbabilityMap);
			}
			// If no words are predicted, then the case can occur that the second word is provided and first word is not.
			//In that case we will consider the second word as the only word and predict the next word based on that word.
			if (predictedWords == null || predictedWords.isEmpty()) {
				// Getting list of next words based on the second word.
				wordProbabilityMap = secondWordsProbabilities.get(w2);
				if (wordProbabilityMap != null) {
					// Getting the resulting list
					predictedWords = getResultList(wordProbabilityMap);
				}
			}
		}
		//If result is found, then we will return the first element of the list as it will have the highest probability after sorting.
		//In case of multiple words having same probability, we will still return the first word(A word can also be chosen randomly).
		// if no result is found, which means that the data doesn't contain the input word and can't predict the next word,
		//like if an empty string is passed, then we will pick a random word from the training text and return it.
		if(predictedWords !=null) return predictedWords.get(0).toString();
		else{
			//Making an arrayList from the map
			Object[] randomWords = firstWords.keySet().toArray();
			//Getting a random word from the list
			Object key = randomWords[new Random().nextInt(randomWords.length)];
			return key.toString();
		}
	}

	// getResultList converts the Map of predicted words into a List, then sorts the predicted words
	// based on their probability and return it.
	private List<String> getResultList(Map<String, Double> stringDoubleMap){
//		System.out.println("========RESULTS========");
		return new ArrayList<>(stringDoubleMap.entrySet())
				.stream()
				.sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // reverse sorting
				.peek(el -> System.out.print(""))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}
	//this method starts the training of the model based on the Shakespeare_data.csv
	public void startTraining() {
		try {
			List<String> samples = new ArrayList<String>();
			List<String> playerLines = new ArrayList<String>();
			BufferedReader reader;
			try {
				//Setting up the file path to read.
				reader = new BufferedReader(new FileReader(
						"C:\\Stuff\\KU Study\\EECS 738 Machine Learning\\Projects\\EECS738-Project2-Hidden-Mark..-M\\data\\external\\Shakespeare_data.csv"));
				//Reading the first line that has the column names, we will not be using it.
				String line = reader.readLine();
				//Reading all the lines
				while (line != null) {
					line = reader.readLine();
					samples.add(line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Looping on the samples for Data exploration.
			//We are going to take only the playerLines column in use for this project as we only need to predict the word based on the text.
			for (int current = 0; current < samples.size(); current++) {

				try {
					String t=samples.get(current).toString();
					//We will split the whole sample based on ",".
					String[] temp = t.split(",");
					//We know that last column is playerLine. But there can be "," in the player line also which will further
					//create another string for the text. Thus we will discard the first 5 values which are not required and
					//take the rest of the values which are text as sentences.
					if(temp.length > 5){
						for (int i = 5; i < temp.length; i++) {
							// We will remove the symbols present in the string.
							String clean = temp[i].trim().replaceAll("[.:;\"]*", "");
							//clean=clean.replaceAll(".","");
							if(clean!=null && !clean.equals("")) 	playerLines.add(clean);
						}
					}
				}catch(Exception e){
					System.out.println("");
				}

			}
			//System.out.println("Processing each sentences: "+playerLines.size());
			// process each sentence's words
			for (int i = 0; i < playerLines.size(); i++) {
				System.out.println(i);
				//We will pass the words in a sentence to the processSentences method which will then create the Maps for predicting words in Hidden Markov Model.
				processSentences(Arrays.asList(playerLines.get(i).split(" ")));
			}

			System.out.println("Training ended");
		}catch (Exception e){
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	//ProcessSentences method takes the list of words from a single sentence and create Maps of words which includes
	//words occurred after a single word, words occurred after a particular pair of words
	//words occurred in the start of a sentence along with their probability of occurrance.
	private void processSentences(List<String> sentence) {
		int len = sentence.size();
		// for each word in the list
		for (int i = 0; i < len; i++) {
			// get the current word
			String currentWord = sentence.get(i).toLowerCase().trim();

			// if its position is the first in the sentence
			if (i==0) {
				if(currentWord==null || currentWord.trim().equals("")) continue;
				// add it to the map and set the counter to 1, or if it is already there increment the counter by 1
				firstWords.computeIfPresent(currentWord, (k, v) -> v + 1);
				firstWords.putIfAbsent(currentWord, 1L);
				// if it is not the first word
			} else {
				// get the previous word
				String previousWord = sentence.get(i - 1).toLowerCase().trim();
				// if we are at the second position, we will add the word to the Map which has word predictions based on only on previous word.
				if (i == 1) {
					// add (previousToken, token) pair to the SecondPossibilities map
					addToSecondWordsList(previousWord, currentWord);
					// if word is after the two words, then we will add the word to the Map which has
					// predicted word based on the pair of previous words
				} else {
					String previousPreviousWord = sentence.get(i - 2).toLowerCase().trim();
					addToTwoWordList(previousPreviousWord, previousWord, currentWord);
				}
			}
		}
		//Now after our map is updated with the current sentence, we will calculate the probabilities for the predicted word occurance.
		// finding the probabilities for the First possible words
		Double firstWordsTotal = firstWords
				.values() // get values
				.stream() // stream
				.mapToDouble(Long::doubleValue) // convert them to double
				.sum(); // sum

		// now, divide each entry count to total count and find the probability
		for (Map.Entry<String, Long> entry : firstWords.entrySet()) {
			Double value = entry.getValue() / firstWordsTotal;
			firstWordsProbabilities.put(entry.getKey(), value);
		}
		// finding probability for the Second possible words
		for (Map.Entry<String, List<String>> entry : secondWords.entrySet()) {
			secondWordsProbabilities.put(entry.getKey(), getNextProbability(entry.getValue()));
		}
		// finding probability for the twoPossibleWords
		for (Map.Entry<List<String>, List<String>> entry : thirdWords.entrySet()) {
			thirdWordsProbabilities.put(entry.getKey(), getNextProbability(entry.getValue()));
		}
	}

	//This method calculates the probability of the word based on it's occurrance and divide it by the total number of words.
	private Map<String, Double> getNextProbability(List<String> words){
		Map<String, Long> wordCounts = new HashMap<>();
		for (String word : words) {
			wordCounts.computeIfPresent(word, (k, v) -> v + 1);
			wordCounts.putIfAbsent(word, 1L);
		}

		Map<String, Double> probs = new HashMap<>();
		double len = words.size();
		//Calculating the probability and updating a map with word and probabilities.
		for (Map.Entry<String, Long> entry : wordCounts.entrySet()) {
			probs.put(entry.getKey(), entry.getValue() / len);
		}

		return probs;
	}

	//this method adds next word in the Map of word occurance based on a single word.
	private void addToSecondWordsList(String previousWord, String word) {
		List<String> valAbsent = convertWordsToList(word);
		//Put word in Map if not present
		secondWords.putIfAbsent(previousWord, valAbsent);
		//If word is present, increase the couter by 1
		secondWords.computeIfPresent(previousWord, (k, v) -> {
			ArrayList<String> temp = new ArrayList<>(v);
			temp.add(word);
			return temp;
		});
	}

	//this method adds next word in the Map of word occurance based on a pair of words.
	private void addToTwoWordList(String key1, String key2, String value) {
		List<String> key = convertWordsToList(key1, key2);
		List<String> val = convertWordsToList(value);
		List<String> list = thirdWords.get(key);
		//Put word in Map if not present
		if (list != null ){
			list.add(value);
		}else{
			thirdWords.putIfAbsent(key, val);
		}
	}
	// returns an array list from an array
	private List<String> convertWordsToList(String...val){
		return new ArrayList<>(Arrays.asList(val));
	}
}