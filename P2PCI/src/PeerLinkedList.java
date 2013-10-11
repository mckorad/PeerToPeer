public class PeerLinkedList {
	// reference to the head node.
	private Node head;
	private int listCount;

	// LinkedList constructor
	public PeerLinkedList() {
		// this is an empty list, so the reference to the head node
		// is set to a new node with no data
		head = new Node(null, 0);
		listCount = 0;
	}

	// appends the specified element to the end of this list.
	public synchronized void add(String host_name, int port_number) {
		Node temp = new Node(host_name, port_number);
		Node current = head;
		// starting at the head node, crawl to the end of the list
		while (current.getNext() != null) {
			if (current.getNext().getHost_name().equalsIgnoreCase(host_name)) {
				notify();
				return;
			}
			current = current.getNext();
		}
		// the last node's "next" reference set to our new node
		current.setNext(temp);
		listCount++;// increment the number of elements variable
		notify();
	}

	// inserts the specified element at the specified position in this list.
	public synchronized void add(String host_name, int port_number, int index) {
		Node temp = new Node(host_name, port_number);
		Node current = head;
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

	// returns the element at the specified position in this list.
	public synchronized String get(int index) {
		// index must be 1 or higher
		if (index <= 0)
			return null;

		Node current = head.getNext();
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null)
				return null;

			current = current.getNext();
		}
		notify();
		return current.toString_();
	}

	// removes the elemen t at the specified position in this list.
	public synchronized boolean remove(int index) {
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

	public synchronized int search(String hostname) {
		Node current = head.getNext();
		while (current != null) {
			if (current.getHost_name().equals(hostname)) {
				notify();
				return current.getPort_number();
			}
			current = current.getNext();
		}
		notify();
		return 0;

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

	public synchronized int size()
	// post: returns the number of elements in this list.
	{
		notify();
		return listCount;
	}

	public String toString_() {
		Node current = head.getNext();
		String output = "";
		while (current != null) {
			output += "[" + current.getHost_name() + "]";
			output += "[" + current.getPort_number() + "]";
			// output += "[" + current.getRfc_version().toString() + "]";
			current = current.getNext();
		}
		return output;
	}

	private class Node {
		// reference to the next node in the chain,
		// or null if there isn't one.
		Node next;
		// data carried by this node.
		// could be of any type you need.
		// Object data;
		String host_name;
		int port_number;

		public String toString_() {
			Node current = head.getNext();
			String output = "";
			while (current != null) {
				output += "[" + current.getHost_name() + "]";
				output += "[" + current.getPort_number() + "]";
				current = current.getNext();
			}
			return output;
		}

		// Node constructor
		public Node(String _host_name, int _port_number) {
			next = null;
			host_name = _host_name;
			port_number = _port_number;

		}

		public String getHost_name() {
			return host_name;
		}

		public int getPort_number() {
			return port_number;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node _next) {
			next = _next;
		}
	}
}