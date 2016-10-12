package xmlvalidator;

public class XMLTagStackNode extends StringStackNode {
	private final int lineNumber;


	public XMLTagStackNode(String item, StringStackNode next, int lineNumber) {
		super(item, next);
		this.lineNumber = lineNumber;
	}


	public int getLineNumber() {
		return lineNumber;
	}

}
