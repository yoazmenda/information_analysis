import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sun.org.mozilla.javascript.JavaAdapter;
 

public class Main {

	
	public static void main(String argv[]) throws IOException{
	//command line validation
	if (argv.length != 5){
		System.err.println("input error: usage example: tree_learn traindir/ test.examples 25 7 output.txt");
		System.err.printf("argv length: %d\n",argv.length);
		System.exit(-1);
	}
	
	//get training set and test files	
	File trainDir = new File(argv[0]);
	File[] dirents= trainDir.listFiles();
	ArrayList<File> trainingSetFiles = new ArrayList<>();
	File testExamples = null;
	File testLabels= null;
	for (int i = 0; i < dirents.length; i++){
		File dirent = dirents[i]; 
		if (dirent.getName().contains(".train")){
			trainingSetFiles.add(dirent);
		}
		if(dirent.getName().contains(".examples")){
			testExamples = dirent;
		}
		if(dirent.getName().contains(".labels")){
			testLabels = dirent;
		}
	}
	int numOfClassifications = trainingSetFiles.size();	
	double validationPercantage = Double.parseDouble(argv[2]);	
	int L = Integer.parseInt(argv[3]);
	File output = new File(argv[4]);			
	if(!output.exists()){
		output.createNewFile();
	}
	FileWriter fw = new FileWriter(output.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(new Date().toString()+"\n");
	bw.write("Learning results:\n");

	Dictionary dict = new Dictionary(trainingSetFiles);
	
	
	
	
	
	
	
	
	bw.close();
	}//end main
		
		
		
		
		
}
