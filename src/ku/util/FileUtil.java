package ku.util;

import java.io.*;

public class FileUtil {

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
