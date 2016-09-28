package dnasplicing;

import static java.lang.Math.*;
import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.junit.Assert.*;

import sbccunittest.*;

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
	// @Override
	// public DnaStrand cutSplice(String enzyme, String splicee) {
	// if (nodeCount != 1)
	// throw new IllegalArgumentException("This dna strand has " + this.nodeCount + " nodes. Must have only one");
	//
	// LinkedDnaStrand splicedStrand = null;
	//
	// String seq = start.dnaSequence;
	//
	// int fromIndex = 0;
	//
	// this.print();
	//
	// while (fromIndex < seq.length()) {
	// int beginningOfEnzyme = seq.indexOf(enzyme, fromIndex);
	//
	// // if the enzyme sequence was not found, the append the rest of the original sequence to the new sequence
	// if (beginningOfEnzyme != -1) {
	//
	// // append the segment before the enzyme
	// String before = seq.substring(fromIndex, beginningOfEnzyme);
	//
	// if (splicedStrand == null)
	// if (before.length() > 0)
	// splicedStrand = new LinkedDnaStrand(before);
	// else {
	// splicedStrand.append(before);
	// }
	//
	// // add the splicee instead of the replaced enzyme
	// if (splicedStrand == null)
	// splicedStrand = new LinkedDnaStrand(splicee);
	// else {
	// splicedStrand.append(splicee);
	// }
	//
	// // skip searching the segment that was replaced
	// fromIndex = beginningOfEnzyme + enzyme.length();
	// } else {
	// String substring = seq.substring(fromIndex, seq.length());
	// if (splicedStrand == null)
	// splicedStrand = new LinkedDnaStrand(substring);
	// else
	// splicedStrand.append(substring);
	// break;
	// }
	// }
	//
	// splicedStrand.print();
	//
	// return splicedStrand;
	// }
	//
	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {
		if (nodeCount != 1)
			throw new IllegalArgumentException("This dna strand has " + this.nodeCount + " nodes. Must have only one");

		LinkedDnaStrand splicedStrand = null;

		String[] split = start.dnaSequence.split(enzyme);

		// this.print("=========\nOriginal");
		// out.println("Length: " + split.length);
		//
		// for (String s : split) {
		// out.print(s + ",");
		// }

		int i;
		if (start.dnaSequence.startsWith(enzyme)) {
			splicedStrand = new LinkedDnaStrand(splicee);
			splicedStrand.append(split[1]);
			i = 2;
		} else {
			splicedStrand = new LinkedDnaStrand(split[0]);
			i = 1;
		}
		for (; i < split.length; i++) {
			// splicedStrand.print("i = " + i);
			splicedStrand.append(splicee);
			splicedStrand.append(split[i]);

		}
		if (start.dnaSequence.endsWith(enzyme)) {
			splicedStrand.append(splicee);
		}

		// splicedStrand.print("Result");

		return splicedStrand;
	}


	private void print(String tag) {
		out.print(tag + ": ");
		for (DnaSequenceNode cur = start; cur != null; cur = cur.next) {
			out.print(cur.dnaSequence);
			if (cur.next != null)
				out.print("-");
		}
		out.printf("(%d node(s))\n", nodeCount);
	}


	@Override
	public DnaStrand createReversedDnaStrand() {
		this.print("Original");
		LinkedDnaStrand reversed = null;
		for (DnaSequenceNode cur = end; cur != null; cur = cur.previous) {
			String reverse = reverse(cur.dnaSequence);
			if (reversed == null)
				reversed = new LinkedDnaStrand(reverse);
			else
				reversed.append(reverse);
		}
		reversed.print("Reversed");
		return reversed;
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
		StringBuilder s = new StringBuilder();
		DnaSequenceNode current = start;
		while (current != null) {
			s.append(current.dnaSequence);
			current = current.next;
		}
		out.println(s.length());
		out.println(this.nodeCount);
		return s.toString();
	}


	public static void main(String... args) {
		{
			String dnaSequence = LinkedDnaStrandTester.createRandomDnaSequence(50, 100);
			String dnaToAppend = LinkedDnaStrandTester.createRandomDnaSequence(5, 10);
			int numTimesToAppend = (int) (random() * 10);
			LinkedDnaStrand linkedStrand = new LinkedDnaStrand(dnaSequence);
			SimpleDnaStrand simpleStrand = new SimpleDnaStrand(dnaSequence);

			for (int ndx = 0; ndx < numTimesToAppend; ndx++) {
				linkedStrand.append(dnaToAppend);
				simpleStrand.append(dnaToAppend);
			}

			assertEquals(simpleStrand.toString(), linkedStrand.toString());
			assertEquals(numTimesToAppend + 1, linkedStrand.getNodeCount());

			LinkedDnaStrand rl = (LinkedDnaStrand) linkedStrand.createReversedDnaStrand();

			// Verify that the original linked strand wasn't changed
			DnaSequenceNode node = linkedStrand.getFirstNode();
			int nodeNdx = 0;
			while (node != null) {
				assertEquals("Sequences don't match at node index " + nodeNdx, nodeNdx == 0 ? dnaSequence : dnaToAppend,
						node.dnaSequence);
				node = node.next;
				nodeNdx++;
			}

			// Verify that the new strand string is reversed
			assertEquals(simpleStrand.createReversedDnaStrand().toString(), rl.toString());
			// totalScore += 10;

			// If the new strand has a reverse order of nodes and sequences within each node, give extra
			// credit
			int numNodes = linkedStrand.getNodeCount();
			if (numNodes == rl.getNodeCount()) {
				// Build array of reversed dna strings from original LinkedDnaStrand. Start at end of
				// array and move toward
				// start
				node = linkedStrand.getFirstNode();
				String[] reversedDnaSequences = new String[linkedStrand.getNodeCount()];
				nodeNdx = numNodes - 1;
				while (node != null) {
					reversedDnaSequences[nodeNdx] = reverse(node.dnaSequence);
					node = node.next;
					nodeNdx--;
				}

				// Verify that the reversed list's nodes contain the same data as in the array
				node = rl.getFirstNode();
				nodeNdx = 0;
				while (node != null) {
					if (!node.dnaSequence.equals(reversedDnaSequences[nodeNdx]))
						break;
					node = node.next;
					nodeNdx++;
				}
				if (nodeNdx == linkedStrand.getNodeCount()) {
				}
				// extraCredit += 5;
			}

		}
	}
}
