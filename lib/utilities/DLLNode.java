/**
 * 
 */
package utilities;

/**
 * @author peon-dev
 *
 */
public class DLLNode<E> {

	private DLLNode<E> next = null;
	private DLLNode<E> prev = null;
	private E element = null;
	
	/**
	 * Constructor creates a Node that has references to a next node and a previous node.
	 * To create a head node, make the previous parameter a null. To create a tail node,
	 * make the next parameter a null.
	 * 
	 * @param previous - DLLNode that refers to the previous item in the list
	 * @param next - DLLNode that refers to the next item in the list
	 * @param o - Object that is stored in the node.
	 */
	public DLLNode(DLLNode<E> previous, DLLNode<E> next, E o)
	{
		this.prev = previous;
		this.next = next;
		this.element = o;
	}
	
	public DLLNode<E> getPreviousNode()
	{
		return this.prev;
	}
	
	public DLLNode<E> getNextNode()
	{
		return this.next;
	}
	
	public boolean hasNextNode()
	{
		return !(this.next == null);
	}
	
	public boolean hasPreviousNode()
	{
		return !(this.prev == null);
	}
	
	public E getElement()
	{
		return this.element;
	}
	
	public void setNextNode(DLLNode<E> nn)
	{
		this.next = nn;
	}
	
	public void setPreviousNode(DLLNode<E> pn)
	{
		this.prev = pn;
	}
	
	public void setElement(E o)
	{
		this.element = o;
	}
}
