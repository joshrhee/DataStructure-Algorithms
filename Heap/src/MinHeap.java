import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        T[] backingArrayTemp = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data is null");
            }
            backingArrayTemp[i + 1] = data.get(i);
        }
        size = data.size();
        backingArray = backingArrayTemp;
        for (int i = size / 2; i >= 1; i--) {
            downHeap(i);
        }
    }

    /**
     * Down heap method for building heap algorithm and remove method
     * @param index the starting index which will start down heap
     */
    private void downHeap(int index) {
        while (index * 2 <= size) {
            T smallestData = null;
            int smallestIndex = 0;
            if (index * 2 + 1 > size) {
                smallestData = backingArray[index * 2];
                smallestIndex = index * 2;
            } else if (backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) > 0) {
                smallestData = backingArray[index * 2 + 1];
                smallestIndex = index * 2 + 1;
            } else {
                smallestData = backingArray[index * 2];
                smallestIndex = index * 2;
            }
            //Breaking point
            if (backingArray[index].compareTo(smallestData) < 0) {
                break;
            }
            backingArray[smallestIndex] = backingArray[index];
            backingArray[index] = smallestData;
            index = smallestIndex;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            int index = size + 1;
            int parentIndex = index / 2;
            if (size >= backingArray.length - 1) {
                T[] backingArrayTemp = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i <= size; i++) {
                    backingArrayTemp[i] = backingArray[i];
                }
                backingArray = backingArrayTemp;
            }
            backingArray[index] = data;
            while (parentIndex >= 1 && backingArray[parentIndex].compareTo(backingArray[index]) > 0) {
                T backingArrayChange = backingArray[parentIndex];
                backingArray[parentIndex] = backingArray[index];
                backingArray[index] = backingArrayChange;

                index = parentIndex;
                parentIndex = parentIndex / 2;
            }
            size++;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            int index = size;
            T removedData = backingArray[1];
            backingArray[1] = backingArray[index];
            backingArray[index] = null;
            index = 1;
            size--;
            downHeap(index);
            return removedData;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
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
     * Returns the size of the heap.
     *
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
