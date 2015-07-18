import java.util.ArrayList;

public class Tree {
	private Node root;

	public Tree(ArrayList<Message> messages, int innerNodes) {
		root = new Node(messages);
		for (int i = 0; i < innerNodes; i++) {
			improve();
		}
	}

	// this procedure selects a leaf and a word and then splits the leaf and creates two new leaves
	private void improve() {
		Node leaf = getRandomLeaf();
		String word = Main.dict.getRandomWord();
		split(leaf,word);
	}

	private void split(Node leaf, String word) {
		leaf.setIsInnerNode(word);
		Node newLeafLeft; //contains word
		Node newLeafRight; //don't contain word
		ArrayList<Message> messagesWithWord = new ArrayList<Message>();
		ArrayList<Message> messagesWithOutWord = new ArrayList<Message>();
		ArrayList<Message> messages = leaf.getMessages();
		Message msg;
		for (int i = 0 ; i < messages.size(); i++){
			msg = messages.get(i); 
			if (msg.contains(word)){
				messagesWithWord.add(msg);			
			}
			else{
				messagesWithOutWord.add(msg);
			}
		}
		leaf.setMessagesNull(); // delete old messages to save memory  because garbage collector can't know i don't need it
		newLeafLeft = new Node(messagesWithWord);		
		newLeafRight = new Node(messagesWithOutWord);	
		leaf.setLeft(newLeafLeft);
		leaf.setRight(newLeafRight);
	}

	// input: a message object
	// output: an integer - classification prediction
	public double testResults(ArrayList<Message> messages) {
		if (messages.size() <= 0)
			return -1;
		int hits = 0;
		int misses = 0;
		Message msg;
		for (int i = 0; i < messages.size(); i++) {
			msg = messages.get(i);
			if (predict(msg) == msg.getClassification()) {
				hits++;
			} else {
				misses++;
			}
		}
		// prevent division by zero
		if (misses == 0)
			return 1;
		return (double) hits / (double) misses;

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
		
		return Message.mostCommonClassification(current.getMessages());
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

}
