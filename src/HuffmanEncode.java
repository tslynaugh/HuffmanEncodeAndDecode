/**
* CS 340 Assignment 3
 * 
 *  This project will encode a text file into a smaller-sized encoded
 * text file. The compression is typically 50%-60%
 * 
 * @author Thomas Lynaugh
 * 
 * Last Modified: October 24, 2018
 *
 */

import java.io.*;
import java.util.*;

public class HuffmanEncode {
	public HuffmanEncode(String in, String out) throws IOException {
		// Implements the Huffman encoding algorithm
		// Add private methods and instance variables as needed

		File input = new File(in);

		int charCount = totalChars(input);

		// create array that holds frequencies of chars, stored at an
		// index equal to that char's Ascii code
		int[] charFrequencies = findCharFrequencies(input);
		int charsAppearing = countChars(charFrequencies);

		HuffmanTree charTree = huffmanRepresentation(charFrequencies, charsAppearing);

		// fill an array the the character encodings. The encoding
		// will be stored at it's respective char's Ascii code as
		// its array index
		String[] charEncodings = new String[127];

		String next;

		Iterator<String> iter = charTree.iterator();

		while (iter.hasNext()) {
			next = iter.next();

			if (next.length() > 0) {
				charEncodings[next.charAt(0)] = next.substring(2);

			}

		}

		String representation = iter.toString();

		HuffmanOutputStream outputStream = new HuffmanOutputStream(out, representation, charCount);

		BufferedReader br = new BufferedReader(new FileReader(in));

		String nextEncoding;
		int r;

		// a non-essential progress bar while the chars are encoded
		int progression = charCount / 10;
		int progressCounter = 0;
		int counter = 0;
		System.out.println("Encoding...");

		while ((r = br.read()) != -1) {

			counter++;

			if (r == -1) {
				return;
			}

			// display progress bar
			if (counter % progression == 0) {
				progressCounter++;
				System.out.println(progressCounter * 10 + "%");
			}

			nextEncoding = charEncodings[(int) r];

			for (int i = 0; i < nextEncoding.length(); i++) {
				outputStream.writeBit(nextEncoding.charAt(i));

			}
		}

		outputStream.close();
		br.close();

		System.out.println("Encoding Complete!");

		System.out.println("Encoded " + counter + " characters out of " + charCount + " total");

	}

	public HuffmanTree huffmanRepresentation(int[] charFrequencies, int charsAppearing) {
		// create a huffman tree with the chars based upon their frequencies

		// find the lowest two values in the huffmanTable array. In this
		// implementation, leftChild < rightChild

		BinaryHeap charHeap = new BinaryHeap(charsAppearing);

		int priority;
		HuffmanTree tree;

		// insert all char frequencies into a binary heap
		for (int i = 0; i < charFrequencies.length; i++) {
			if (charFrequencies[i] > 0) {
				priority = charFrequencies[i];
				tree = new HuffmanTree((char) i);
				charHeap.insert(priority, tree);
			}
		}

		// pull out the two most minimal priorities, merge them into
		// a tree, and then put that tree back into the binary heap
		// until the binary heap consists of one single tree
		while (true) {
			int leftFreq = charHeap.getMinPriority();
			HuffmanTree leftTree = charHeap.getMinTree();
			charHeap.removeMin();

			int rightFreq = charHeap.getMinPriority();
			HuffmanTree rightTree = charHeap.getMinTree();
			charHeap.removeMin();

			HuffmanTree mergedTree = new HuffmanTree(leftTree, rightTree, (char) 128);
			// the new merged tree's frequency is equal to leftTree frequency + right
			// tree frequency
			int combinedFrequency = leftFreq + rightFreq;

			charHeap.insert(combinedFrequency, mergedTree);

			if (charHeap.size == 1) {
				break;
			}

		}

		// the final tree in the binary heap is the Huffman tree of all char
		// frequencies
		HuffmanTree charTree = charHeap.getMinTree();

		return charTree;
	}

	public int totalChars(File filename) throws FileNotFoundException {
		// return the total number of chars in initial input file

		Scanner fileScanner = new Scanner(filename);

		int chars = 0;

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			// include a space after each word
			line += " ";
			chars += line.length() + 1;

		}

		fileScanner.close();
		System.out.println("Total num of chars " + chars);
		return chars;

	}

	public int countChars(int[] charFrequencies) {
		// return the count of individual characters that appear in the file

		int count = 0;
		for (int i = 0; i < charFrequencies.length; i++) {
			if (charFrequencies[i] > 0) {
				count++;
			}
		}

		return count;
	}

	public int[] findCharFrequencies(File filename) throws IOException {
		// create array for every character in ASCII. Array size increased by
		// by one to hold end of line value, when done going through file
		int[] charFrequencies = new int[127];

		// initialize each array value to 0
		for (int x = 0; x < charFrequencies.length; x++) {
			charFrequencies[x] = 0;
		}

		BufferedReader in = new BufferedReader(new FileReader(filename));

		// go through entire file and work on each word individually

		int asciiValue;
		// update char frequency array based on chars in
		// current word
		while ((asciiValue = in.read()) != -1) {

			// determine the index to update frequency of, which
			// will equal the Ascii code of the current char

			// update frequency at the index equal to the current
			// char's Ascii code
			charFrequencies[asciiValue]++;
		}

		char[] asciiArray = asciiArray();

		return charFrequencies;

	}

	public char[] asciiArray() {
		// return an array with each ASCII character at an index equal to its
		// ASCII code

		char[] asciiArray = new char[127];

		for (int i = 0; i < asciiArray.length; i++) {

			asciiArray[i] = (char) (i);

		}

		return asciiArray;
	}

	public static void main(String args[]) throws IOException {
		// args[0] is the name of the file whose contents should be compressed
		// args[1] is the name of the output file that will hold the compressed

		new HuffmanEncode(args[0], args[1]);

		// do not add anything here
	}
}