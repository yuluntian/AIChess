package player.dict;


/**
 *  A subclass of Entry, used inside HashTableChained.
 */

//Constructs a DictEntry object with the given key and value. 
public class DictEntry extends Entry{
	public DictEntry(Object key, Object value){
		this.key = key;
		this.value = value;
	}
}