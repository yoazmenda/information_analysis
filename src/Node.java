import java.util.ArrayList;


public class Node implements TreePrinter.PrintableNode{

	private ArrayList<Message> messages;
	private boolean isLeaf;
	private String word;
	private Node right;
	private Node left;
	private int classification;
	
	public Node(){		
		messages = new ArrayList<Message>();
		isLeaf = true;
		right = null;
		left = null;
		word = null;
	}
	
	public Node(ArrayList<Message> messages){		
		this.messages = new ArrayList<Message>();
		isLeaf = true;
		right = null;
		left = null;
		//deep copy all messages
		for (int i = 0; i < messages.size(); i++){
			this.messages.add(messages.get(i));
		}
		classification = Message.mostCommonClassification(messages);
	}
	public boolean isLeaf(){
		return isLeaf;
	}
	
	public Node getRight(){
		return right;
	}
	public Node getLeft(){
		return left;
	}
	
	public String getText(){
		if (word==null) return Integer.toString(classification);
		
		return word+": "+Integer.toString(this.messages.size());
	}
	public void setRight(Node node){
		right = node;
	}
	public void setLeft(Node node){
		left = node;
	}
	
	public ArrayList<Message> getMessages(){
		return messages;
	}

	public void setIsInnerNode(String word) {
		this.word = word;
		this.isLeaf = false;
	}

	public void setMessagesNull() {
		this.messages = null;
	}

	public String getWord() {
		return word;
	}

	public int getClassification() {
		return this.classification;
	}

	public void setisLeaf() {
		isLeaf = true;
		right = null;
		left = null;
		word = null;		
	}


	
	
	
	
	
	
}
