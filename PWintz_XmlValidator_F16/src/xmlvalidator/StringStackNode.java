package xmlvalidator;

public class StringStackNode {

	private final String item;

	private final StringStackNode next;


	public StringStackNode(String item, StringStackNode next) {
		this.item = item;
		this.next = next;
	}


	public String getItem() {
		return item;
	}


	public StringStackNode getNext() {
		return next;
	}

}
