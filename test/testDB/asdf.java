 package testDB;

import utilities.DoubleLinkedList;

public class asdf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoubleLinkedList<Integer> dll = new DoubleLinkedList<Integer>();
		dll.add(new Integer(111));
		dll.add(new Integer(112));
		dll.add(new Integer(113));
		dll.add(new Integer(114));
		
		Integer[] ints = dll.toArray();
		for(Integer i : ints)
			System.out.println(i);
	}

}
