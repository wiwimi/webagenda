/**
 * 
 */
package utilities;

/**
 * @author peon-dev
 *
 */
public class DLLNode {

	private DLLNode next = null;
	private DLLNode prev = null;
	private Object element = null;
	
	/**
	 * Constructor creates a Node that has references to a next node and a previous node.
	 * To create a head node, make the previous parameter a null. To create a tail node,
	 * make the next parameter a null.
	 * 
	 * @param previous - DLLNode that refers to the previous item in the list
	 * @param next - DLLNode that refers to the next item in the list
	 * @param o - Object that is stored in the node.
	 */
	public DLLNode(DLLNode previous, DLLNode next, Object o)
	{
		this.prev = previous;
		this.next = next;
		this.element = o;
	}
	
	public DLLNode getPreviousNode()
	{
		return this.prev;
	}
	
	public DLLNode getNextNode()
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
	
	public Object getElement()
	{
		return this.element;
	}
	
	public void setNextNode(DLLNode nn)
	{
		this.next = nn;
	}
	
	public void setPreviousNode(DLLNode pn)
	{
		this.prev = pn;
	}
	
	public void setElement(Object o)
	{
		this.element = o;
	}
}
