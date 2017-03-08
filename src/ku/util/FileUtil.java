package ku.util;

import java.io.*;

/**
 * This class defines the ways to copy the file.
 * @author Sathira Kittisukmongkol
 */
public class FileUtil {

	/**
	 * Copy the file byte by byte.
	 * @param in , the input file to read.
	 * @param out , the output file to write.
	 * @throws RuntimeException
	 */
	static void copy(InputStream in, OutputStream out) throws RuntimeException {
		int box;
		try {
			box = in.read();
			while (box != -1) {
				out.write(box);
				box = in.read();
			}
			in.close();
			out.close();
		} catch (IOException ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * Copy the file by any byte.
	 * @param in , the input file to read.
	 * @param out , the output file to write.
	 * @param blocksize , set the size of byte to read.
	 * @throws RuntimeException
	 */
	static void copy(InputStream in, OutputStream out, int blocksize) throws RuntimeException {
		byte[] data = new byte[blocksize];
		int box;
		try {
			box = in.read(data);
			while (box != -1) {
				out.write(data);
				box = in.read(data);
			}
			in.close();
			out.close();
		} catch (IOException ex) {
			throw new RuntimeException();
		}
	}

	/**
	 * Copy the file line by line.
	 * @param in , the input file to read.
	 * @param out , the output file to write.
	 * @throws RuntimeException
	 */
	static void bcopy(InputStream in, OutputStream out) throws RuntimeException {
		try {
			InputStreamReader read2 = new InputStreamReader(in);
			BufferedReader buffReader = new BufferedReader(read2);

			OutputStreamWriter out2 = new OutputStreamWriter(out);
			PrintWriter printWriter = new PrintWriter(out);

			String box_read = buffReader.readLine();
			while (box_read != null) {
				printWriter.println(box_read);
				box_read = buffReader.readLine();
			}

			printWriter.close();
			buffReader.close();
		} catch (IOException ex) {
			throw new RuntimeException();
		}
	}

}
