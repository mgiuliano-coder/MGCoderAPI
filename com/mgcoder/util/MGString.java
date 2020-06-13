package com.mgcoder.util;

public abstract class MGString {
    /**
     * Splits a string into chunks of a specific size. If the size does not divide evenly into the size of the range
     * of characters to split, the last element of the returned array will contain the remaining characters.
     * For example, given the string "0123456789" with a start of 2, an end of 7, and a size of 4, would return an
     * array with the following elements: {"2345","67"}.
     * @param string the String to be split.
     * @param start the index of the character to start splitting.
     * @param end the index of the character where splitting should stop, inclusive.
     * @param size how many characters will each chunk contain.
     * @return a String array with the split String chunks.
     * @throws NullPointerException thrown if the String passed in is null.
     * @throws IndexOutOfBoundsException thrown if the start is less than zero, the end is greater than the length
     * of the String - 1, the start is greater than the end, or the size is less than 1.
     */
    public static String[] splitToStringArray(String string, int start, int end, int size)
            throws NullPointerException, IndexOutOfBoundsException {
        if(string == null)
            throw new NullPointerException();

        if((start < 0) || (end > string.length() - 1) || (start > end) || (size < 1))
            throw new IndexOutOfBoundsException();

        int sizeOfRange = end - start + 1;
        int arraySize = sizeOfRange % size == 0 ? sizeOfRange / size : sizeOfRange / size + 1;
        String[] splitString = new String[arraySize];

        int index1, index2;
        for(int i = 0; i < arraySize; i++) {
            index1 = i * size + start;
            index2 = i * size + size + start;

            if(index2 > end)
                index2 = end + 1;

            splitString[i] = string.substring(index1, index2);
        }

        return splitString;
    }
}
