package player.list;


/**
 *  A class representing a node in a singly linked list, used in HashTableChained. 
 *
 *  The item field stores the item stored in the node. The next field stores the next node from "this" node.
 */
public class SListNode{
	Object item;
	SListNode next;

	//Constructs a new SListNode with the given Object o as its item.
	protected SListNode(Object o){
		this.item = o;
		next = null;
	}

	//Returns the item of "this" node.
	public Object item(){
		return item;
	}

	//Returns the next node from "this" node.
	public SListNode next(){
		return next;
	}
}
