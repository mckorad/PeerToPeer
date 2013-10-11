
public class LinkedList
{
	private Node head;
	private int listCount;
	
	public LinkedList()
	{
		head = new Node(0, null, null);
		listCount = 0;
	}
	
	// appends the specified element to the end of this list.
	public void add(int rfc_number, String rfc_version, String rfc_title)

	{
		Node temp = new Node(rfc_number, rfc_version, rfc_title);
		Node current = head;
		while(current.getNext() != null)
		{
			current = current.getNext();
		}
		current.setNext(temp);
		listCount++;
	}
	
	// inserts the specified element at the specified position in this list.
	public void add(int rfc_number, String rfc_version, String rfc_title, int index)
	
	{
		Node temp = new Node(rfc_number, rfc_version, rfc_title);
		Node current = head;
		
		for(int i = 1; i < index && current.getNext() != null; i++)
		{
			current = current.getNext();
		}
		
		temp.setNext(current.getNext());
		current.setNext(temp);
		listCount++;
	}
	
	// returns the element at the specified position in this list.
	public String get(int index)
	{
		// index must be 1 or higher
		if(index <= 0)
			return null;
		
		Node current = head.getNext();
		for(int i = 1; i < index; i++)
		{
			if(current.getNext() == null)
				return null;
			
			current = current.getNext();
		}
		return current.toString_();
	}
	
	// removes the element at the specified position in this list.
	public boolean remove(int index)
	{
		// if the index is out of range, exit
		if(index < 1 || index > size())
			return false;
		
		Node current = head;
		for(int i = 1; i < index; i++)
		{
			if(current.getNext() == null)
				return false;
			
			current = current.getNext();
		}
		current.setNext(current.getNext().getNext());
		listCount--; 
		return true;
	}
	
	// returns the number of elements in this list.
	public int size()
	{
		return listCount;
	}
	
	public String toString_()
	{
		Node current = head.getNext();
		String output = "";
		while(current != null)
		{
			output += "[" + current.getRfc_number() + "]";
			output += "[" + current.getRfc_title().toString() + "]";
			output += "[" + current.getRfc_version().toString() + "]";
			current = current.getNext();
		}
		return output;
	}
	
	private class Node
	{
		
		Node next;
		int rfc_number;
		String rfc_version;
		String rfc_title;
		
		public String toString_()
		{
			Node current = head.getNext();
			String output = "";
			while(current != null)
			{
				output += "[" + current.getRfc_number() + "]";
				output += "[" + current.getRfc_title().toString() + "]";
				output += "[" + current.getRfc_version().toString() + "]";
				current = current.getNext();
			}
			return output;
		}
		
		

		// Node constructor
		public Node(int _rfc_number, String _rfc_version, String _rfc_title)
		{
			next = null;
			rfc_number = _rfc_number;
			rfc_version = _rfc_version;
			rfc_title = _rfc_title;
		}
		
		public int getRfc_number()
		{
			return rfc_number;
		}
		
		public String getRfc_version()
		{
			return rfc_version;
		}
		
		public String getRfc_title()
		{
			return rfc_title;
		}

		
		public Node getNext()
		{
			return next;
		}
		
		public void setNext(Node _next)
		{
			next = _next;
		}
	}
}