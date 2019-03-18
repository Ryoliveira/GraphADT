
/**
 * Defines the a generic doubly-linked list class
 * @author Ryan Oliveira
 * @author Tina Nemati
 * CIS 22C, Lab 2
 */

import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 * 
	 * @postcondition New list with default values is created
	 */
	public List() {
		first = null;
		last = null;
		iterator = null;
		length = 0;
	}

	/**
	 * Instantiates a new List by copying another List
	 * 
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
	 */

	public List(List<T> original) {
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/
	
	/**
     * Searches the List for the specified
     * value using the iterative linear
     * search algorithm
     * @param value the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * @postcondition: position of the iterator remains
     * unchanged!
     */
    public int linearSearch(T value) {
    	Node temp = first;
    	int count = 1;
    	while (temp != null) {
    		if (temp.data == value) {
    			return count;
    		}
    		temp = temp.next;
    		count++;
    	}
        return -1;
    }
    
    /**
     * Returns the index from 1 to length
     * where value is located in the List
     * by calling the private helper method
     * binarySearch
     * @param value the value to search for
     * @return the index where value is 
     * stored from 1 to length, or -1 to
     * indicate not found
     * @precondition isSorted()
     * @postcondition the position of the
     * iterator must remain unchanged! 
     * @throws IllegalStateException when the
     * precondition is violated.
     */
    public int binarySearch(T value) throws IllegalStateException {
    	if(!isSorted()) {
    		throw new IllegalStateException("binarySearch(): " + 
    	"list is not sorted. Cannot begin the search!");
    	}
        return binarySearch(1, length, value);
    }
    
    /**
     * Searches for the specified value in
     * the List by implementing the recursive
     * binarySearch algorithm
     * @param low the lowest bounds of the search
     * @param high the highest bounds of the search
     * @param value the value to search for
     * @return the index at which value is located
     * or -1 to indicate not found
     * @postcondition the location of the iterator
     * must remain unchanged
     */
    private int binarySearch(int low, int high, T value) {
    	if (high < low) {
    		return -1;
    	} 
    	int mid = low + (high-low)/2;
    	Node temp = first;
    	for(int i=1;i<mid;i++) {
    		temp = temp.next;
    	}
    	if (temp.data.equals(value)) {
    		return mid;
    	} else if (temp.data.compareTo(value) > 0) {
    		return binarySearch(low, mid-1, value);
    	} else {
    		return binarySearch(mid+1, high, value);
    	}
    	
        
    }
	/**
     * Determines whether a List is sorted
     * by calling the recursive helper method
     * isSorted
     * Note: A List that is empty can be
     * considered to be (trivially) sorted
     * @return whether this List is sorted
     */
    public boolean isSorted() {
        Node temp = first;
        return isSorted(temp);
    }
    
    /**
     * Recursively determines whether 
     * a List is sorted in ascending order
     * @return whether this List is sorted
     */
    private boolean isSorted(Node n) {
    	if(n == null || n.next == null) {
    		return true;
    	}
    	else if (n.data.compareTo(n.next.data) > 0) {
    		return false;
    	}
    	return isSorted(n.next);		
    }
    
    /**
     * Returns the index of the iterator
     * from 1 to n. Note that there is 
     * no index 0.
     * @precondition
     * @return the index of the iterator
     * @throws NullPointerException when
     * the precondition is violated
     */
    public int getIndex() throws NullPointerException{
        if(offEnd()) {
        	throw new NullPointerException("getIndex(): "
        			+ "Iterator is off end, cannot access the index!");
        }
        Node temp = first;
        int count = 1;
        while(temp != iterator) {
        	temp = temp.next;
        	count++;
        }
        return count;
    }

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition List is not empty
	 * @return the integer value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (this.isEmpty()) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
		}
		return first.data;
	}

	/**
	 * Return the element currently pointed at by the iterator
	 * 
	 * @precondition Iterator is not null
	 * @return the element pointed by the iterator
	 * @throws NullPointerException if the precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIterator(): " + "iterator is off end. Cannot access data.");
		} else {
			return iterator.data;
		}
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition List is not empty
	 * @return the integer value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (this.isEmpty()) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 * 
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 * 
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * returns whether the iterator is off the end of the list
	 * 
	 * @return whether iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Returns whether the Lists have the same data in the same order
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two lists are equals
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {
					if (temp1.data != temp2.data) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**** MUTATORS ****/
	
	/**
     * Points the iterator at first
     * and then iteratively advances 
     * it to the specified index
     * @param index the index where
     * the iterator should be placed
     * @precondition 1 <= index <= length
     * @throws IndexOutOfBoundsException
     * when precondition is violated
     */
    public void moveToIndex(int index) throws IndexOutOfBoundsException{
        if(index < 1 || index > length) {
        	throw new IndexOutOfBoundsException("moveToIndex(): "
        			+ "Index not in range!");
        }
        pointIterator();
        for(int i = 0; i<index-1;i++) {
        	advanceIterator();
        }
    }

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the list
	 * @postcondition Value is added at the beginning of the list
	 */
	public void addFirst(T data) {
		Node p = new Node(data);
		if (length == 0) {
			first = last = p;
		} else {
			p.next = first;
			first.prev = p;
			first = p;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the list
	 * @postcondition Value is added at end of the list
	 */
	public void addLast(T data) {
		Node p = new Node(data);
		if (this.isEmpty()) {
			first = last = p;
		} else {
			p.next = null;
			last.next = p;
			p.prev = last;
			last = p;
		}
		length++;
	}

	/**
	 * removes the element at the front of the list
	 * 
	 * @precondition List is not empty
	 * @postcondition First value is removed from the list
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if (this.isEmpty()) {
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 * 
	 * @precondition List is not empty
	 * @postcondition Last value is removed from the list
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if (this.isEmpty()) {
			throw new NoSuchElementException("removeLast(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			last = last.prev;
			last.next = null;

		}

		length--;
	}

	/**
	 * Moves iterator to beginning of list
	 * 
	 * @postcondition iterator points to beginning of list
	 */
	public void pointIterator() {
		iterator = first;
	}

	/**
	 * inserts element after the node currently pointed to by iterator
	 * 
	 * @param data Element to be added to list by iterator
	 * @precondition iterator != null
	 * @postcondition element is inserted after element pointed to by iterator
	 * @throws NullPointerException if precondition is violated
	 */
	public void addIterator(T data) {
		if (offEnd()) {
			throw new NullPointerException("addIterator():" + "Iterator is offEnd, cannot add data.");
		} else if (iterator == last) {
			addLast(data);
		} else {
			Node N = new Node(data);
			N.next = iterator.next;
			N.prev = iterator;
			iterator.next.prev = N;
			iterator.next = N;
			length++;
		}
	}

	/**
	 * Removes the node pointed to by the iterator
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator == null
	 * @throws NullPointerException if precondition is violated
	 */
	public void removeIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("removeIterator(): " + "Iterator is offEnd, no data to remove.");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			length--;
		}
		iterator = null;
	}

	/**
	 * Moves the iterator up by one node
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator is moved up by one node
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIterator(): " + "iterator is off end. Cannot access data.");
		} else {
			iterator = iterator.next;
		}
	}

	/**
	 * Moves the iterator down by one node
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator is moved down by one node
	 * @throws NullPointerException when the precondition is violated
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("reverseIterator(): " + "iterator is off end. Cannot access data.");
		} else {
			iterator = iterator.prev;
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value separated by a blank space At the end of the List a new
	 * line
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String res = "";
		pointIterator();
		while (!offEnd()) {
			res += iterator.data + " ";
			advanceIterator();
		}
		res += "\n";
		return res;
	}

	/**
	 * Prints the contents of the linked list to the screen in the formatted #.
	 * <element> followed by a new line
	 * 
	 */
	public void printNumberedList() {
		pointIterator();
		int i = 1;
		while (!offEnd()) {
			System.out.println(i + ". " + iterator.data);
			advanceIterator();
			i++;
		}
	}
	
	/**
     * Prints a linked list to the console
     * in reverse by calling the private 
     * recursive helper method printReverse
     */
    public void printReverse() {
       Node temp = last;
       printReverse(temp);
    }
    
    /**
     * Prints a linked list to the console
     * recursively (no loop)
     * in reverse order from last to first
     * Each element separated by a space
     * Should print a new line after all
     * elements have been displayed
     */    

    private void printReverse(Node n) {
        if(n == null) {
        	System.out.println();
        }else {
        	System.out.print(n.data + " ");
        	printReverse(n.prev);
        }
    }     

}
