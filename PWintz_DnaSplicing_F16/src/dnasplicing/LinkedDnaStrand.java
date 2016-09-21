package dnasplicing;

public class LinkedDnaStrand implements DnaStrand {

	private DnaSequenceNode start;

	private DnaSequenceNode end;

	private int appendCount;

	private int nodeCount;


	public LinkedDnaStrand(String dna) {
		start = new DnaSequenceNode(dna);
		end = start;
		appendCount = 0;
		nodeCount = 1;
	}


	@Override
	public long getNucleotideCount() {
		long count = 0;
		DnaSequenceNode current = start;
		while (current != null) {
			count += current.dnaSequence.length();
			current = current.next;
		}
		return count;
	}


	@Override
	public void append(String dnaSequence) {
		DnaSequenceNode next = new DnaSequenceNode(dnaSequence);
		end.next = next;
		next.previous = end;
		end = next;
		appendCount++;
		nodeCount++;
	}


	/**
	 * This method creates a <bold>new</bold> DnaStrand that is a clone of the current DnaStrand, but with every
	 * instance of enzyme replaced by splicee. For example, if the LinkedDnaStrand is instantiated with "TTGATCC", and
	 * cutSplice("GAT", "TTAAGG") is called, then the linked list should become something like (previous pointers not
	 * shown):
	 * 
	 * first -> "TT" -> "TTAAGG" -> "CC" -> null
	 * 
	 * <b>NOTE</b>: This method will only be called when the linked list has just one node, and it will only be called
	 * once for a DnaStrand. This means that you do not need to worry about searching for enzyme matches across node
	 * boundaries.
	 * 
	 * @param enzyme
	 *            is the DNA sequence to search for in this DnaStrand.
	 * 
	 * @param splicee
	 *            is the DNA sequence to append in place of the enzyme in the returned DnaStrand
	 * 
	 * @return A <bold>new</bold> strand leaving the original strand unchanged.
	 */
	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {
		if (nodeCount != 1)
			throw new IllegalArgumentException("This dna strand has " + this.nodeCount + " nodes. Must have only one");

		LinkedDnaStrand splicedStrand = null;

		String seq = start.dnaSequence;

		int fromIndex = 0;

		while (fromIndex < seq.length()) {
			int beginningOfEnzyme = seq.indexOf(enzyme, fromIndex);

			// if the enzyme sequence was not found, the append the rest of the original sequence to the new sequence
			if (beginningOfEnzyme != -1) {

				// append the segment before the enzyme
				String before = seq.substring(fromIndex, beginningOfEnzyme);

				if (splicedStrand == null)
					splicedStrand = new LinkedDnaStrand(before);
				else
					splicedStrand.append(before);

				// add the splicee instead of the replaced enzyme
				splicedStrand.append(splicee);

				// skip searching the segment that was replaced
				fromIndex = beginningOfEnzyme + enzyme.length();
			} else {
				String substring = seq.substring(fromIndex, seq.length());
				if (splicedStrand == null)
					splicedStrand = new LinkedDnaStrand(substring);
				else
					splicedStrand.append(substring);
				break;
			}
		}


		return splicedStrand;
	}


	@Override
	public DnaStrand createReversedDnaStrand() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getAppendCount() {
		return appendCount;
	}


	@Override
	public DnaSequenceNode getFirstNode() {
		return start;
	}


	@Override
	public int getNodeCount() {
		return nodeCount;
	}


	@Override
	public String toString() {
		String s = "";
		DnaSequenceNode current = start;
		while (current != null) {
			s += current.dnaSequence;
			current = current.next;
		}
		if (s.contains("["))
			throw new IllegalStateException(s + " contains [");

		return s;
	}


}
