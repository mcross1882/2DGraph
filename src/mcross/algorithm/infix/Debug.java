package mcross.algorithm.infix;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Debug {
	private static final String DEBUG_LOG_PATH = "debug.txt";
	
	final public static void makeLog(Exception err) {
		FileOutputStream fos = null;
		
		PrintWriter writer   = null;
		
		try {
			fos    = new FileOutputStream(DEBUG_LOG_PATH);
			writer = new PrintWriter(fos);
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy (HH:mm:ss)");
			Calendar cal = Calendar.getInstance();
			
			writer.write("CREATION DATE (TIME): ");
			writer.write(dateFormat.format(cal.getTime()));
			writer.write("\n\n");
			
			err.printStackTrace(writer);
			
			writer.flush();
			writer.close();
			fos.close();
		}
		
		catch (FileNotFoundException fileNotFound) {
			System.err.println("Fatal Error: Could not output debug log");
		}
		
		catch (IOException ioErr) {
			System.err.println("Fatal Error: Could not close file stream");
		}
	}
}
