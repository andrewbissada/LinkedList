package csci603.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This class implements a List by means of a linked data structure.
 * A List (also known as a <i>sequence</i>) is an ordered collection.
 * Elements in the list can be accessed by their integer index.  The
 * index of the first element in the list is zero.
 */
public class LinkedList<E> implements Iterable<E>
  {
    private Node<E> head;
    private int size;


    /**
     * A list node contains the data value and a link to the next
     * node in the linked list.
     */
    private static class Node<E> //**Why (private) classes inside of another class?
      {
        private E data;
        private Node<E> next;

        
        /**
         * Construct a node with the specified data value and link.
         */
        public Node(E data, Node<E> next)
          {
            this.data = data;
            this.next = next;
            //Using this(data,next) will result in recursive constructor error
          }
        
        /**
         * Construct a node with the given data value
         */
        public Node(E data)
          {
            this(data, null);
          }
        
        //Helper Methods
        public Node<E> getNext()
        {
        	return this.next;
        }
        
        public E getData() 
        {
        	return this.data;
        }
        
        
      }


    /**
     *  An iterator for this singly-linked list.
     */
    private static class LinkedListIterator<E> implements Iterator<E>
      {
        private Node<E> nextElement;
        
        /**
         * Construct an iterator initialized to the first element in the list.
         */
        public LinkedListIterator(Node<E> head)
          {
            nextElement = head;
          }


        /**
         * Returns true if the iteration has more elements.
         */
        @Override
        public boolean hasNext()
          {            
        	boolean nextBool = false; //creating bool to return result of later
        	if(nextElement != null)
        	{
        		nextBool = true;        		
        	}
        	return nextBool;
          }


        /**
         * Returns the next element in the list.
         *
         * throws NoSuchElementException if the iteration has no next element.
         */
        @Override
        public E next()
          {
            if (hasNext() == false)
            {
            	throw new NoSuchElementException();
            }
            else
            {
            	E temporary = nextElement.getData();
            	///Using a temporary variable because we are changing nextElement on the next line
            	nextElement = nextElement.getNext();
            	//^^this is important
            	return temporary;
            } 
            
          //End of next method  
          }
        
        //End of class
        }


    /**
     * Helper method: Find the node at a specified index.
     *
     * @return a reference to the node at the specified index
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    private Node<E> getNode(int index)
      {
    	//doesn't work properly with index>=
        if (index < 0 || index > size())
        {
            throw new IndexOutOfBoundsException("getNode Exception index equals " + index);
        }

        Node<E> node = head;

        for (int i = 0;  i < index;  i++)
        {
            node = node.next;
        }

        return node;
      }


    /**
     * Constructs an empty list.
     */
    public LinkedList()
      {
    	//setting head to null seems to delete an entry. I made it empty string
    	head = new Node("");
    	size = 0;
      }


    /**
     * Appends the specified element to the end of the list.
     */
    public void add(E element)
      {
    	if (size() == 0)
    	{
			Node<E> newNode = new Node(element);
			head = newNode;
    	}
    	else if (size()!= 0)
    	{
            Node<E> temp = head;
            while(temp.next != null) 
            	{
            		temp = temp.next;
            	}
            temp.next = new Node<E>(element);
    	}
        
        size++;
      }


    /**
     * Inserts the specified element at the specified position in the list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    public void add(int index, E element)
      {
    	if(index < 0 || index > size())
    		throw new IndexOutOfBoundsException(Integer.toString(index)); 
    	else if (index != 0)
    	{
    		//STEP 1: Set node for where you want to add after
    		//STEP 2: Create the new Node
    		Node<E> temporaryNode = getNode(index);
    		Node<E> newNode = new Node(element, temporaryNode);
    		
    		//STEP 3: Change the pointer reference of whats now the previous node to the new one were adding
    		Node<E> previousNode = getNode(index-1);
    		previousNode.next = newNode;
    	}
    	else if (size() == 0 && index == 0)
    	{
			// I.E. if this is empty/new, add it to beginning
    		//STEP 1: Create new node
    		Node<E> newNode = new Node(element);
    		//STEP 2: Since it was empty before this, set head to this new node
			head = newNode;
    	}
    	else if (size()!= 0 && index == 0)
    	{
    		Node<E> temporaryNode = head;
    		Node<E> newNode = new Node(element,temporaryNode);
    		head = newNode;
    	}
    	//STEP ?(4): Increase size of LinkedList
    	size++;
      }


    /**
     * Removes all of the elements from this list.
     */
    public void clear()
      {
        while (head != null)
          {
             Node<E> temp = head;
             
             head = head.next;

             temp.data = null;
             temp.next = null;
          }

        size = 0;
      }


    /**
     * Returns the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public E get(int index)
      {
        if (index < 0 || index > size())
        {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        
        return node.data;
      }


    /**
     * Replaces the element at the specified position in this list
     * with the specified element.
     *
     * @returns The data value previously at index
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public E set(int index, E newValue)
      {    	
    	if ( index < 0 || index >= size())
    	{
    		throw new IndexOutOfBoundsException(Integer.toString(index));
    	}
    	
    	else
    	{
    		Node<E> previous = getNode(index);
    		Node<E> node2 = previous;
    		node2.data = newValue;
    		return previous.data;
    	}
      }


    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    public int indexOf(Object obj)
      {
        int index = 0;

        if (obj == null)
          {
            for (Node<E> node = head;  node != null;  node = node.next)
              {
                if (node.data == null)
                    return index;
                else
                    index++;
              }
          }
        else
          {
            for (Node<E> node = head;  node != null;  node = node.next)
              {
                if (obj.equals(node.data))
                    return index;
                else
                    index++;
              }
          }

        return -1;
    }


    /**
     * Returns <tt>true</tt> if this list contains no elements.
     */
    public boolean isEmpty()
      {
        if(size() == 0)
        {
        	return true;
        }
        else 
        	return false;
      }


    /**
     * Removes the element at the specified position in this list.  Shifts
     * any subsequent elements to the left (subtracts one from their indices).
     *
     * @returns the element previously at the specified position
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public E remove(int index)
      {
    	Node<E> deletedNode= new Node(null);
    	
        if ( index < 0 || index >= size() )
        {
            throw new IndexOutOfBoundsException("The Index is " + index);
        }
        
        else if (index != 0)
    	{
        	Node<E> temporaryNode = getNode(index-1);
        	
        	deletedNode = getNode(index);
        	
        	temporaryNode.next = deletedNode.next;        	
    	}
        
    	else if (index == 0)
    	{
    		deletedNode = getNode(index);
    		
    		head = deletedNode.next;
    	}
        
        
        size--;
        return deletedNode.data;
      }


    /**
     * Returns the number of elements in this list.
     */
    public int size()
      {
        return size;
      }


    /**
     * Returns an iterator over the elements in this list in proper sequence.
     */
    @Override
    public Iterator<E> iterator() 
    {
        Iterator<E> iterator2 = new LinkedListIterator(head);        
        
        return iterator2;
    }


    /**
     * Returns a string representation of this list.
     */
    @Override
    public String toString()
      {
    	//Use StringBuilder to convert Node into String format
        int counter = 0; //Initialize counter for later

        StringBuilder finalString = new StringBuilder();
        //Append the opening bracket to the string
        finalString.append("[");
        
        
        //Loop until we have gotten to the size of LinkedList
        //The minus 1 is there as the position/counter starts at 0, not 1
        while( size() > 0 && counter < size() - 1 )
        {
        	//Notice in the line below we are appending both the data, and formatting it with a comma
        	finalString.append(get(counter) + ", ");
        	counter++;
        }
        
        //Append the closing bracket to the string
        finalString.append(get(counter) + "]");
        
        return finalString.toString();
        
      }


    /*
     * Compares the specified object with this list for equality. Returns true
     * if and only if both lists contain the same elements in the same order.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj)
      {
        if (obj == this)
            return true;

        if (!(obj instanceof LinkedList))
            return false;

        // cast obj to a linked list
        LinkedList listObj = (LinkedList) obj;

        // compare elements in order
        Node<E> node1 = head;
        Node    node2 = listObj.head;

        while (node1 != null && node2 != null)
          {
            // check to see if data values are equal
            if (node1.data == null)
              {
                if (node2.data != null)
                    return false;
              }
            else
              {
                if (!node1.data.equals(node2.data))
                    return false;
              }

            node1 = node1.next;
            node2 = node2.next;
          }

        return node1 == null && node2 == null;
      }

    /**
     * Returns the hash code value for this list.
     */
    @Override
    public int hashCode()
      {
        int hashCode = 1;
        Node<E> node = head;

        while (node != null)
          {
            E obj = node.data;
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
            node = node.next;
          }
        return hashCode;
      }
  }
