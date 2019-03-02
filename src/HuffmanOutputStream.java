import java.io.*;
import java.util.Iterator;

public class HuffmanOutputStream {
	// add additional private variables as needed
	// do not modify the public methods signatures or add public methods
	DataOutputStream d;
	private int bits = 0;
	private int count = 0;
	private char[] flip = new char[9];

	public HuffmanOutputStream(String filename, String tree, int totalChars) throws IOException {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);

		} catch (IOException e) {

		}

		// add other initialization statements as needed

	}

	public void writeBit(char bit) throws IOException {
		// PRE:bit == '0' || bit == '1'
		flip[count] = bit;

		count++;

		if (count == 8) {
			for (int i = 7; i >= 0; i--) {
				bits = (bits * 2) + (flip[i] - 48);

			}

			d.write(bits);
			count = 0;
			bits = 0;

		}

	}

	public void close() throws IOException {
		// write final byte (if needed)
		// close the DataOutputStream

		if (count == 0) {
			while (count < 8) {
				flip[count] = '0';
				count++;
			}

		}

		if (count == 8) {
			for (int i = 7; i >= 0; i--) {
				bits = (bits * 2) + (flip[i] - 48);

			}
			d.write(bits);
			count = 0;
			bits = 0;

		}

		d.write(bits);
		d.flush();
		d.close();

	}
}