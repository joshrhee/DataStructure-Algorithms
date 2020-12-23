import java.util.*;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value is null");
        } else {
            if (table.length == 0 || (double) (size + 1.0) / table.length > MAX_LOAD_FACTOR) {
                resizeBackingTable(2 * table.length + 1);
            }
            int index = getIndex(key);
            int saved = -1;
            int probingTime = 0;
            while (probingTime < size && table[index] != null && !table[index].getKey().equals(key)) {
                if (!table[index].isRemoved()) {
                    probingTime++;
                } else if (table[index].isRemoved() && saved == -1) {
                    saved = index;
                }
                index = (index + 1) % table.length;
            }
            if (table[index] != null && table[index].getKey().equals(key) && !table[index].isRemoved()) {
                V result = table[index].getValue();
                table[index].setValue(value);
                return result;
            } else {
                if (saved != -1) {
                    index = saved;
                }
                size++;
                table[index] = new LinearProbingMapEntry<>(key, value);
                return null;
            }
        }
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        } else {
            int index = getIndex(key);
            int probingTime = 0;
            while (probingTime != size && !table[index].getKey().equals(key) && table[index] != null) {
                if (!table[index].isRemoved()) {
                    probingTime++;
                }
                index = (index + 1) % table.length;
            }
            LinearProbingMapEntry<K, V> entry = null;
            if (table[index] != null && table[index].getKey().equals(key) && !table[index].isRemoved()) {
                entry = table[index];
            }
            if (entry == null) {
                throw new NoSuchElementException("Key is not in the map");
            }
            V removeValue = table[index].getValue();
            table[index].setRemoved(true);
            size--;
            return removeValue;
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int index = getIndex(key);
        int probingTime = 0;
        while (probingTime != size && table[index] != null && !table[index].getKey().equals(key)) {
            if (!table[index].isRemoved()) {
                probingTime++;
            }
            index = (index + 1) % table.length;
        }
        if (table[index] == null || table[index].isRemoved()) {
            throw new NoSuchElementException("Key is not in the map");
        }
        return table[index].getValue();
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        } else {
            int index = 0;
            for (index = getIndex(key); table[index] != null; index = (index + 1) % table.length) {
                if (table[index].getKey().equals(key)) {
                    if (table[index].isRemoved()) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> setKey = new HashSet<>();
        for (int i  = 0; i < table.length && setKey.size() < size; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                setKey.add(table[i].getKey());
            }
        }
        return setKey;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> listValue = new ArrayList<>();
        for (int i = 0; i < table.length && listValue.size() < size; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                listValue.add(table[i].getValue());
            }
        }
        return listValue;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length is less than the size");
        } else {
            LinearProbingMapEntry<K, V>[] newTable = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[length];
            int newIndex = -1;
            int track = 0;
            for (int i = 0; i < table.length && track < size; i++) {
                if (table[i] != null && !table[i].isRemoved()) {
                    newIndex = getIndexResize(table[i].getKey(), newTable.length);
                    while (newTable[newIndex] != null) {
                        newIndex = (newIndex + 1) % newTable.length;
                    }
                    newTable[newIndex] = new LinearProbingMapEntry<>(table[i].getKey(), table[i].getValue());
                    track++;
                }
            }
            table = newTable;
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Return the index based on the key's hash code.
     *
     * @param key key for index
     * @return index of the key
     */
    private int getIndex(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * Return the index based on the key's hash code.
     *
     * @param key key for index
     * @return index of the key
     */
    private int getIndexResize(K key, int newTable) {
        return Math.abs(key.hashCode() % newTable);
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
