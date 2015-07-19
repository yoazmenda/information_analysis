import java.util.ArrayList;
import java.util.BitSet;

public class Message {

	private BitSet bitset;
	private int classification;
	private Boolean testing;

	public Message() {
		this.bitset = new BitSet();
		this.classification = 0;
		testing = false;
	}

	public Message(String str, Dictionary dict, int classification) {
		testing = false;
		this.bitset = new BitSet();
		this.classification = 0;
		String[] words = str.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			this.setWord(words[i], dict);
		}
		this.classification = classification;
	}

	public void setForTesting() {
		testing = true;
	}

	public Boolean isForTesting() {
		return testing;
	}

	public void setWordIndex(int n) {
		bitset.set(n);
	}

	public void setWord(String str, Dictionary dict) {
		setWordIndex(dict.lookupByWord(str));
	}

	public void setClassification(int n) {
		this.classification = n;
	}

	public int getClassification() {
		return this.classification;
	}

	public boolean contains(String word) {
		for (int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1)) {
			if (Main.dict.lookupByIndex(i).equals(word)) {
				return true;
			}
		}
		return false;
	}

	public void printMessageIndices() {
		System.out.println(this.bitset.toString());
	}

	public void print() {
		System.out.printf("%d: ", classification);
		for (int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1)) {
			System.out.printf("%s ", Main.dict.lookupByIndex(i));
		}
		System.out.println();
	}

	public static int mostCommonClassification(ArrayList<Message> messages) {
		int count[] = new int[Main.MAXCLASSIFICATION];
		for (int i = 0; i < Main.MAXCLASSIFICATION; i++) count[i]=0;
		// count for each forum how many messages it has
		for (int i = 0; i < messages.size(); i++) {
			System.out.println(messages.get(i).getClassification());
			count[messages.get(i).getClassification()]++;
		}

		int max = 0;
		int best_i = 0;
		for (int i = 1; i <= Main.numOfClassifications; i++) {
			if (max <= count[i]) {
				max = count[i];
				best_i = i;
			}
		}
//		System.out.printf("best forum: %d\n", best_i);
		return best_i;
	}

}
