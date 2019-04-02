package edu.uprm.ece.icom4035.list;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {

	private static class Node<T>{
		
		private T element;
		private Node<T> next;
		private Node<T> previous;
		
		public Node<T> getPrevious() {
			return previous;
		}

		public void setPrevious(Node<T> previous) {
			this.previous = previous;
		}

		public Node(){
			this.element = null;
			this.next = null;
			this.previous = null;
		}
		
		public Node(Node<T> N, Node<T> P, T e) {
			this.element = e;
			this.next = N;
			this.previous = P;
		}

		public T getElement() {
			return element;
		}

		public void setElement(T element) {
			this.element = element;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}
	}
	
	private Node<E> header;
//	private Node<E> tail;
	private int size;
	
	//Constructor for class
	//Initializes header and size variables
	public SortedCircularDoublyLinkedList() {
		this.size = 0;
		this.header = new Node<E>();
	}
	
	/* Adds a new element to the list in the right order by iterating
	 * over the list comparable interface.
	 */
	@Override
	public boolean add(E obj) {
		if (isEmpty()) {
			header.setNext(new Node<E>(header, header, obj));
			header.setPrevious(header.getNext());
		}
		int count = 0;
		Node<E> temp = header;
		while (count < this.size()) {
			if (temp.getNext().getElement().compareTo(obj) < 0)
				temp = temp.getNext();
			else {
				addBetween(temp, temp.getNext(), obj);
				break;
			}
			count ++;
		}
		if (count == this.size()) { //if it gets to the end of the list
//			temp = temp.getPrevious(); //have to go back once because it had already returned to the previous position
			addBetween(temp, header, obj);
		}
		this.size++;
		return true;
	}

	//Getter for the size of the list
	@Override
	public int size() {
		return this.size;
	}

	
	/* Removes the first node that has the same element in the parameter.
	 * Returns true if the element is erased, or an IndexOutBoundsException if illegal.
	 */
	@Override
	public boolean remove(E obj) {
		if (isEmpty())
			return  false;
		
		//Option 1
//		Node<E> temp = this.header;
//		int count = 0;
//		while (!temp.getNext().equals(obj) && (count < this.size())) {
//			temp = temp.getNext();
//			count ++;
//		}
//		if (count >= this.size()-1)
//			return false;
//			
//		remove(count);
		
		//Option 2 - uses two  methods  already implemented to 
		//minimize code
		remove(firstIndex(obj));
		return true;
	}

	/* Removes the node in the according index.
	 * Returns true if the element is erased, or an IndexOutBoundsException if illegal.
	 */
	@Override
	public boolean remove(int index) {
		if (isEmpty())
			return false;
		this.checkIndex(index);
		if (index == 0) {
			//moves the node pointers
			Node<E> temp = this.header.getNext();
			this.header.setNext(temp.getNext());
			//set the fields null for garbage  collector
			temp.setElement(null);
			temp.setNext(null);
			temp.setPrevious(null);
			this.size--;
			return true;
		}
		else {
			//moves the node pointers after finding the appropiate nodes
			Node<E> temp1 = this.findNode(index - 1);
			Node<E> temp2 = temp1.getNext();
			temp1.setNext(temp2.getNext());
			temp1.getNext().setPrevious(temp1);
			//set the fields null for garbage  collector
			temp2.setNext(null);
			temp2.setPrevious(null);
			temp2.setElement(null);
			this.size--;
			return true;
		}
		
	}

	/* Removes all the nodes in the list that have the element in the parameter.
	 * Returns the amount of elements erased.
	 */
	@Override
	public int removeAll(E obj) {
		int amountRemoved = 0;
		int count = 0;
		Node<E> temp = this.header;
		while (count < this.size()) {
			if (temp.getNext().getElement().equals(obj)) {
				remove(count);
				count --; //when (remove(count) is executed, size decreases by 1 so we have to decrease count/index
				amountRemoved++;
			}else {
				temp = temp.getNext();
			}
			count ++;
		}
				
		return amountRemoved;
	}

	/* Returns the first element in the list */
	@Override
	public E first() {
		if (this.isEmpty())
			return null;
		return header.getNext().getElement();
	}

	/* Returns get the last element in the list. */
	@Override
	public E last() {
		if (this.isEmpty())
			return null;
		return header.getPrevious().getElement();
	}

	/* Returns the element based on the index by iterating
	 through the list until the index desired is met.
	 */
	@Override
	public E get(int index) {
		this.checkIndex(index);
		Node<E> target = this.findNode(index);
		return target.getElement();
	}

	/* Clears all the required nodes by clearing the next and
	 previous node of the header since the garbage collector
	 will clear the other nodes because there is no pointer to
	 those nodes being used by the program.
	 */
	@Override
	public void clear() {
		// By setting the header null, the JVM garbage collector clears all the
		// other nodes, clearing all the list.
		this.header.setNext(null);
		this.header.setPrevious(null);
		this.size = 0;
		
	}
	
	/* Verifies if a element 'e' is in the list.
	 * Returns true if found else false.
	 */
	@Override
	public boolean contains(E e) {
		Node<E> temp = this.header;
		
		int count = 0;
		while (count < this.size()) { //Iterates until element 'e' is found
			if (temp.getNext().equals(e)) {
				return true;
			}
			temp = temp.getNext();
			count ++;
		}
		
		return false;
	}
	
	/* Verifies if the list contains elements */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/* Returns the index (int) of the first element by
	 iterating over the list.
	 Returns -1 if element isn't found in the list.
	 */
	@Override
	public int firstIndex(E e) {
		int index = 0;
		Node<E> temp = this.header;
		int count = 0;
		//Iterates over the list until the first element of 'e' is found and it saves the index.
		//count and index work the same so we use index to avoid creating
		//another variable
		while ((index < this.size()) && !temp.getNext().getElement().equals(e) ) {
			temp = temp.getNext();
			index ++;
		}
		if ((index < this.size()) && temp.getNext().getElement().equals(e))
			return index;
		return -1;
	}	

	/* Returns the index (int) of the last element by
	 iterating over the list.
	 Returns -1 if element isn't found in the list.
	 */
	@Override
	public int lastIndex(E e) {
		int index = 0;
		int lastIndexPos = -1;
		Node<E> temp = this.header;
		// Iterates over all the list, saving the last position where the 
		// element 'e' is present.
		//count and index work the same so we use index to avoid creating
		//another variable
		while (index < this.size()) {
			if (temp.getNext().getElement().equals(e))
				lastIndexPos = index;
			temp = temp.getNext();
			index ++;
		}
		return lastIndexPos;
	}
	
	
	/* Creates a foward iterator class for the list */
	private class ListIterator<E> implements Iterator<E> {

		private Node<E> currentNode;
		private int count;
		
		//Constructor for the iterator
		public ListIterator() {
			this.currentNode = (Node<E>) header;
			this.count = 0;
		}
		
		//Constructor with parameters
		public ListIterator(int index) {
			int i = 0;
			this.currentNode = (Node<E>) header;
			while (i < index) {
				this.currentNode = this.currentNode.getNext();
				i++;
			}
			this.count = 0;
		}
		
		
		//Checks if there is a next element in the list
		@Override
		public boolean hasNext() {
//			return this.currentNode.getNext() != header;
//			System.out.println(count + "\t" + size());
			return count < size();
		}

		//Gets the next element, if is not possible,
		//throws an NoSuchElementException 
		@Override
		public E next() {
			if (this.hasNext()) {
				E result = this.currentNode.getNext().getElement();
				this.currentNode = this.currentNode.getNext();
				this.count ++;
				return result;
			}else {
				throw new NoSuchElementException();
			}
		}
		
	}

	//returns an instance of the class ListIterator
	@Override
	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}

	//returns an instance of the class ListIterator with parameter
	@Override
	public Iterator<E> iterator(int index) {
		return new ListIterator<E>(index);
	}
	
	/* Creates a reverse iterator class for the list */
	private class ReverseListIterator<E> implements ReverseIterator<E> {

		private Node<E> currentNode;
		private int count;

		//Constructor for the reverse iterator
		public ReverseListIterator() {
			this.currentNode = (Node<E>) header;
			this.count = 0;
		}
		
		//Constructor with  parameters
		public ReverseListIterator(int index) {
			int i = 0;
			this.currentNode = (Node<E>) header;
			while (i < index) {
				this.currentNode = this.currentNode.getPrevious();
				i++;
			}
			this.count = 0;
		}
		
		//Checks if there is a next element in the list
		@Override
		public boolean hasPrevious() {
			return this.count < size();
		}

		//Gets the previous element, if is not possible,
		//throws an NoSuchElementException 
		@Override
		public E previous() {
			if (this.hasPrevious()) {
				E result = this.currentNode.getPrevious().getElement();
				this.currentNode = this.currentNode.getPrevious();
				this.count ++;
				return result;
			}else {
				throw new NoSuchElementException();
			}
		}
		
	}
	
	//returns an instance of the class ReverseListIterator
	@Override
	public ReverseIterator<E> reverseIterator() {
		return new ReverseListIterator<E>();
	}

	//returns an instance of the class ReverseListIterator with parameter
	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		return new ReverseListIterator<E>(index);
	}
	
	///////////////////////////////////////////
	/////Custom methods to facilitate code/////
	///////////////////////////////////////////
	
	//Verifies if the index is valid, else throws IndexOutOfBoundsException
	private void checkIndex(int index) {
		if ((index < 0) || (index >= this.size())){
			throw new IndexOutOfBoundsException();
		}
	}
	
	//Find a node in the list on a certain index
	private Node<E> findNode(int index) {
		Node<E> temp = this.header.getNext();
		int i = 0;
		
		while (i < index) {
			temp = temp.getNext();
			i++;
		}
		return temp;
		
	}
	
	//Adds a new node with element 'e' between two nodes
	private void addBetween(Node<E> P, Node<E> N, E e) {
		Node<E> nNode = new Node<E>(N, P, e);
		P.setNext(nNode);
		N.setPrevious(nNode);
	}
	
	//Custom method to test the iterators and for debugging
	public void marcosString() {
		Node<E> temp = header.getNext();
		int count = 0;
		while (count < this.size()) {
			System.out.println(temp.getElement());
			temp = temp.getNext();
			count ++;
		}
	}


}
