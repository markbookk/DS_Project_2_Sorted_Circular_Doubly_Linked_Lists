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
		
		public Node(T e, Node<T> N, Node<T> P) {
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
	
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean remove(E obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int removeAll(E obj) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		
	}
	
	/* Verifies if a element 'e' is in the list.
	 * Returns true if found else false.
	 */
	@Override
	public boolean contains(E e) {
		Node<E> temp = this.header;
		
		int count = 0;
		while (count < this.size()) { //Iterates until found element 'e'
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

	@Override
	public Iterator<E> iterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Returns the index (int) of the first element by
	 iterator over the list.
	 */
	@Override
	public int firstIndex(E e) {
		int index = 0;
		Node<E> temp = this.header;
		int count = 0;
		while (!temp.getNext().getElement().equals(e) && (count < size) ) {
			temp = temp.getNext();
			index ++;
		}
		if (temp.getNext().getElement().equals(e))
			return index+1;
		return -1;
	}	

	@Override
	public int lastIndex(E e) {
		// TODO Auto-generated method stub
		return 0;
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


}
