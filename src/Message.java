import java.util.BitSet;

public class Message {

	private BitSet bitset;
	private int classification;
	private Boolean testing;
	
	public Message(){
		this.bitset = new BitSet();
		this.classification = 0;
		testing = false;
	}
	
	public Message(String str, Dictionary dict, int classification) {
		testing = false;
		this.bitset = new BitSet();
		this.classification = 0;
		String[] words = str.split("\\s+");		
		for (int i = 0; i < words.length; i++){
			this.setWord(words[i], dict);
		}
		this.classification = classification;
	}
	public void setForTesting(){
		testing = true;
	}
	
	public Boolean isForTesting(){
		return testing;
	}
	public void setWordIndex(int n){		
		bitset.set(n);
	}
	
	public void setWord(String str,Dictionary dict){
		setWordIndex(dict.lookupByWord(str));
	}
	
	public void setClassification(int n){
		this.classification = n;
	}
	
	public int getClassification(){
		return this.classification; 
	}
	
	public void printMessageIndices(){
		System.out.println(this.bitset.toString());
	}

	public void print(Dictionary dict) {
		System.out.printf("%d: ", classification);
		for (int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i+1)) {
		     System.out.printf("%s ", dict.lookupByIndex(i));
		 }
		System.out.println();
	}
	
}
