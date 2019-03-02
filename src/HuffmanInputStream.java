import java.io.*;

public class HuffmanInputStream {
	// add additional private variables and methods as needed
	// DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;

	int count = 0;
	int currByte;
	int[] bitArr = new int[9];

	public HuffmanInputStream(String filename) {

		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();

		} catch (IOException e) {
			System.out.println("HuffmanInputStream caught an IOException");
		}

		// add other initialization statements as needed

	}

	public int readBit() throws IOException {
		// returns the next bit in the file
		// the value returned will be either a 0 or a 1

		if (count == 0) {
			currByte = d.readUnsignedByte();
			count = 8;

			for (int i = 7; i >= 0; i--) {
				bitArr[i] = currByte % 2;
				currByte = currByte / 2;

			}
		}
		int temp = count - 1;
		count--;

		return bitArr[temp];

	}

	public String getTree() {
		// return the tree representation read from the file

		return tree;
	}

	public int getTotalChars() {
		// return the character count read from the file

		return totalChars;
	}
}
