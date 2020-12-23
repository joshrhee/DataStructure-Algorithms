import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Sang June Rhee
 * @version 1.0
 * @userid srhee34
 * @GTID 903569008
 *
 * Collaborators:
 * https://www.tutorialspoint.com/java/util/linkedlist_poll.htm (Learned Linked-List poll() method)
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() { // Need TA's HELP!!! (Do I need to do something for here?)
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) { // Need TA'S HELP!!!
        if (data == null) {
            throw new IllegalArgumentException("The data does not have any value");
        } else {
            for (T collectionData : data) {
                add(collectionData);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data does not have any value");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * Helper method of Add
     *
     * The adding data becomes a leaf in the tree
     *
     * Time complexity is O(log n) for best and average case
     * Time complexity is O(n) for worst case.
     *
     * @param curr the current node
     * @param data the data to add
     * @return Node which adds the data
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) { // NEED TA's SUPER HELP!!!!!!!!!!
        if (data == null) {
            throw new IllegalArgumentException("The data does not have any value");
        } else if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree");
        } else {
            BSTNode<T> dummy = new BSTNode(-1);
            root = rRemove(root, data, dummy);
            return dummy.getData();
        }
    }


    /**
     * Helper method of remove
     *
     * Time complexity is O(log n) for best and average case
     * Time complexity is O(n) for worst case
     *
     * @param curr current node
     * @param data the data to remove
     * @param dummy dummy node which will save the remove data
     * @return the data that was removed
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        //Data found case
        } else if (curr.getData().compareTo(data) == 0) {
            dummy.setData(curr.getData());
            size--;
            //No child of curr Node
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            //One child of curr Node
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            //Two child of curr Node
            } else {
                BSTNode<T> dummy2 = new BSTNode(-1);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        //Data not found case
        } else {
            return null;
        }
        return curr;
    }


    /**
     * Successor method for remove
     *
     * This method works when remove node has two child
     *
     * @param curr current node
     * @param dummy dummy node which will save the remove data
     * @return dummy node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data does not have any value");
        } else if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree");
        } else {
            return rGet(root, data);
        }
    }

    /**
     * Helper method of get
     *
     * Time complexity is O(log n) for best and average cases
     * Time complexity is O(n) for worst case
     *
     * @param curr the current node
     * @param data the data to get
     * @return the data in the tree equal to the data
     * otherwise
     */
    private T rGet(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        }
        return curr.getData().compareTo(data) > 0
                ? rGet(curr.getLeft(), data)
                : rGet(curr.getRight(), data);
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data does not have any value");
        } else {
            return rContains(root, data);
        }
    }

    /**
     * Helper method of contains
     *
     * Time complexity is O(log n) for best and average cases
     * Time complexity is O(n) for worst case
     *
     * @param curr the current node
     * @param data the data for search
     * @return true if the parameter data is included the tree, false
     * otherwise
     */
    private boolean rContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) == 0) {
            return true;
        }
        return curr.getData().compareTo(data) > 0
                ? rContains(curr.getLeft(), data)
                : rContains(curr.getRight(), data);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderCollection = new ArrayList<>();
        rPreorder(preorderCollection, root);
        return preorderCollection;
    }

    /**
     * Helper method of preorder
     *
     * Time complexity is O(n)
     *
     * @param curr represent current node
     */
    private void rPreorder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            list.add(curr.getData());
            rPreorder(list, curr.getLeft());
            rPreorder(list, curr.getRight());
        }
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderCollection = new ArrayList<>();
        rInorder(inorderCollection, root);
        return inorderCollection;
    }

    /**
     * Helper method of inorder
     *
     * Time complexity is O(n)
     *
     * @param curr represent current node
     */
    private void rInorder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            rInorder(list, curr.getLeft());
            list.add(curr.getData());
            rInorder(list, curr.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderCollection = new ArrayList<>();
        rPostOrder(postorderCollection, root);
        return postorderCollection;
    }

    /**
     * Helper method of postorder
     *
     * Time complexity is O(n)
     *
     * @param curr represent current node
     */
    private void rPostOrder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            rPostOrder(list, curr.getLeft());
            rPostOrder(list, curr.getRight());
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelorderCollection = new ArrayList<>();
        Queue<BSTNode> queue = new LinkedList<BSTNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> dequeNode = queue.poll();
            levelorderCollection.add(dequeNode.getData());

            if (dequeNode.getLeft() != null) {
                queue.add(dequeNode.getLeft());
            }

            if (dequeNode.getRight() != null) {
                queue.add(dequeNode.getRight());
            }
        }
        return levelorderCollection;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Height()'s helper method
     *
     * Returns the height of the root of the tree
     *
     * Time Complexity is O(n)
     *
     * @param curr current tree's root node
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int lHeight = rHeight(curr.getLeft());
            int rHeight = rHeight(curr.getRight());

            if (lHeight > rHeight) {
                return lHeight + 1;
            } else {
                return rHeight + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("k cannot be bigger than size");
        }
        LinkedList<T> kList = new LinkedList<>();
        rKLargest(kList, k, root);
        return kList;
    }

    /**
     * Helper method of the kLargest
     *
     * @param list Linked list
     * @param k the number of largest elements to return
     * @param node node for the data
     */
    private void rKLargest(LinkedList<T> list, int k, BSTNode<T> node) {
        if (k > list.size() && node != null) {
            rKLargest(list, k, node.getRight());
            if (k > list.size()) {
                list.addFirst(node.getData());
                rKLargest(list, k, node.getLeft());
            }
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
    public BSTNode<T> getRoot() {
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
