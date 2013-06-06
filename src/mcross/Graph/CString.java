package mcross.Graph;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

public class CString {
	
	public static final int DEFAULT_STRING_SIZE = 50;
	
	private static final String LOG_TAG = "CString Library";
	
	private int    mMaxLength    = 0;
	
	private char[] mString = null;

	public CString() {
		try {
			initialize(DEFAULT_STRING_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CString(int size) {
		try {
			initialize(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CString(String string) {
		copyString(string);
	}
	
	public void clearString() {
		for(int index=0; ((index < mMaxLength) && (mString[index] != '\0')); index++)
			mString[index] = '\0';
	}
	
	public void copyString(String value) {

		int size = value.length();
		
		try {
			if(size > mMaxLength)
				initialize(size);
		
			for(int index=0; index < size; index++)
				mString[index] = value.charAt(index);
			
			//sanitizeArray();
		}
		catch(Exception error) {
			error.printStackTrace();
			Log.e(LOG_TAG, error.getMessage());
		}
	}
	
	public final char[] getArray() {
		return mString;
	}
	
	public char getAt(int location) throws Exception {
		if(location < 0 && location >= mMaxLength)
			throw new Exception("Could not retrieve character. Index out of bounds");
		
		return mString[location];
	}
	
	public int getFillCount() {
		int filled = 0;
		
		for(int index=0; index < mMaxLength; index++)
			if(mString[index] != '\0')
				filled++;
			else 
				break;
		
		return filled;
	}
	
	public int getMaxLength() {
		return mMaxLength;
	}
	
	public String getString() {
		return truncateArray();
	}
	
	public CString clone() {
		CString ret = new CString(mMaxLength);
		
		ret.copyString(mString.toString());
		
		return ret;
	}

	private void initialize(int size) throws Exception {
		if(size <= 0)
			throw new Exception("Cannot initialize zero-length array");
		
		try {
			mMaxLength    = size;
			
			mString = new char [mMaxLength];
			
			clearString();
		}
		catch(Exception error) {
			error.printStackTrace();
			Log.e(LOG_TAG, error.getMessage());
		}
	}

	public void readArray(DataInputStream stream) {
		try {
			for(int index=0; index < mMaxLength; index++)
				mString[index] = stream.readChar();
			
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(LOG_TAG, e.getMessage());
		}
	}
	
	public boolean inSizeRange(String value) {
		int length = value.length();
		
		return ((value != null)       &&
				(length >= 0) &&
				(length <  mMaxLength));
	}

	/*
	private void sanitizeArray() {
		boolean encounter = false;
		
		for(int index=mMaxLength; index >= 0; index--)
		{
			if(mString[index] == '\0') 
			{
				if(encounter)
					mString[index] = ' ';
			}
			else {
				encounter = true;
			}
		}
	}
	*/

	private String truncateArray() {
		return String.copyValueOf(mString, 0, getFillCount());
	}

	public void writeArray(DataOutputStream stream) {
		try {
			for(int index=0; index <  mMaxLength; index++)
				stream.writeChar(mString[index]);
			
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(LOG_TAG, e.getMessage());
		}
	}
}
