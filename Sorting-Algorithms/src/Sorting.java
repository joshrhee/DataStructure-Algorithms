import java.util.*;

/**
 * Your implementation of various sorting algorithms.
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

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i != 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = temp;
                i--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array or comparator is null");
        }
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        int pointer = arr.length - 1;

        while (swapsMade) {
            swapsMade = false;
            for (int i = startIndex; i < endIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) { //Am I using in the right way???
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;

                    swapsMade = true;
                    pointer = i;
                }
            }
            endIndex = pointer;

            if (swapsMade) {
                swapsMade = false;
                for (int i = endIndex; i > startIndex; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;

                        swapsMade = true;
                        pointer = i;
                    }
                }
                startIndex = pointer;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        //Part 1
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        T[] right = (T[]) new Object[arr.length - midIndex];
        for (int i = 0; i < arr.length - midIndex; i++) {
            right[i] = arr[midIndex + i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        //Part 2
        int i = 0;
        int j = 0;
        while (i != left.length && j != right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        //Part 3

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (arr.length <= 1) {
            return;
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;

        int max = getMax(arr);
        int k = numberDigits(max);

        for (int iteration = 0; iteration < k; iteration++) {
            for (int i = 0; i < arr.length; i++) {
                buckets[arr[i] / div % 10 + 9].addLast(arr[i]);
            }
            div *= 10;
            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx++] = (int) bucket.removeFirst();
                }
            }
        }
    }

    /**
     * Helper method to find maximum element of array
     * @param inputArray input Array
     * @return  return the maximum element
     */
    private static int getMax(int[] inputArray) {
        int maxValue = Math.abs(inputArray[0]);
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == Integer.MIN_VALUE) {
                maxValue = Integer.MAX_VALUE;
            } else if (Math.abs(inputArray[i]) > maxValue) {
                maxValue = Math.abs(inputArray[i]);
            }
        }
        return maxValue;
    }

    /**
     * Helper method to find number of digits of the maximum number
     * @param num input Array
     * @return return the number of digits
     */
    private static int numberDigits(int num) {
        int count = 0;
        while (num != 0) {
            num /= 10;
            ++count;
        }
        return count;
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array or comparator or rand is null");
        }
        rQuickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Helper method for quickSort
     * @param arr       the array that must be sorted after the method runs
     * @param left      left index
     * @param right     right index
     * @param <T>       data type to sort
     */
    private static <T> void rQuickSort(T[] arr, int left, int right, Comparator<T> comparator, Random rand) {

        //Part 1
        if (right - left < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(right - left + 1) + left;
        T pivot = arr[pivotIndex];

        T temp = arr[left];
        arr[left] = arr[pivotIndex];
        arr[pivotIndex] = temp;

        //Part 2
        int leftIndex = left + 1;
        int rightIndex = right;

        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }
            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], pivot) >= 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;

                leftIndex++;
                rightIndex--;
            }
        }

        //Part 3
        temp = arr[left];
        arr[left] = arr[rightIndex];
        arr[rightIndex] = temp;

        rQuickSort(arr, left, rightIndex - 1, comparator, rand);
        rQuickSort(arr, rightIndex + 1, right, comparator, rand);
    }
}