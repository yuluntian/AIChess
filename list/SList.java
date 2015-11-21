package player.list;


/**
 *  A class representing a singly linked list, used in HashTableChained. 
 *
 *  The head field stores the first node of the list. The size field stores the total number of nodes stored in the list. 
 */
public class SList{
	SListNode head;
	int size;

	//Constructs an empty SList.
	public SList(){
		head = null;
		size = 0;
	}

	//Insert an object o at the front of the list.
	public void insertFront(Object o){
		SListNode node = new SListNode(o);
		if(head == null){
			head = node;
		}else{
			node.next = head;
			head = node;
		}
		size++;
	}

	//Returns the first node of the list.
	public SListNode front(){
		return head;
	}

	//Returns the size of the list.
	public int size(){
		return size;
	}

        //Removes the given node from the list.
	public void remove(SListNode node){
		SListNode prev = head;
		if(node == head) head = head.next;
		else{
			while(prev != null){
				if(prev.next == node){
					prev.next = node.next;
					break;
				}
				prev = prev.next;
			}
		}
		size--;
	}

}
