/**
 * 
 */
package xmlvalidator;

import java.util.*;

import org.apache.commons.lang3.*;

/**
 * @author PaulWintz October 5, 2016 For CompSci 106 at SBCC
 */
public class BasicXmlValidator implements XmlValidator {

	/**
	 * Uses a StringStack (optionally more than one) to validate the nesting of XML elements in xmlDocument. Extra
	 * credit will be awarded if unquoted attribute values are found. This method returns when the first problem is
	 * found.
	 * 
	 * @param xmlDocument
	 *            The XML document as a String.
	 * 
	 * @return If the xmlDocument's elements are properly nested, null is returned. Otherwise, a String[] containing
	 *         error information is returned. The array contents depend on the type of problem that occurs (see below):
	 * 
	 *         <pre>
	 *         If the stack is empty when a closing tag is found, the return array should be:
	 * 
	 *         [0] = "Orphan closing tag" 
	 *         [1] = Closing tag name 
	 *         [2] = Line # of closing tag name 
	 *              e.g. 
	 *         [0] = "Orphan closing tag"
	 *         [1] = "sometag" 
	 *         [2] = "35"
	 * 
	 *         If the stack is not empty when the end of the xmlDocument is reached:
	 * 
	 *         [0] = "Unclosed tag at end" 
	 *         [1] = Tag name from the top of the stack 
	 *         [2] = Line # of the tag name 
	 *              e.g. 
	 *         [0] = "Unclosed tag at end" 
	 *         [1] = "sometag" 
	 *         [2] = "14"
	 * 
	 *         If a closing tag name does not match the tag name on the top of the stack:
	 * 
	 *         [0] = "Tag mismatch" 
	 *         [1] = Tag name from the top of the stack 
	 *         [2] = Line # of the tag name 
	 *         [3] = Closing tag name 
	 *         [4] = Line # of the closing tag name 
	 *              e.g. 
	 *         [0] = "Tag mismatch" 
	 *         [1] = "sometag" 
	 *         [2] = "14" 
	 *         [3] = "someothertag" 
	 *         [4] = "30"
	 *         
	 *         EXTRA CREDIT:
	 *         [0] = "Attribute not quoted" 
	 *         [1] = Name of the tag that has an unquoted attribute
	 *         [2] = Line # of the tag name 
	 *         [3] = Attribute name 
	 *         [4] = Line # of the unquoted attribute value 
	 *              e.g. 
	 *         [0] = "Attribute not quoted" 
	 *         [1] = "sometag" 
	 *         [2] = "14" 
	 *         [3] = "someattribute" 
	 *         [4] = "15"
	 * 
	 *         </pre>
	 * 
	 * 
	 *         Validation notes: Ignore any tag that doesn't start with '/' or a letter character. This includes xml
	 *         version/encoding elements like <?xml version="1.0" encoding="UTF-8"?>, and comments like <!-- diblah -->.
	 *         Ignore self-closing tags like <sometag />. Ignore comments like <!-- This is a comment -->. Line numbers
	 *         start at 1. '\n' marks the end of a line. You can assume that the XML document won't have any CDATA
	 *         sections. Note that XML start tags can span multiple lines. E.g. :
	 * 
	 *         <sometag attr1="blah" attr2="diblah">
	 * 
	 */

	/*
	 * Line breaks only matter for keeping track of which line a tag is on. So, read each line, increment the line
	 * number If the line contains the start of a tag, then create a string builder. If the end of the tag is not in
	 * that line, add the entire rest of the line and continue to the next line. when the closing bracket is
	 * encountered, if the first or last characters are valid, then split the string between spaces. The first String
	 * will be the tag; push or pop it. Check the remaining string to make sure that the first the quation marks are
	 * closed.
	 */


	@Override
	public String[] validate(String xmlDocument) {
		BasicStringStack tagStack = new BasicStringStack();
		Stack<Integer> lineNumberStack = new Stack<>();

		int startOfCurrentTagLineNumber = 0;
		int lineNumber = 1;

		char[] chars = xmlDocument.toCharArray();
		StringBuilder sb = null;

		try {
			for (char c : chars) {
				switch (c) {
				case '<': // Open Tab
					if (sb != null) {
						throw new IllegalStateException("Tag already opened");
					}
					startOfCurrentTagLineNumber = lineNumber;
					sb = new StringBuilder();
					break;
				case '>': // Close Tab
					if (sb == null) {
						throw new IllegalStateException("Tag not opened");
					}

					// Push or pop the tag to the stack if it matches the necessary requirements
					processTag(tagStack, lineNumberStack, sb.toString(), startOfCurrentTagLineNumber);
					sb = null;

					break;
				case '\n': // Line Break
					lineNumber++;
					break;
				default:
					if (sb != null) {
						sb.append(c);
					}
					break;
				}
			}

			if (!tagStack.isEmpty()) {
				throw new UnclosedXmlTagException(tagStack.peek(0), lineNumberStack.peek());
			}

		} catch (XmlTagException e) {
			return e.errorStringArray();
		}
		return null;
	}


	private void processTag(BasicStringStack startTagStack, Stack<Integer> startLineNumberStack, String currentTagName,
			int startOfCurrentTagLineNumber) throws OrphanTagException, MismatchedXmlTagException {
		if (currentTagName.startsWith("!") || currentTagName.startsWith("?") || currentTagName.endsWith("/")) {
			// A comment, header, or self-closing tag, so skip
			return;
		}

		currentTagName = currentTagName.trim().split(" ")[0];

		// Closing tab. Try to pop off the opening tag.
		if (currentTagName.startsWith("/")) {
			currentTagName = currentTagName.substring(1);

			if (startTagStack.isEmpty()) {
				throw new OrphanTagException(currentTagName, startOfCurrentTagLineNumber);
			}

			String poppedStartTagName = startTagStack.pop();
			int poppedStartLineNumber = startLineNumberStack.pop();

			if (!currentTagName.equals(poppedStartTagName)) {
				throw new MismatchedXmlTagException(poppedStartTagName, poppedStartLineNumber, currentTagName,
						startOfCurrentTagLineNumber);
			} else {
				return;
			}
		} else {
			// then this is an opening tag. push it onto the stack.
			startTagStack.push(currentTagName);
			startLineNumberStack.push(startOfCurrentTagLineNumber);
		}
	}


	@SuppressWarnings("serial")
	static abstract class XmlTagException extends Throwable {
		int lineNumber;

		String tag;


		XmlTagException(String tag, int lineNumber) {
			this.lineNumber = lineNumber;
			this.tag = tag;
		}


		protected String[] errorStringArray() {
			String[] errorString = { warningMessage(), tag, String.valueOf(lineNumber) };
			return errorString;
		}


		abstract protected String warningMessage();
	}


	@SuppressWarnings("serial")
	private static class UnclosedXmlTagException extends XmlTagException {
		private static final String UNCLOSED_TAG_WARNING = "Unclosed tag at end";


		UnclosedXmlTagException(String tag, int lineNumber) {
			super(tag, lineNumber);
		}


		@Override
		protected String warningMessage() {
			return UNCLOSED_TAG_WARNING;
		}
	}


	@SuppressWarnings("serial")
	private static class OrphanTagException extends XmlTagException {

		private static final String ORPHAN_TAG = "Orphan closing tag";


		OrphanTagException(String tag, int lineNumber) {
			super(tag, lineNumber);
		}


		protected String warningMessage() {
			return ORPHAN_TAG;
		}
	}


	@SuppressWarnings("serial")
	private static class MismatchedXmlTagException extends XmlTagException {

		private static final String MISMATCHED_TAG_WARNING = "Tag mismatch";

		String closeTag;

		int closeLineNumber;


		MismatchedXmlTagException(String startTag, int startLineNumber, String closeTag, int closeLineNumber) {
			super(startTag, startLineNumber);

			this.closeTag = closeTag;
			this.closeLineNumber = closeLineNumber;
		}


		protected String warningMessage() {
			return MISMATCHED_TAG_WARNING;
		}


		@Override
		protected String[] errorStringArray() {
			return ArrayUtils.addAll(super.errorStringArray(), closeTag, String.valueOf(closeLineNumber));
		}
	}
}
