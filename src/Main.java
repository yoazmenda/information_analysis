import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {

	public static void main(String argv[]) throws IOException {
		// command line validation
		if (argv.length != 5) {
			System.err
					.println("input error: usage example: tree_learn traindir/ test.examples 25 7 output.txt");
			System.err.printf("argv length: %d\n", argv.length);
			System.exit(-1);
		}

		// get training set and test files
		File trainDir = new File(argv[0]);
		File[] dirents = trainDir.listFiles();
		ArrayList<File> trainingSetFiles = new ArrayList<>();
		ArrayList<File> files = new ArrayList<>();
		File testExamples = null;
		File testLabels = null;
		for (int i = 0; i < dirents.length; i++) {
			File dirent = dirents[i];
			if (dirent.getName().contains(".train")) {
				trainingSetFiles.add(dirent);
			}
			if (dirent.getName().contains(".examples")) {
				testExamples = dirent;
			}
			if (dirent.getName().contains(".labels")) {
				testLabels = dirent;
			}
		}
		for (int i = 0; i < trainingSetFiles.size(); i++) {
			files.add(trainingSetFiles.get(i));
		}
		files.add(testExamples);
		System.out.println(files);
		//int numOfClassifications = trainingSetFiles.size();
		double validationPercantage = Double.parseDouble(argv[2]) / 100;
		//int L = Integer.parseInt(argv[3]);
		File output = new File(argv[4]);
		if (!output.exists()) {
			output.createNewFile();
		}

		// read files and build dictionary
		FileWriter fw = new FileWriter(output.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(new Date().toString() + "\n");
		bw.write("Learning results:\n");
		Dictionary dict = new Dictionary(files);
		dict.printDictionary();

		// create messages objects(for learning and validation)
		ArrayList<Message> messages = Parser.CreateMessages(files, testLabels,dict);
	
//		// move some to validation
//		ArrayList<Message> validationMessages = new ArrayList<Message>();
//		int numOfValidation = (int)Math.floor(messages.size() * validationPercantage);
//		for (int i = 0; i < numOfValidation; i++) {
//			int rand = (int) Math.floor(Math.random() * (messages.size()));
//			System.out.printf("range: %d, rand: %d\n", messages.size(), rand);
//			Message temp = messages.get(i);
//			messages.remove(i);
//			validationMessages.add(temp);
//		}
//		
//		//build tree with different sizes and take maximum over validation
		for (int i = 0; i< messages.size(); i++){
			messages.get(i).print(dict);
		}
		
		
		
		bw.close();
	}// end main

}

