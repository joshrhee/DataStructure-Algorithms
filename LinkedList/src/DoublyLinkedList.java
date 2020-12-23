import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Sang June Rhee
 * @version 1.0
 * @userid srhee34
 * @GTID 903569008
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException();
        } else {
            if (index == 0) {
                addToFront(data);
            } else {
                //Create a new Node
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
                //We are going to insert a new Node between (index - 1) and (index)
                //Set temp1 as the Node of location of index - 1
                DoublyLinkedListNode<T> temp1 = head;
                for (int i = 1; i < index; i++) {
                    temp1 = temp1.getNext();
                }
                //Set temp2 as the Node of location of current index
                DoublyLinkedListNode<T> temp2 = temp1.getNext();
                //Set the connection of above Nodes
                temp1.setNext(newNode);
                newNode.setNext(temp2);
                if (temp2 != null) {
                    temp2.setPrevious(newNode);
                }
                newNode.setPrevious(temp1);
                size++;
                if (newNode.getNext() == null) {
                    tail = newNode;
                }
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            // Create a New Node
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            //Set the head to the next of the New Node
            newNode.setNext(head);
            if (head != null) {
                head.setPrevious(newNode);
            }
            //Set the newNode to head
            head = newNode;
            size++;
            if (head.getNext() == null) {
                tail = head;
            }
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                addToFront(data);
            } else {
                tail.setNext(newNode);
                newNode.setPrevious(tail);
                tail = newNode;
                size++;
            }
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == 0) {
                return removeFromFront();
            } else {
                DoublyLinkedListNode<T> prevOfRemovedNode = head;
                for (int i = 1; i < index; i++) {
                    prevOfRemovedNode = prevOfRemovedNode.getNext();
                }
                DoublyLinkedListNode<T> removedNode = prevOfRemovedNode.getNext();
                //Save the return data
                T returnData = removedNode.getData();
                //Connecting Nodes without removedNode
                prevOfRemovedNode.setNext(prevOfRemovedNode.getNext().getNext());
                if (prevOfRemovedNode.getNext() != null) {
                    prevOfRemovedNode.getNext().setPrevious(prevOfRemovedNode);
                }
                if (removedNode == tail) {
                    tail = prevOfRemovedNode;
                }
                size--;
                return returnData;
            }

        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            DoublyLinkedListNode<T> removedNode = head;
            T returnData = removedNode.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            DoublyLinkedListNode<T> removedNode = tail;
            T returnData = removedNode.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return returnData;
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            DoublyLinkedListNode<T> indexNode;
            if (index < size / 2) {
                indexNode = head;
                for (int i = 0; i < index; i++) {
                    indexNode = indexNode.getNext();
                }
            } else {
                indexNode = tail;
                for (int i = size - 1; i > index; i--) {
                    indexNode = indexNode.getPrevious();
                }
            }
            return indexNode.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        for (int i = 0; i <= size - 1; i++) {
            removeFromFront();
        }
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            DoublyLinkedListNode<T> curr = head;
            while (curr != null) {
                T currData = curr.getData();
                if (currData.equals(data)) {
                    DoublyLinkedListNode<T> prevOfCurr = curr.getPrevious();
                    prevOfCurr.setNext(prevOfCurr.getNext().getNext());
                    if (prevOfCurr.getNext() != null) {
                        prevOfCurr.getNext().setPrevious(prevOfCurr);
                    }
                    if (curr == head) {
                        head = curr.getNext();
                    }
                    if (curr == tail) {
                        tail = prevOfCurr;
                    }
                    size--;
                    return currData;
                }
                curr = curr.getNext();
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] returnArray = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            returnArray[i] = curr.getData();
            curr = curr.getNext();
        }
        return returnArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
