import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	public static ArrayList<Message> CreateMessages(ArrayList<File> files, File labels, Dictionary dict) {
		File file = null;
		ArrayList<Message> messages = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			String fileName = file.getName().substring(0, file.getName().indexOf('.'));			
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				BufferedReader labelReader = new BufferedReader(new FileReader(labels));
				String line;
				// read messages from file and parse each line
				while ((line = br.readLine()) != null) {
					ArrayList<String> wordList = new ArrayList<>();
					line = line.replaceAll(
							"[-\"/=~!@#$%^&*#!&?'\\;*:(){}<>_.,]", " ")
							.toLowerCase();
					line = line.replace("\\", " ");
					String[] words = line.split("\\s+");
					// add all new words to dictionary:
					for (int j = 1; j < words.length; j++) {
						if (!wordList.contains(words[j])) {
							wordList.add(words[j]);
						}
					}
					
					
					//set classification
					Message newMsg = new Message();
					if (!fileName.equals("test")){
						newMsg.setClassification(Integer.parseInt(fileName));
					}
					else{
						newMsg.setClassification(Integer.parseInt(labelReader.readLine()));
					}
					for (int k = 0; k < wordList.size(); k++){
						newMsg.setWord(wordList.get(k), dict);
					}
					messages.add(newMsg);
				}

			} catch (IOException e) {
				System.err.println("error reading from file");
				System.err.println(e);
				System.exit(-1);
			}
		}
		return messages;
	}

	public static ArrayList<Message> createValidationMessages(
			ArrayList<Message> messages) {
		// TODO Auto-generated method stub
		return null;
	}

}