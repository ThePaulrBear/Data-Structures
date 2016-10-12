package xmlvalidator;

public class BasicStringStack implements StringStack {

	private int count = 0;

	private StringStackNode top = null;


	/**
	 * Pushes the given item onto the top of the stack.
	 * 
	 * @param item
	 */
	@Override
	public void push(String item) {
		StringStackNode pushed = new StringStackNode(item, top);
		top = pushed;
		count++;
	}


	/**
	 * Removes the top item from the stack.
	 * 
	 * @return The removed item.
	 */
	@Override
	public String pop() {
		if (top == null) {
			return null;
		}

		StringStackNode popped = top;
		top = top.getNext();
		count--;
		return popped.getItem();
	}


	/**
	 * Returns, but does not remove, the item at the given position. 0 is the top, 1 is the second item, and so on.
	 * 
	 * @param position
	 * 
	 * @return The item at the given position.
	 */
	@Override
	public String peek(int position) {
		int i = 0;
		for (StringStackNode cursor = top; cursor != null; cursor = cursor.getNext()) {
			if (i == position)
				return cursor.getItem();
			i++;
		}
		return null;
	}


	/**
	 * @return The number of items on the stack
	 */
	@Override
	public int getCount() {
		return count;
	}


	public boolean isEmpty() {
		return count == 0;
	}

}
