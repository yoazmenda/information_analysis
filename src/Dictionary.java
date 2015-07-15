import java.io.*;
import java.util.ArrayList;
import java.math.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class Dictionary {

	private ArrayList<DictEntry> words;
	
	public Dictionary(ArrayList<File> trainingSetFiles, double validationPercantage) {
			File file = null;
			for (int i=0;i<trainingSetFiles.size();i++){
				file = trainingSetFiles.get(i);
				int classification = Integer.parseInt(file.getName().substring(0, file.getName().indexOf('.')));				
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				       
				    }
				}
				catch(IOException e){
					System.err.println("error reading from file");
					System.err.println(e);
					System.exit(-1);					
				}
			}
	}

}
