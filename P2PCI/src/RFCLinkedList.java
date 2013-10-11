public class RFCLinkedList {
	// reference to the head node.
	private Node head;
	private int listCount;

	// LinkedList constructor
	public RFCLinkedList() {
		// this is an empty list, so the reference to the head node
		// is set to a new node with no data
		head = new Node(0, null, null);
		listCount = 0;
	}

	// post: appends the specified element to the end of this list.
	public synchronized void add(int rfc_number, String rfc_title,
			String host_name) {
		Node temp = new Node(rfc_number, rfc_title, host_name);
		Node current = head;
		// starting at the head node, crawl to the end of the list
		while (current.getNext() != null) {
			current = current.getNext();
		}
		// the last node's "next" reference set to our new node
		current.setNext(temp);
		listCount++;// increment the number of elements variable
		notify();
	}

	// inserts the specified element at the specified position in this list.
	public synchronized void add(int rfc_number, String rfc_title,
			String host_name, int index) {
		Node temp = new Node(rfc_number, rfc_title, host_name);
		Node current = head;
		// crawl to the requested index or the last element in the list,
		// whichever comes first
		for (int i = 1; i < index && current.getNext() != null; i++) {
			current = current.getNext();
		}
		// set the new node's next-node reference to this node's next-node
		// reference
		temp.setNext(current.getNext());
		// now set this node's next-node reference to the new node
		current.setNext(temp);
		listCount++;// increment the number of elements variable
		notify();
	}

	public synchronized int search_returnIndex(String hostname) {
		Node current = head.getNext();
		int i = 0;
		while (current != null) {
			if (current.getHost_name().equals(hostname)) {
				notify();
				return i + 1;
			}
			i++;

			current = current.getNext();
		}
		notify();
		return 0;

	}

	public int[] search(String rfcnumber, String title, int size) {
		Node current = head.getNext();
		int i = 0;
		int index[] = new int[size];
		for (int j = 0; j < size; j++)
			index[j] = 0;

		while (current != null) {
			String rfc = Integer.toString(current.getRfc_number());
			if (current.getRfc_title().equals(title) && rfc.endsWith(rfcnumber)) {
				index[i] = 1;
			}
			i++;
			current = current.getNext();
		}

		return index;

	}

	public String get(int index)
	// post: returns the element at the specified position in this list.
	{
		// index must be 1 or higher
		if (index <= 0)
			return null;

		Node current = head.getNext();
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null)
				return null;

			current = current.getNext();
		}
		return current.getRfc_number() + " " + current.getRfc_title() + " "
				+ current.getHost_name();
	}

	public synchronized String get_hostnme(int index)
	// post: returns the hostname of element at the specified position in this
	// list.
	{
		// index must be 1 or higher
		if (index <= 0) {
			notify();
			return null;
		}
		Node current = head.getNext();
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null) {
				notify();
				return null;
			}

			current = current.getNext();
		}
		notify();
		return current.getHost_name();
	}

	//removes the element at the specified position in this list.
	public synchronized boolean remove(int index)
	{
		// if the index is out of range, exit
		if (index < 1 || index > size())
			return false;

		Node current = head;
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null)
				return false;

			current = current.getNext();
		}
		current.setNext(current.getNext().getNext());
		listCount--; // decrement the number of elements variable
		notify();
		return true;
	}

	//returns the number of elements in this list.
	public synchronized int size()
	{
		notify();
		return listCount;
	}

	public String toString_() {
		Node current = head.getNext();
		String output = "";
		while (current != null) {
			output += "[" + current.getRfc_number() + "]";
			output += "[" + current.getRfc_title().toString() + "]";
			output += "[" + current.getHost_name().toString() + "]";
			current = current.getNext();
		}
		return output;
	}

	private class Node {
		// reference to the next node in the chain,
		// or null if there isn't one.
		Node next;
		int rfc_number;
		String host_name;
		String rfc_title;

		// Node constructor
		public Node(int _rfc_number, String _rfc_title, String _host_name) {
			next = null;
			rfc_number = _rfc_number;
			rfc_title = _rfc_title;
			host_name = _host_name;
		}

		// these methods should be self-explanatory
		public int getRfc_number() {
			return rfc_number;
		}

		public String getHost_name() {
			return host_name;
		}

		public String getRfc_title() {
			return rfc_title;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node _next) {
			next = _next;
		}
	}
}