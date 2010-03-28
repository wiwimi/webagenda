/**
 * 
 */
package utilities;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;



/**
 * Represents a Linked List that can traverse backwards or forwards
 * 
 * @author peon-dev
 *
 */
public class DoubleLinkedList<E> implements List<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DLLNode head = null;
	private DLLNode tail = null;
	
	private int size = 0;
	
	/**
	 * Constructor. Nothing to see here.
	 */
	public DoubleLinkedList()
	{
		size = 0;
	}
	
	/**
	 * Constructor that has an array that is automatically added to double linked list.
	 * @param array to add to dll
	 */
	public DoubleLinkedList(List<E> array)
	{
		size = 0;
		DLLNode head = null,
				tail = null;
		// Assign head
		head = new DLLNode(null,null,array.get(0));
		tail = head;
		// All are assigned if array size is 1. 
		size ++;
		// Array has to be 2 or more for loop to work, so there will be a previous node
		for(int i = 1; i < array.size(); i++)
		{
			tail = new DLLNode(tail,null,array.get(i)); // tail is now a node after previous tail, which is now a back-link
			tail.getPreviousNode().setNextNode(tail); // original tail can forward-link to current tail
			size ++;
		}
	}
	
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException,
			IndexOutOfBoundsException {
		int i = 0;
		DLLNode current = head; 
		if(index > (size / 2)) {
			i = size;
			current = tail;
			for(; i > index; i--)
			{
				current = current.getPreviousNode();
			}
			// Add to position before index
			if(i == size) {
				// No change, position is at tail
				DLLNode dll = new DLLNode(tail,null,toAdd);
				tail.setNextNode(dll);
				tail = dll;
			}
			else {
				//Add utilizing both next and previous nodes
				DLLNode dll = new DLLNode(current,current.getNextNode(),toAdd);
				current.setNextNode(dll);
				current.getNextNode().setPreviousNode(dll);
			}
			size ++;
			return true;
		}
		else if(size == 0) {
			current = new DLLNode(null,null,toAdd); // current takes place of head, prev is null
			if(head == null) {
				head = current;
				tail = head;
			}
			size++;
			return true;
		}
		else {
			// Start from head
			// current has been assigned to the head at top of method
			for(; i < index; i++)
			{
				current = current.getNextNode();
			}
			DLLNode dll = new DLLNode(current.getPreviousNode(),current,toAdd);
			try {
				current.getPreviousNode().setNextNode(dll);
				current.setPreviousNode(dll);
			}
			catch(NullPointerException npE) {
				// This is at head position : create before head node
				dll = new DLLNode(null,head,toAdd);
				head.setPreviousNode(dll);
				head = dll;
				
			}
			size ++;
			return true;
		}
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException,
			IndexOutOfBoundsException {
		if(size == 0) {
			head = new DLLNode(null,null,toAdd);
			tail = head;
			size++;
			return true;
		}
		else {
			DLLNode add_this = new DLLNode(tail,null,toAdd);
			tail.setNextNode(add_this);
			tail = add_this;
			size++;
		}
		
		return true;
	}

	@Override
	public boolean addAll(List<? extends E> toAdd) throws NullPointerException {
		for(int i = 0; i < toAdd.size(); i++) 
		{
			add(toAdd.get(i));
		}
		return true;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		DLLNode dll = head;
		for(int i = 0; i < size; i++)
		{
			if(dll.getElement() == toFind) return true;
			dll = dll.getNextNode();
		}
		return false;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		DLLNode to_return = head;
		for(int i = 0; i < index; i++)
		{
			to_return = to_return.getNextNode();
		}
		return (E) to_return.getElement();
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public Iterator<E> iterator() {
		if(isEmpty())
			return null;
		else
			return new DLLIterator();
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException, NullPointerException {
		if(index == size) throw new IndexOutOfBoundsException();
		if(size == 0) throw new NullPointerException();
		DLLNode current = null;
		int i = 0;
		if(index > (size / 2)) {
			current = tail;
			if(index == size - 1) {
				if(tail.hasPreviousNode()) {
					// remove the tail to previous and reassign
					tail = tail.getPreviousNode();
					tail.setNextNode(null);
				}
				else {
					// Only tail exists, also means only head exists
					head = null;
					tail = null;
				}
				size --;
				return (E) current.getElement();
			}
			
			// Start at tail, traverse 
			for(i = (size - 1); i > index; i--) {
				current = current.getPreviousNode();
				// arrives at position i (index) from tail end and must remove item at i
			}
			current.getPreviousNode().setNextNode(current.getNextNode());
			current.getNextNode().setPreviousNode(current.getPreviousNode());
			size --;
			return (E) current.getElement();
		}
		else {
			current = head;
			// Start at head
			if(index == 0) {
				if(head.hasNextNode())
				{
					head = head.getNextNode();
					head.setPreviousNode(null);
				}
				else {
					// only one item exists
					head = null;
					tail = null;
				}
				size --;
				return (E) current.getElement();
			}
			for(; i < index; i++)
			{
				current = current.getNextNode();
			}
			current.getPreviousNode().setNextNode(current.getNextNode());
			current.getNextNode().setPreviousNode(current.getPreviousNode());
			size --;
			return (E) current.getElement();
			
		}
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		// Because the item cannot be determined its head or tail proximity, we use head
		DLLNode current = head;
		/* Use a boolean so that loop is forced to act as a 'do-while' loop, not be one.
		 * Loop cannot be a do-while since the statement "current = current.getNextNode" will
		 * ruin the current.hasNextNode() test in the 'while' since the current node is
		 * re-assigned at the bottom of the loop. (Checks for the next node's hasNextNode(),
		 * instead of the current node's hasNextNode(). If a head object is found, it must
		 * be dealt with separately so reassignment cannot be at the top.)
		 */
		boolean b = true;
		while(b)
		{
			if(current.getElement() == toRemove)
			{
				
				if(! current.hasPreviousNode()) {
					// Head
					Object o = (E) current.getElement();
					head = current.getNextNode();
					head.setPreviousNode(null);
					size--;
					return (E) o;
				}
				else if(! current.hasNextNode()) {
					// Tail
					Object o = (E) current.getElement();
					tail = current.getPreviousNode();
					tail.setNextNode(null);
					size--;
					return (E) o;
				}
				else {
					// Anything in between
					current.getPreviousNode().setNextNode(current.getNextNode());
					current.getNextNode().setPreviousNode(current.getPreviousNode());
					size--;
					return (E) current.getElement();
				}
			}
			b = current.hasNextNode(); // When b is false, it still must be checked once more.
			current = current.getNextNode();
		}
		
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException,
			IndexOutOfBoundsException {
		DLLNode current = head;
		for(int i = 0; i < index; i++)
		{
			current = current.getNextNode();
		}
		current.setElement(toChange);
		return (E) current.getElement();
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Takes the DoubleLinkedList array, adds the toHold parameter array to it, and returns that array.
	 * 
	 * @param E[] toHold - Array to add to the returned array on top of the current DoubleLinkedList array.
	 * @return E[] array - Place this array into an Object[] array. You may get a ClassCastException if
	 * it's not an Object[] array.
	 */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		Object[] temp = (E[]) new Object[toHold.length];
		if(temp.length < (size + toHold.length)) {
			temp = (E[]) new Object[(size + toHold.length)];
		}
		DLLNode current = head;
		int i = 0;
		for(; i < size; i++)
		{
			temp[i] = (E) current.getElement();
			current = current.getNextNode(); 
		}
		for(int j = 0; j < toHold.length; j++)
		{
			if(temp[i] == null) {
				// add to this position
				temp[i] = toHold[j];
			}
			i++;
		}
		return (E[]) temp;
	}

	@Override
	public E[] toArray() {
		E[] array = (E[])(Array.newInstance(head.getElement().getClass(), size));
		DLLNode current = head;
		for (int i = 0; i < size; i++)
		{
		array[i] = (E) current.getElement();
		current = current.getNextNode();
		}
		return array;
	}

	public class DLLIterator implements Iterator<E> {

		DLLNode dll_current = head;
		
		@Override
		public boolean hasNext() {
			return (dll_current != null);
		}

		@Override
		public E next() throws NoSuchElementException {
			Object o = (E) dll_current.getElement();
			dll_current = dll_current.getNextNode();
			return (E) o;
		}
		
	}
}
