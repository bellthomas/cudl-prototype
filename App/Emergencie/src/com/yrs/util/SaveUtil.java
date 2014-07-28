package com.yrs.util;

import java.nio.ByteBuffer;

public class SaveUtil { //Use save.buffer.getInt() etc. for other types of data and make sure to read/write in the same order to ensure the same result is returned that is saved
	
	public ByteBuffer buffer;
	
	public SaveUtil() {
		buffer = ByteBuffer.allocateDirect(512); //TODO check if large/small enough
	}
	
	public void putString(String STRING) {
		buffer.putInt(STRING.length());
		for(char CHAR : STRING.toCharArray()) {
			buffer.putChar(CHAR);
		}
	}
	
	public String getString() {
		int size = buffer.getInt();
		char[] chars = new char[size];
		for(int i = 0; i < size; i++) {
			chars[i] = buffer.getChar();
		}
		return new String(chars);
	}
	
	public void putSaveUtil(SaveUtil save) {
		int size = save.buffer.position();
		buffer.putInt(size);
		for(int i = 0; i < size; i++) {
			buffer.put(save.buffer.get(i));
		}
	}
	
	public SaveUtil getSaveUtil() {
		int size = buffer.getInt();
		SaveUtil save = new SaveUtil();
		for(int i = 0; i < size; i++) {
			save.buffer.put(buffer.get());
		}
		save.buffer.position(0); //Set ready for reading
		return save;
	}
	
}
