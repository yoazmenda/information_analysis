import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
	public static Dictionary dict;
	public static int numOfClassifications = 0;
	public static int MAXCLASSIFICATION = 50;
	
	
	
	
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
		numOfClassifications = trainingSetFiles.size();
		for (int i = 0; i < numOfClassifications; i++) {
			files.add(trainingSetFiles.get(i));
			
		}
		if (testExamples == null){
			testExamples = new File(argv[1]);			
		}
		files.add(testExamples);		
		double validationPercantage = Double.parseDouble(argv[2]) / 100;
		int L = Integer.parseInt(argv[3]);
		File output = new File(argv[4]);
		if (!output.exists()) {
			output.createNewFile();
		}

		// read all files and build dictionary
		System.out.println("reading files from disk...");
		FileWriter fw = new FileWriter(output.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(new Date().toString() + "\n");
		bw.write("Learning results:\n");
		dict = new Dictionary(files);		

		//create messages objects from all files (test also)(for learning and validation)
		System.out.println("creating and parsing messages...");
		ArrayList<Message> messages = Parser.CreateMessages(files, testLabels);		
		
		
		//divide to testing and learning messages:		
		ArrayList<Message> testingMessages = new ArrayList<Message>();
		Message temp = null;
		for (int i = 0; i<messages.size(); i ++){
			temp = messages.get(i);
			if (temp.isForTesting()){				
				testingMessages.add(temp);
			}
		}
		for (int i = 0; i < testingMessages.size(); i++){
			messages.remove(testingMessages.get(i));
		}
		

		
		
		
	
		// move some of training messages to be validation messages (according to validationPercantage parameter)
		ArrayList<Message> validationMessages = new ArrayList<Message>();
		int numOfValidation = (int)Math.floor(messages.size() * validationPercantage);
		for (int i = 0; i < numOfValidation; i++) {
			int rand = (int) Math.floor(Math.random() * (messages.size()));					
			temp = messages.get(rand);
			validationMessages.add(temp);
		}
		
		for (int i = 0; i < validationMessages.size(); i++){
			messages.remove(validationMessages.get(i));
		}	
			

		
		//build trees in different sizes and test them on the validation messages
		Tree bestTree = null;
		Tree currentTree = null;
		double result;
		double bestTreeResult = 0;
		for (int T = (int) Math.pow(2, 0); T <= Math.pow(2, L);  T *= 2){			
			currentTree = new Tree(messages, T);
			result = currentTree.testResults(validationMessages,null);//null = don't write results to file.
			System.out.printf("Tree Size: %d; Success Rate: %f\n", T, result);
			if (result >= bestTreeResult) {				
				bestTree = currentTree;	
				bestTreeResult = result;
			}
		}						
		
//		for (int i = 0; i < testingMessages.size(); i++){
//			Message msg = testingMessages.get(i);
//			int prediction = bestTree.predict(msg);
//			int real = msg.getClassification();
//			System.out.printf("%d. Prediction: %d; Reality: %d\n", i+1,prediction, real);
//		}
		
		
//		TreePrinter.print(bestTree.getRoot());
		System.out.println("Leaves: " + bestTree.getLeafCount());		
		//print results on screen and to output file
		System.out.printf("Best Tree size (on validation): %d\n", bestTree.getSize());
		System.out.println("testing the best tree on the test examples");
		result = bestTree.testResults(testingMessages,bw);
		System.out.printf("Success Rate: %f\n", result);
		bw.close();								
		
		
		System.exit(0);
		
		
		
		
		 
		
		
		
		
		

	}// end main

}

