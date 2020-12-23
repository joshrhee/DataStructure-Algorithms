import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            for (T collectionData: data) {
                if (collectionData == null) {
                    throw new IllegalArgumentException("element in data is null");
                }
                add(collectionData);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            AVLNode<T> newRoot = rAdd(data, root);
            if (newRoot != null) {
                size++;
                root = newRoot;
            }
        }
    }


    /**
     * Helper method of Add
     *
     * @param data the data to add
     * @param node current node
     * @return Node which adds the data
     */
    private AVLNode<T> rAdd(T data, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<>(data);
        } else {

            //Insert node in left subtree
            if (data.compareTo(node.getData()) < 0) {
                AVLNode<T> newLeftNode = rAdd(data, node.getLeft());
                if (newLeftNode == null) {
                    return null;
                }
                node.setLeft(newLeftNode);
            //Insert node in right subtree
            } else if (data.compareTo(node.getData()) > 0) {
                AVLNode<T> newRightNode = rAdd(data, node.getRight());
                if (newRightNode == null) {
                    return null;
                }
                node.setRight(newRightNode);
            //Return null if the data is already exist in the tree
            } else {
                return null;
            }
            //Update height and balance factor
            update(node);

            //Re-balance factor
            return reBalance(node);
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            AVLNode<T> dummy = new AVLNode(-1);
            AVLNode<T> newRoot = rRemove(root, data, dummy);
            root = newRoot;
            return dummy.getData();
        }
    }

    /**
     * Helper method of remove()
     *
     * @param node current node
     * @param data the data that was removed
     * @return return the removed node
     */
    private AVLNode<T> rRemove(AVLNode<T> node, T data, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("The node is not in the tree");
        } else {

            //The data is smaller than the current node
            //So go into the left subtree
            if (data.compareTo(node.getData()) < 0) {
                AVLNode<T> newLeftNode = rRemove(node.getLeft(), data, dummy);
                node.setLeft(newLeftNode);
                //The data is bigger than the current node
                //So go into the right subtree
            } else if (data.compareTo(node.getData()) > 0) {
                AVLNode<T> newRightNode = rRemove(node.getRight(), data, dummy);
                node.setRight(newRightNode);
                //Base Case
                //Where we found the data we want to remove
            } else if (data.compareTo(node.getData()) == 0) {
                dummy.setData(node.getData());
                size--;

                //No child Case
                if (node.getLeft() == null && node.getRight() == null) {
                    return null;

                    //One child Case
                    //The case with only a right subtree or no subtree
                    //Swap the node we want to remove with its right child
                } else if (node.getLeft() == null) {
                    return node.getRight();

                    //One child Case
                    //The case with only a left subtree or no subtree
                    //swap the node we want to remove with its left child
                } else if (node.getLeft() == null) {
                    return node.getRight();

                    //Two child Case
                    //Use predecessor
                } else {
                    AVLNode<T> dummy2 = new AVLNode(-1);
                    node.setLeft(removePredecessor(node.getLeft(), dummy2));
                    node.setData(dummy2.getData());
                }
            }
            //Update height and balance factor
            update(node);

            //Re-balance factor
            return reBalance(node);
        }
    }

    //TA's help!!
    /**
     * Predecessor method of rRemove
     *
     * This method is used when remove node has two child
     *
     * @param node current node
     * @param dummy2 dummy node which will save the remove data
     * @return dummy node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> node, AVLNode<T> dummy2) {
        if (node.getRight() == null) {
            dummy2.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(removePredecessor(node.getRight(), dummy2));
        }
        //Update height and balance factor
        update(node);

        //Re-balance factor
        return reBalance(node);
    }

    /**
     * Update the node's height and balance factor
     *
     * @param node current node
     */
    private void update(AVLNode<T> node) {
        int leftNodeHeight = node.getLeft() == null
                ? -1
                : node.getLeft().getHeight();
        int rightNodeHeight = node.getRight() == null
                ? -1
                : node.getRight().getHeight();

        //Update this node's height
        node.setHeight(Math.max(leftNodeHeight, rightNodeHeight) + 1);

        //Update this node's balance factor
        node.setBalanceFactor(leftNodeHeight - rightNodeHeight);
    }

    /**
     * Update the balance factor of the current node
     *
     * @param node current node
     */
    private AVLNode<T> reBalance(AVLNode<T> node) {

        //Right heavy
        if (node.getBalanceFactor() <= -2) {

            //Left Rotation
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight((rightRotation(node.getRight())));
            }
            node = leftRotation(node);

        //Left heavy
        } else if (node.getBalanceFactor() >= 2) {

            //Right rotation
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            node = rightRotation(node);
        }
        //Node has a bf of 0, 1, or -1
        return node;
    }

    /**
     * Left-Left Case
     *
     * @param node current node
     * @return node that is performed Left-Left rotation
     */
    private AVLNode<T> leftLeft(AVLNode<T> node) {
        return rightRotation(node);
    }

    /**
     * Left-Right Case
     *
     * @param node current node
     * @return node that is performed Left-Right rotation
     */
    private AVLNode<T> leftRight(AVLNode<T> node) {
        node.setLeft(leftRotation(node.getLeft()));
        return leftLeft(node);
    }

    /**
     * Right-Right Case
     *
     * @param node current node
     * @return node that is performed Right-Right rotation
     */
    private AVLNode<T> rightRight(AVLNode<T> node) {
        return leftRotation(node);
    }

    /**
     * Right-Left Case
     *
     * @param node current node
     * @return node that is performed Right-Left rotation
     */
    private AVLNode<T> rightLeft(AVLNode<T> node) {
        node.setRight(rightRotation(node.getRight()));
        return rightRight(node);
    }

    /**
     * Left Rotation method
     *
     * @param a current node
     * @return the node the is rotated
     */
    private AVLNode<T> leftRotation(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * Right Rotation method
     *
     * @param c current node
     * @return the node that is rotated
     */
    private AVLNode<T> rightRotation(AVLNode<T> c) {
        AVLNode<T> b = c.getLeft();
        c.setLeft(b.getRight());
        b.setRight(c);
        update(c);
        update(b);
        return b;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else if (!contains(data)) {
            throw new NoSuchElementException("data is not in the tree");
        } else {
            return rGet(data, root);
        }
    }

    /**
     * Helper method of get()
     *
     * @param data the data to search for in the tree
     * @param curr current node
     * @return the data in the tree equal to the parameter
     */
    private T rGet(T data, AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        }
        return curr.getData().compareTo(data) > 0
                ? rGet(data, curr.getLeft())
                : rGet(data, curr.getRight());
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            return rContains(data, root);
        }
    }

    /**
     * Helper method of contains()
     *
     * @param data the data to search for in the tree
     * @param curr current node
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean rContains(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) == 0) {
            return true;
        }
        return curr.getData().compareTo(data) > 0
                ? rContains(data, curr.getLeft())
                : rContains(data, curr.getRight());
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Helper method of height()
     *
     * @param node current node
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int rHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeight = rHeight(node.getLeft());
            int rightHeight = rHeight(node.getRight());

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new IllegalArgumentException("word array is null");
        } else if (word.length == 0) {
            LinkedList<T> listEmpty = new LinkedList<>();
            return listEmpty;
        }
        LinkedList<T> list = new LinkedList<>();
        AVLNode<T> commonRoot = findingCommonRoot(word, root);

        traverseAdding(word[0], list, commonRoot, true);
        list.removeLast();
        traverseAdding(word[word.length - 1], list, commonRoot, false);

        for (int i = 0; i < word.length; i++) {
            if (!word[i].equals(list.get(i))) {
                throw new NoSuchElementException("The path is not in the tree");
            }
        }
        return list;
    }

    /**
     * Traverse to the both left with adding in reverse order to the list
     *
     * @param target array of T, where each element represents a letter in the
     * word (in order).
     * @param list the original list
     * @return list with adding all elements
     */
    private List<T> traverseAdding(T target, LinkedList<T> list, AVLNode<T> commonRoot, boolean figure) {
        AVLNode<T> currNode = commonRoot;

        if (commonRoot == null) {
            throw new NoSuchElementException("The path is not in the tree");
        }
        if (figure) {
            list.addFirst(currNode.getData());
        } else {
            list.addLast(currNode.getData());
        }

        if (currNode.getData().compareTo(target) > 0) {
            currNode = currNode.getLeft();
            traverseAdding(target, list, currNode, figure);
        } else if (currNode.getData().compareTo(target) < 0) {
            currNode = currNode.getRight();
            traverseAdding(target, list, currNode, figure);
        }

        return list;
    }

    /**
     * Traverse to the right with adding in right order to the list
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @param list the original list
     * @return list with adding all elements
     */
    private List<T> traverseAddingRight(T[] word, LinkedList<T> list, AVLNode<T> commonRoot) {

        AVLNode<T> movingRoot = commonRoot.getRight();
        while (list.get(size - 1).compareTo(word[word.length - 1]) != 0) {
            list.addLast(movingRoot.getData());
        }
        return list;
    }

    /**
     * Finding the deepest common root
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @param commonRoot current root
     * @return return the deepest common root
     */
    private AVLNode<T> findingCommonRoot(T[] word, AVLNode<T> commonRoot) {

        if (commonRoot == null) {
            throw new NoSuchElementException("the path is not in the tree");
        } else if (word[0].compareTo(commonRoot.getData()) < 0
                && word[word.length - 1].compareTo(commonRoot.getData()) < 0) {
            return findingCommonRoot(word, commonRoot.getLeft());
        } else if (word[0].compareTo(commonRoot.getData()) > 0
                && word[word.length - 1].compareTo(commonRoot.getData()) > 0) {
            return findingCommonRoot(word, commonRoot.getRight());
        } else {
            return commonRoot;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}