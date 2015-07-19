import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

//import TreePrinter.PrintableNode;

public class Tree {
	private Node root;
	private int size;
	ArrayList<Node> leaves;

	public Tree(ArrayList<Message> messages, int innerNodes) {
		root = new Node(messages);
		size = innerNodes;
		leaves = new ArrayList<Node>();
		leaves.add(root);
		for (int i = 0; i < innerNodes; i++) {
			improve();
		}
	}

	// this procedure selects a leaf and a word and then splits the leaf and
	// creates two new leaves
	private void improve() {
		Node bestLeaf = null;// = getRandomLeaf();
		String bestWord = null;// = Main.dict.getRandomWord();
		double bestInformationGain = 0;
		ArrayList<String> words = Main.dict.getWords();
		Node currentLeaf;
		String currentWord;	
		for (int i = 0; i < words.size(); i++) {
			currentWord = words.get(i);
			for (int j = 0; j < leaves.size(); j++) {
				currentLeaf = leaves.get(j);
				double currentIG = IG(currentWord, currentLeaf);
//				System.out.println(currentIG);
				if (currentIG >= bestInformationGain){
					bestInformationGain = currentIG;
					bestWord = currentWord;
					bestLeaf = currentLeaf;
				}
			}
		}
		split(bestLeaf, bestWord);
		// improvement
		leaves.remove(bestLeaf);
		leaves.add(bestLeaf.getLeft());
		leaves.add(bestLeaf.getRight());		
	}

	private double IG(String X, Node L) {
		return H(L) - H(X,L); //H(L) - H(X)
	}

	private double H(String X, Node L) {		
		split(L,X);
		Node La = L.getLeft();
		Node Lb = L.getRight();	
		System.out.println("N(La):" + N(La));
		System.out.println("N(L): " +N(L));
		System.out.println("N(Lb): " + N(Lb));
		System.out.println("H(La): "+H(La));
		System.out.println("H(Lb)" + H(Lb));
		double ans = (N(La)/N(L))*H(La) + (N(Lb)/N(L))*H(Lb);
		L.setRight(null);
		L.setLeft(null);
		return ans;
	}

	private double H(Node L) {
		double sum = 0;
		for (int i = 1; i < Main.numOfClassifications; i++){
			sum += (N(L,i)/N(L))*log2(N(L)/N(L,i));
		}
		return sum;
	}

	private double log2(double d) {		
		return Math.log10(d)/Math.log10(2);
	}

	private double N(Node L, int i) {
		int sum = 0;
		ArrayList<Message> msgs = L.getMessages();
		for (int j = 0; j < msgs.size(); j++){			
			if (msgs.get(j).getClassification() == i) sum++;
		}
		return (double)sum;
	}

	private double N(Node L) {
		System.out.println("In N: "+ L.getMessages());
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

	public Node getRandomLeaf() {
		Node current = root;
		while (!current.isLeaf()) {
			if (Math.random() > 0.5) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}
		return current;
	}

	public void print() {

	}

	public Node getRoot() {

		return root;
	}

	public int getLeafCount() {
		return this.leaves.size();
	}

}
