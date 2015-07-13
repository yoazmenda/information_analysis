import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

	
	public static void main(String argv[]){
	//command line validation
	if (argv.length != 5){
		System.err.println("input error: usage example: tree_learn traindir/ test.examples 25 7 output.txt");
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
	System.out.println(trainingSetFiles);
	System.out.println(testExamples);
	System.out.println(testLabels);
		
	
	}
		
		
		
		
		
}
