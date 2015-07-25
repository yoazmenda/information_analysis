import java.io.*;
import java.util.ArrayList;

//import java.math.*;

public class Dictionary {

	private ArrayList<String> wordList;
	private int wordCount;

	// parses files. builds dictionary. build messages.
	public Dictionary(ArrayList<File> files) {
		File file = null;
		wordList = new ArrayList<>();
		wordCount = 0;
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				// read messages from file and parse each line
				while ((line = br.readLine()) != null) {
					line = line.replaceAll(
							"[-\"/=~!@#$%^&*#!&?'\\;*:(){}<>_.,]", " ")
							.toLowerCase();
					line = line.replace("\\", " ");
					String[] words = line.split("\\s+");
					// add all new words to dictionary:
					for (int j = 1; j < words.length; j++) {
						if (!wordList.contains(words[j])) {
							wordList.add(wordCount++, words[j]);
						}
					}
				}

			} catch (IOException e) {
				System.err.println("error reading from file");
				System.err.println(e);
				System.exit(-1);
			}
		}

	}

	public int lookupByWord(String str) {
		return wordList.indexOf(str);
	}

	public String lookupByIndex(int n) {
		return wordList.get(n);
	}


	public ArrayList<String> getWords() {
		return wordList;
	}
	
	public int getCout(){
		return wordCount;
	}

}