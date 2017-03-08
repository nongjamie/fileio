package ku.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.xml.stream.util.StreamReaderDelegate;

import stopwatch.TaskTimer;

/**
 * This class defines code used to create Runnable 'tasks' to test
 * the file copy methods in FileUtil.  The subclasses should
 * define their own run() method to perform the actual task.
 * 
 * See the main method for example of how to use this.
 * It uses the Stopwatch and TaskTimer classes from stopwatch project.
 * 
 * All the file copy methods require an input stream (read from file)
 * and an output stream that writes to a file, so this class defines
 * methods to open a file as an InputStream and open an output file 
 * as an OutputStream.  Files can be opened via the constructor or
 * setInput(filename) and setOutput(filename) methods.
 * 
 * The method to open an InputStream shows how to use the ClassLoader
 * to find a file on the classpath of this project.  The classpath
 * includes files in your project's src/ directory.  
 * It is a standard technique for opening resources.
 * 
 * @author Sathira Kittisukmongkol
 *
 */
public class FileCopyTask implements Runnable {
	/** The InputStream that data will be read form. */
	protected InputStream in = null;
	/** The OutputStream that data will be written to. */
	protected OutputStream out = null;

	/** Default constructor doesn't do anything but may be
	 *  needed for subclasses that don't invoke parameterized constructor. 
	 */
	public FileCopyTask() {

	}

	/**
	 * Initialize a FileCopyTask with names of the input and output
	 * files to use.
	 * @param infile name of the file to use as input
	 * @param outfile name of the file to use as output
	 * @throws RuntimeException if either file cannot be opened
	 */
	public FileCopyTask(String infile, String outfile) {
		setInput(infile);
		setOutput(outfile);
	}

	/**
	 * Set the file to use as this object's 'in' attribute (InputStream).
	 * @param filename is the name of a file to read as input
	 * @throws RuntimeException if the filename cannot be opened for
	 *    input, which usually means file not found.
	 */
	public void setInput(String filename) {
		in = null;
		try {
			// If the filename is an absolute path or is in the "current"
			// directory then using FileInputStream should open it.
			in = new FileInputStream(filename);
		} catch (FileNotFoundException fne) {
			// ignore it and try again
		}
		if(in != null) {
			return ;
		}
		
		// The ClassLoader knows the application's classpath
		// and can open files that are on the classpath.
		// The filename can have a relative directory to refer to
		// subdirectories of the project source tree.
		ClassLoader loader = this.getClass().getClassLoader();
		in = loader.getResourceAsStream(filename);

		// If loader.getResourceAsStream() cannot create an InputStream
		// then it returns null.  (No exception is thrown.)
		// If 'in' is null then throw a RuntimeException 
		// so the caller will know that filename could not be opened.

		if(in == null) {
			throw new RuntimeException("Don't have "+filename);
		}
	}

	/**
	 * Specify a filename to use as the OutputStream (out attribute).
	 * 
	 * @param filename is the name of the file to write to.
	 *     If the file already exists it will be overwritten.
	 * @throws RuntimeException if the filename cannot be opened as
	 *     an OutputStream.
	 */
	public void setOutput(String filename) {
		try {
			// Use FileOutputStream.
			out = new FileOutputStream(filename);
		} catch (FileNotFoundException fne ) {
			// rethrow it as an unchecked exception.
			throw new RuntimeException("could not open output file "+filename, fne);
		}
	}

	/**
	 * The run() method should be overridden by subclasses
	 * to perform a task.
	 */
	public void run() {
		System.out.println("You forgot to override run in subclass.");
	}

	/**
	 * The toString() method should be overridden by subclasses
	 * to describe the task.
	 */
	public String toString() {
		return "Pay attention! You forgot to write toString in subclass.";
	}

	/**
	 * This main method could be in a separate class, for clarity.
	 * It uses this class to create subclasses for each task.
	 * It uses Stopwatch and TaskTimer to execute the task.
	 * @param args.
	 */
	public static void main(String[] args) {
		// Name of file that want to read.
		final String inputFilename = "Big-Alice-in-Wonderland.txt";
		// Set the size to copy.
		final int byte1KB = 1024 ;
		final int byte4KB = 1024*4 ;
		final int byte64KB = 1024*64;
		// To count the time of each task.
		TaskTimer timer = new TaskTimer();

		/**
		 * Define a FileUtil task to copy a file byte by byte.
		 * This is an anonymous class that extends FileUtilTime.
		 */
		FileCopyTask taskOneByte = new FileCopyTask( inputFilename , "/tmp/filecopy1.txt" ) {

			// Start to copy a file byte by byte.
			// By method named "copy" in the FileUtil class.
			public void run() {
				try{
					FileUtil.copy( in , out );
				}catch( Exception ex ) {
					throw new RuntimeException();
				}
			}

			// Display a topic of the task.
			public String toString() {
				return "1.Copy a file byte-by-byte : ";
			}

		};

		/**
		 * Define a FileUtil task to copy a file 1KB by 1KB.
		 * This is an anonymous class that extends FileUtilTime.
		 */
		FileCopyTask task1KBByte = new FileCopyTask( inputFilename , "/tmp/filecopy2.txt" ) {

			// Start to copy a file 1KB by 1KB.
			// By method named "copy" in the FileUtil class.
			public void run() {
				try{
					FileUtil.copy( in , out , byte1KB );
				}catch( Exception ex ) {
					throw new RuntimeException();
				}
			}

			// Display a topic of the task.
			public String toString() {
				return "2.Copy a file using a byte 1KB : ";

			}
		};

		/**
		 * Define a FileUtil task to copy a file 4KB by 4KB.
		 * This is an anonymous class that extends FileUtilTime.
		 */
		FileCopyTask task4KBByte = new FileCopyTask( inputFilename , "/tmp/filecopy3.txt" ) {
			
			// Start to copy a file 4KB by 4KB.
			// By method named "copy" in the FileUtil class.
			public void run() {
				try{
					FileUtil.copy( in , out , byte4KB );
				}catch( Exception ex ) {
					throw new RuntimeException();
				}
			}

			// Display a topic of the task.
			public String toString() {
				return "3.Copy a file using a byte 4KB : ";

			}
		};

		/**
		 * Define a FileUtil task to copy a file 64KB by 64KB.
		 * This is an anonymous class that extends FileUtilTime.
		 */
		FileCopyTask task64KBByte = new FileCopyTask( inputFilename , "/tmp/filecopy4.txt" ) {

			// Start to copy a file 64KB by 64KB.
			// By method named "copy" in the FileUtil class.
			public void run() {
				try{
					FileUtil.copy( in , out , byte64KB );
				}catch( Exception ex ) {
					throw new RuntimeException();
				}
			}

			// Display a topic of the task.
			public String toString() {
				return "4.Copy a file using a byte 64KB : ";
			}
		};

		/**
		 * Define a FileUtil task to copy a file line by line.
		 * This is an anonymous class that extends FileUtilTime.
		 */
		FileCopyTask taskLine = new FileCopyTask( inputFilename , "/tmp/filecopy5.txt" ) {

			// Start to copy a file line by line.
			// By method named "copy" in the FileUtil class.
			public void run() {
				try{
					FileUtil.bcopy( in , out );
				}catch( Exception ex ) {
					throw new RuntimeException();
				}
			}

			// Display a topic of the task.
			public String toString() {
				return "5.Copy a file Line-by-Line : ";
			}
		};

		/**
		 * Create an array of FileCopyTask to keep objects of the above tasks.
		 */
		FileCopyTask[] fileCopyTask = { taskOneByte , task1KBByte , task4KBByte , task64KBByte , taskLine };

		/**
		 * Loop to do all the tasks.
		 */
		for( int i=0 ; i<fileCopyTask.length ; i++ ) {
			timer.measureAndPrint( fileCopyTask[i] );
		}

	}
}