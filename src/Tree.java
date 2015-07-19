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
		Node bestLeaf = getRandomLeaf();
		String bestWord = Main.dict.getRandomWord();
		double bestInformationGain = 0;
		ArrayList<String> words = Main.dict.getWords();
		Node currentLeaf;
		String currentWord;	
		for (int i = 0; i < words.size(); i++) {
			currentWord = words.get(i);
			for (int j = 0; j < leaves.size(); j++) {
				currentLeaf = leaves.get(j);
				double IG = calcInformationGain(currentWord, currentLeaf);
				if (IG >= bestInformationGain){
					bestInformationGain = IG;
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
		System.out.println("size: " +leaves.size());
	}

	private double calcInformationGain(String currentWord,
			Node currentLeaf) {
		// TODO Auto-generated method stub
		return 0;
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
		System.out.printf("contain: %d\n", messagesWithWord.size());
		System.out.printf("don't contain: %d\n", messagesWithOutWord.size());
		//leaf.setMessagesNull(); // delete old messages to save memory because
								// garbage collector can't know i don't need it
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
