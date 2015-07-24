import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tree {
	private Node root;
	private int size;
	ArrayList<Node> leaves;
	ArrayList<String> tenFirstWords;

	public Tree(ArrayList<Message> messages, int innerNodes) {
		root = new Node(messages);
		size = innerNodes;
		leaves = new ArrayList<Node>();
		leaves.add(root);
		tenFirstWords = new ArrayList<String>();
		for (int i = 0; i < innerNodes; i++) {
			improve();
		}
	}

	// this procedure selects a leaf and a word and then splits the leaf and
	// creates two new leaves
	private void improve() {
		Node bestLeaf =null;// getRandomLeaf();
		String bestWord = null;// Main.dict.getRandomWord();
		double bestIG = 0;
		Node currentLeaf;
		String currentWord;
		ArrayList<String> words;
		ArrayList<Message> msgs;
		for (int i = 0; i < leaves.size(); i++){
			currentLeaf = leaves.get(i);
			msgs = currentLeaf.getMessages();
			for (int j = 0; j < msgs.size(); j++){
				words = msgs.get(j).getWords();
				for (int k = 0 ; k < words.size(); k++){
					currentWord = words.get(k);
					double currentIG = IG(currentWord, currentLeaf);
					if (currentIG >= bestIG) {
						bestIG = currentIG;
						bestWord = currentWord;
						bestLeaf = currentLeaf;
					}
				}
			}
		
		
		}
//		System.out.println("Chose: "+ bestWord+" with IG: "+ bestIG);
		split(bestLeaf, bestWord);
		if (tenFirstWords.size() < 10){
			tenFirstWords.add(bestWord);
		}
		// improvement
		leaves.remove(bestLeaf);
		leaves.add(bestLeaf.getLeft());
		leaves.add(bestLeaf.getRight());
	}

	private double IG(String X, Node L) {
//		System.out.println("Word: "+X+" - H(L) "+H(L)+" ; H(X): " + H(X,L));
		return H(L) - H(X, L); // H(L) - H(X)
	}

	private double H(String X, Node L) {
		split(L, X);
		Node La = L.getLeft();
		Node Lb = L.getRight(); 
		double ans = ((N(La) / N(L)) * H(La)) + ((N(Lb) / N(L)) * H(Lb));
		L.setRight(null);
		L.setLeft(null);
		L.setisLeaf();
		return ans;
	}

	private double H(Node L) {
		double sum = 0;
		for (int i = 1; i <= Main.numOfClassifications; i++) {
			 if (N(L,i) == 0 || N(L) == 0) 
				 ;
			 else{
				 sum += (N(L,i)/N(L))*(log2(N(L)) - log2(N(L,i)));
			 }
		}
//		System.out.println(sum);
		return sum;
	}

	private double log2(double d) {
		return Math.log10(d) / Math.log10(2);
	}

	private double N(Node L, int i) {
		int sum = 0;
		ArrayList<Message> msgs = L.getMessages();
		for (int j = 0; j < msgs.size(); j++) {
			if (msgs.get(j).getClassification() == i)
				sum++;
		}
		return (double) sum;
	}

	private double N(Node L) {
		return L.getMessages().size();
	}

	public int getSize() {
		return size;
	}

	private void split(Node leaf, String word) {
		leaf.setIsInnerNode(word);
		Node newLeafLeft; // contains word
		Node newLeafRight; // don't contain word
		ArrayList<Message> messagesWithWord = new ArrayList<Message>();
		ArrayList<Message> messagesWithOutWord = new ArrayList<Message>();
		ArrayList<Message> messages = leaf.getMessages();
		Message msg;
		for (int i = 0; i < messages.size(); i++) {
			msg = messages.get(i);
			if (msg.contains(word)) {
				messagesWithWord.add(msg);
			} else {
				messagesWithOutWord.add(msg);
			}
		}
		newLeafLeft = new Node(messagesWithWord);
		newLeafRight = new Node(messagesWithOutWord);

		leaf.setLeft(newLeafLeft);
		leaf.setRight(newLeafRight);
	}

	// input: a message object
	// output: an integer - classification prediction
	public double testResults(ArrayList<Message> messages, BufferedWriter bw) {
		if (messages.size() <= 0)
			return -1;
		int hits = 0;
		Message msg;
		for (int i = 0; i < messages.size(); i++) {
			msg = messages.get(i);
			int prediction = predict(msg);
			if (bw != null) {
				try {
					bw.write(Integer.toString(prediction) + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (prediction == msg.getClassification()) {
				hits++;
			}
		}
		return (double) hits / messages.size();
	}

	public int predict(Message msg) {
		Node current = root;
		while (!current.isLeaf()) { // if not null must have two children
			if (msg.contains(current.getWord())) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}

		return current.getClassification();
	}

	public Node getRoot() {

		return root;
	}

	public int getLeafCount() {
		return this.leaves.size();
	}

	public void printTenWords() {
		for (int i=0;i<tenFirstWords.size(); i++){
			System.out.println(i+1+": " +tenFirstWords.get(i));
		}
		
	}

}
