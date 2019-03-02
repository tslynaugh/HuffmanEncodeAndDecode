/**
 * CS 340 Assignment 3
 * 
 *  This project will decode an encrypted text file based
 * upon the encodings it was encoded with.
 * 
 * @author Thomas Lynaugh
 * 
 * Last Modified: October 24, 2018
 *
 */

import java.io.*;
import java.util.*;
import java.lang.Math;

public class HuffmanDecode {

	public HuffmanDecode(String in, String out) throws IOException {
		// implements the Huffman Decode Algorithm
		// Add private methods and instance variables as needed

		HuffmanInputStream decode = new HuffmanInputStream(in);

		String representation = decode.getTree();

		HuffmanTree encodingTree = new HuffmanTree(representation, (char) 128);

		// retrieve the encodings for each character from tree representation
		// in the file input to decode

		long counter = 0;
		int progressCounter = 0;

		FileWriter fw = new FileWriter(out);

		System.out.println("Decoding...");

		// with the 8 bits, check for encodings
		int totalChars = decode.getTotalChars();

		int c;
		// a non-essential progress bar while the chars are decoded
		int progression = totalChars / 10;

		encodingTree.moveToRoot();
		while (counter < totalChars) {

			if (encodingTree.atLeaf()) {
				fw.write(encodingTree.current());
				counter++;
				// display progress bar
				if (counter % progression == 0) {
					progressCounter++;
					System.out.println(progressCounter * 10 + "%");
				}
				encodingTree.moveToRoot();

			}

			if (counter == totalChars) {
				break;

			}

			// c will either be a 0 or a 1
			c = decode.readBit();

			if (c == 0) {
				encodingTree.moveToRight();
			} else if (c == 1) {
				encodingTree.moveToLeft();
			}

			// check if encoding is equal to a char value

		}

		System.out.println("Decoding Complete!");
		System.out.println("Decoded " + counter + " characters out of " + totalChars + " total");

		fw.close();
	}

	public static void main(String args[]) throws IOException {
		// args[0] is the name of a input file (a file created by Huffman Encode)
		// args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		// do not add anything here
	}
}