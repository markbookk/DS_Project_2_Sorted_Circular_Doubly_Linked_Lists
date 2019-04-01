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
	
	public SortedCircularDoublyLinkedList() {
		this.size = 0;
		this.header = null;
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
		Node<E> temp = header.getNext();
		while (count < this.size()) {
			if (temp.getElement().compareTo(obj) == -1)
				temp = temp.getNext();
			else {
				addBetween(temp, temp.getNext(), obj);
				break;
			}
			count ++;
		}
		//what happens when it is added in the last element
		//check header
		this.size++;
		return true;
	}

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
		
		//Option 2
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
			Node<E> temp = this.header.getNext();
			this.header.setNext(temp.getNext());
			temp.setElement(null);
			temp.setNext(null);
			this.size--;
			return true;
		}
		else {
			Node<E> temp1 = this.findNode(index - 1);
			Node<E> temp2 = temp1.getNext();
			temp1.setNext(temp2.getNext());
			temp2.setNext(null);
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
			if (temp.getNext().equals(obj)) {
				remove(count);
			}
			temp = temp.getNext();
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

	@Override
	public void clear() {
		// By setting the header null, the JVM garbage collector clears all the
		// other nodes, clearing all the list.
		this.header.setNext(null);
		this.header.setPrevious(null);
		
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
		while (!temp.getNext().getElement().equals(e) && (count < this.size()) ) {
			temp = temp.getNext();
			index ++;
		}
		if (temp.getNext().getElement().equals(e))
			return index+1;
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
		int count = 0;
		// Iterates over all the list, saving the last position where the 
		// element 'e' is present.
		while (count < this.size()) {
			if (temp.getNext().equals(e))
				lastIndexPos = index;
			temp = temp.getNext();
			index ++;
		}
		return lastIndexPos;
	}
	

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public ReverseIterator<E> reverseIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	///////////////////////////////////////////
	/////Custom methods to facilitate code/////
	///////////////////////////////////////////
	
	private void checkIndex(int index) {
		if ((index < 0) || (index >= this.size())){
			throw new IndexOutOfBoundsException();
		}
	}
	
	private Node<E> findNode(int index) {
		Node<E> temp = this.header.getNext();
		int i = 0;
		
		while (i < index) {
			temp = temp.getNext();
			i++;
		}
		return temp;
		
	}
	
	private void addBetween(Node<E> P, Node<E> N, E e) {
		Node<E> nNode = new Node<E>(P, N, e);
		P.setNext(nNode);
		P.setPrevious(nNode);
	}


}
