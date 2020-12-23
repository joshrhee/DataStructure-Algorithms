import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Sang June Rhee
 * @version 1.0
 * @userid srhee34
 * @GTID 903569008
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
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
            if (size < backingArray.length) {
                for (int i = size - 1; i >= index; i--) {
                    backingArray[i + 1] = backingArray[i];
                }
                backingArray[index] = data;
                size++;
            } else {
                T[] backingArrayTemp = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < index; i++) {
                    backingArrayTemp[i] = backingArray[i];
                }
                for (int i = size - 1; i >= index; i--) {
                    backingArrayTemp[i + 1] = backingArray[i];
                }
                backingArrayTemp[index] = data;
                size++;
                backingArray = backingArrayTemp;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            if (size < backingArray.length) {
                for (int i = size - 1; i >= 0; i--) {
                    backingArray[i + 1] = backingArray[i];
                }
                backingArray[0] = data;
                size++;
            } else {
                T[] backingArrayTemp = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i <= size - 1; i++) {
                    backingArrayTemp[i + 1] = backingArray[i];
                }
                backingArrayTemp[0] = data;
                size++;
                backingArray = backingArrayTemp;
            }
        }
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            if (size < backingArray.length) {
                backingArray[size] = data;
                size++;
            } else {
                T[] backingArrayTemp = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < size; i++) {
                    backingArrayTemp[i] = backingArray[i];
                }
                backingArrayTemp[size] = data;
                size++;
                backingArray = backingArrayTemp;
            }

        }
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            T removedData = backingArray[index];
            for (int i = index + 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            return removedData;
        }
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException();
        } else {
            T removedData = backingArray[0];
            for (int i = 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            return removedData;
        }
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException();
        } else {
            T removedData = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return removedData;
        }
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            return backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        for (int i = 0; i <= size - 1; i++) {
            if (backingArray[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            backingArray[i] = null;
        }
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
