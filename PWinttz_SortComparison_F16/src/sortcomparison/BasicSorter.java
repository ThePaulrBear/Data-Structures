package sortcomparison;

import java.util.*;

public class BasicSorter implements Sorter {

	private static final Random random = new Random();


	/**
	 * Sorts part or all of a list in ascending order.
	 * 
	 * @param data
	 *            The list of elements to sort
	 * @param firstIndex
	 *            Index of the first element to be sorted.
	 * @param numberToSort
	 *            The number of elements in the section to be sorted.
	 */
	public void insertionSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		if (numberToSort < 0)
			throw new IllegalArgumentException("NumberToSort must be greater thatn zero, but was: " + numberToSort);
		/*
		 * (1) For each unsorted element: (a) For each sorted element, starting at the right-end of the sorted section
		 * (i) Compare the unsorted element with the sorted element. (ii)If the unsorted element is less than the sorted
		 * element, then move the sorted element one to the right. Else, put the unsorted element in the empty slot and
		 * break (a) loop.
		 */
		String unsortedWord = null;
		String sortedWord = null;

		List<String> sublist = data.subList(firstIndex, firstIndex + numberToSort);

		int firstUnsorted = 0;

		while (numberToSort > 0) {
			unsortedWord = sublist.get(firstUnsorted);
			int empty;
			for (empty = firstUnsorted; empty > 0; empty--) {

				sortedWord = sublist.get(empty - 1);
				if ((unsortedWord.compareTo(sortedWord) < 0)) {
					// if the first unsorted word is before the sorted word,
					// move the sorted word over one place to the empty slot.
					sublist.set(empty, sortedWord);
					continue;
				} else {
					// if the unsorted word is after the ith word,
					// place the unsorted word into the empty slot.
					break;
				}
			}
			sublist.set(empty, unsortedWord);

			firstUnsorted++;
			numberToSort--;
		}
	}


	/**
	 * Sorts part or all of the list, data, in ascending order. quickSort() must: - Call the insertionSort() function in
	 * this interface for segments of size 15 or less. - Use the median-of-three method to prevent O(n^2) running time
	 * for sorted data sets - Call the partition() function in this interface to do its partitioning.
	 * 
	 * @param data
	 *            The list of elements to sort
	 * @param firstIndex
	 *            Index of the first element to be sorted.
	 * @param numberToSort
	 *            The number of elements in the section to be sorted.
	 */
	public void quickSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		/*
		 * (1) Partition the Array (2) Compare the sizes of the left and right (3) Quick Sort the left side. (4) Quick
		 * sort the right side.
		 * 
		 */

		if (numberToSort < 16) {
			insertionSort(data, firstIndex, numberToSort);
			return;
		}

		int pivot = partition(data, firstIndex, numberToSort);
		int leftLength = pivot - firstIndex;
		int rightLength = numberToSort - leftLength;

		System.out.printf("\nAfter Partition, pivot: %d, data[pivot]: %s\n", pivot, data.get(pivot));
		printSubList(data, firstIndex, leftLength);
		printSubList(data, pivot, rightLength);

		quickSort(data, firstIndex, leftLength);
		quickSort(data, pivot, rightLength);

		System.out.println("\nAfter QuickSort:");
		int leftIsSorted = printSubList(data, firstIndex, leftLength);
		int rightIsSorted = printSubList(data, pivot, rightLength);
		System.out.printf("left: %d, right: %d\n\n", leftIsSorted, rightIsSorted);
	}


	/**
	 * Partitions part (or all) of the list. The range of indices included in the partitioning is [firstIndex,
	 * firstIndex + numberToPartition -1].
	 * 
	 * @param data
	 * @param firstIndex
	 * @param numberToPartition
	 * @return The index, relative to data[0], where the pivot value is located at the end of this partitioning.
	 */
	public int partition(final ArrayList<String> data, final int firstIndex, final int numberToPartition) {
		List<String> sublist = data.subList(firstIndex, firstIndex + numberToPartition);
		int pivot = random.nextInt(numberToPartition); // medianIndex(data, a, b, c);

		// Move the randomly selected pivot to index=0
		Collections.swap(sublist, pivot, 0);
		pivot = 0;

		// start at the index right after the pivot
		int low = pivot + 1;

		// start the high index at the last index of the sub array.
		int high = numberToPartition - 1;

		// When high == low, breaks
		while (low < high) {

			if (sublist.get(low).compareTo(sublist.get(pivot)) <= 0) {
				// if low < pivot
				// increase low index.
				low++;
			} else {
				if (sublist.get(high).compareTo(sublist.get(pivot)) <= 0) {
					// low > pivot and high < pivot,
					// so switch high and low.
					Collections.swap(sublist, low, high);
				}
				// low > pivot and high > pivot,
				// so decrease high index
				high--;
			}
		}

		Collections.swap(sublist, pivot, high);
		pivot = high;

		for (int i = 0; i < pivot; i++) {
			if (sublist.get(i).compareTo(sublist.get(pivot)) > 0) {
				printSubList(sublist, 0, pivot + 1);
				throw new RuntimeException("not partitioned");
			}
		}

		for (int i = pivot; i < numberToPartition; i++) {
			if (sublist.get(i).compareTo(sublist.get(pivot)) < 0)
				throw new RuntimeException("not partitioned");
		}

		return pivot + firstIndex;
	}


	// TODO
	private static int medianIndex(ArrayList<String> data, int a, int b, int c) {
		// if(data.get(a).compareTo(data.get(b)) < 0 && data.get(a).compareTo(data.get(c)) > 0){
		// return a;
		// } else if(data.get(b).compareTo(data.get(a)) < 0 && data.get(b).compareTo(data.get()) > 0)

		// data.get(c)
		return 0;

	}


	int largestMergeLength = 0;


	/**
	 * Sorts part or all of a list in ascending order.
	 * 
	 * mergeSort() must: - Call the insertionSort() function in this interface for segments of size 15 or less. - Call
	 * the merge() function in this interface to do its merging.
	 * 
	 * @param data
	 *            list of elements to sort
	 * @param firstIndex
	 *            Index of the first element to be sorted.
	 * @param numberToSort
	 *            The number of elements in the section to be sorted.
	 */
	public void mergeSort(final ArrayList<String> data, int firstIndex, int numberToSort) {
		if (copy == null) {
			copy = (ArrayList<String>) data.clone();
		} else if (copy.size() != data.size()) {
			throw new RuntimeException("Programming error. Copy should have been erased at the end of the last sort.");
		}

		// System.out.printf("\n\nFull, before\t[%d - %d]:\t", firstIndex, firstIndex + numberToSort - 1);
		// printList(data.subList(firstIndex, firstIndex + numberToSort));

		if (numberToSort < 16) {
			insertionSort(data, firstIndex, numberToSort);
			// Collections.sort(data.subList(firstIndex, firstIndex + numberToSort));
			for (int i = firstIndex; i < firstIndex + numberToSort; i++) {
				copy.set(i, data.get(i));
			}
			return;
		}

		int leftStart = firstIndex;
		int leftLength = numberToSort / 2;
		int rightStart = leftStart + leftLength;
		int rightLength = numberToSort - leftLength;


		// System.out.printf("Left, before\t[%d - %d]:\t", leftStart, leftStart + leftLength - 1);
		// printList(data.subList(leftStart, leftStart + leftLength));


		mergeSort(data, leftStart, leftLength);
		// System.out.println("\nAfter Left:");
		// printSubList(data, leftStart, leftLength, true);

		mergeSort(data, rightStart, rightLength);
		// System.out.println("\nAfter Right:");
		// printSubList(data, rightStart, rightLength, true);

		merge(data, leftStart, leftLength, rightLength);
		// System.out.println("\nAfter Merge:");
		// printSubList(data, firstIndex, numberToSort, true);

		if (numberToSort == data.size()) {
			copy = null;
		}
	}


	ArrayList<String> copy;


	/**
	 * Merges two sorted segments into a single sorted segment. The left and right segments are contiguous.
	 * 
	 * @param data
	 *            The list of elements to merge
	 * @param firstIndex
	 *            Index of the first element of the left segment.
	 * @param leftSegmentSize
	 *            The number of elements in the left segment.
	 * @param rightSegmentSize
	 *            The number of elements in the right segment.
	 */
	public void merge(final ArrayList<String> data, int firstIndex, int leftSegmentSize, int rightSegmentSize) {
		if (copy == null) {
			copy = (ArrayList<String>) data.clone();
		} else if (copy.size() != data.size()) {
			throw new RuntimeException("Programming error. Copy should have been erased at the end of the last sort.");
		}

		int leftFrom = firstIndex;
		int leftTo = leftFrom + leftSegmentSize;
		int rightFrom = leftTo;
		int rightTo = rightFrom + rightSegmentSize;

		List<String> left = copy.subList(leftFrom, rightFrom);
		List<String> right = copy.subList(rightFrom, rightTo);

		int dataIndex = leftFrom;

		int leftIndex = 0;
		int rightIndex = 0;

		String element;
		while (leftIndex < left.size() || rightIndex < right.size()) {
			if (rightIndex == right.size()
					|| (leftIndex < left.size() && left.get(leftIndex).compareTo(right.get(rightIndex)) < 0)) {
				element = left.get(leftIndex);
				leftIndex++;
			} else {
				element = right.get(rightIndex);
				rightIndex++;
			}

			data.set(dataIndex, element);
			dataIndex++;
		}

		for (int i = leftFrom; i < rightTo; i++) {
			copy.set(i, data.get(i));
		}

		// System.out.println("\n\n");
		// int leftIsSorted = printSubList(left, 0, leftSegmentSize, leftFrom);
		// int rightIsSorted = printSubList(right, 0, rightSegmentSize, rightFrom);
		// int mergedIsSorted = printSubList(data, firstIndex, leftSegmentSize + rightSegmentSize);
		// int copyIsSorted = printSubList(copy, firstIndex, leftSegmentSize + rightSegmentSize);
		//
		// System.out.printf("\nleft: %d\tright: %d\tmerged:%d\tcopy:%d\n", leftIsSorted, rightIsSorted, mergedIsSorted,
		// copyIsSorted);

	}


	/**
	 * EXTRA CREDIT
	 * 
	 * Sorts the list in ascending order.
	 * 
	 * @param data
	 *            The list of elements to sort
	 */
	public void heapSort(ArrayList<String> data) {
		throw new RuntimeException("Not implemented");
	}


	/**
	 * EXTRA CREDIT
	 * 
	 * Heapifies the given list.
	 * 
	 * @param data
	 *            The list to heapify.
	 */
	public void heapify(ArrayList<String> data) {
		throw new RuntimeException("Not implemented");
	}


	public static void main(String[] args) {
		BasicSorter sorter = new BasicSorter();

		ArrayList<String> list = new ArrayList<>();
		String[] b = new String[34];

		for (int i = 0; i < b.length; i++) {
			b[i] = String.valueOf((char) (random.nextInt(26) + 65)) + String.valueOf((char) (random.nextInt(26) + 65));
		}

		Collections.addAll(list, b);

		int offset = 0;
		sorter.quickSort(list, offset, list.size() - offset);

		List<String> subList = list.subList(offset, list.size());
		printList(subList);
		System.out.println("Index out of order: " + isInOrder(subList));
	}


	private static int isInOrder(List<String> list) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i - 1).compareTo(list.get(i)) > 0)
				return i;
		}
		return -1;
	}


	private static int printSubList(List<String> list, int firstIndex, int sublistLength) {
		return printSubList(list, firstIndex, sublistLength, 0);
	}


	private static int printSubList(List<String> list, int firstIndex, int sublistLength, int offset) {
		List<String> subList = list.subList(firstIndex, firstIndex + sublistLength);

		System.out.printf("[%d-%d]:", offset + firstIndex, offset + firstIndex + sublistLength - 1);
		printList(subList);
		return isInOrder(subList);
	}


	private static void printList(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			// if (i % 16 == 0 && i > 0)
			// System.out.print("\n");
			System.out.print(list.get(i) + "\t");
		}
		System.out.println();
	}
}
