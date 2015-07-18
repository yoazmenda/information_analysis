import java.util.ArrayList;


public class Node {

	private ArrayList<Message> messages;
	private boolean isLeaf;
	private String word;
	private Node right;
	private Node left;
	
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
	


	
	
	
	
	
	
}
