package com.yrs.util;

import java.io.IOException;

/**
 * 
 * Simple data set class for instructions to be carried out by the phone
 *
 */
public class EmergencieConfig {
	
	public IEmergencieInstruction[] instructionSet;
	
	public LocationUtil location = LocationUtil.instance;
	
	public EmergencieConfig(IEmergencieInstruction[] instrns) {
		instructionSet = instrns;
	}
	
	
	
	public void Execute() {
		//Run Location Service
		location.DefaultPinPoint();
		
		
		//ExecuteAllConfigs
		for(IEmergencieInstruction instrn :instructionSet) {
			instrn.Execute(this);
		}
	}
	
	public SaveUtil getSaveUtil() {
		SaveUtil save = new SaveUtil();
		save.buffer.putInt(instructionSet.length);
		for(IEmergencieInstruction instrn : instructionSet) {
			save.putString(instrn.getClass().toString());
			save.putSaveUtil(instrn.getSave());
		}
		return save;
	}
	
	@SuppressWarnings("rawtypes")
	public static EmergencieConfig loadFromSaveUtil(SaveUtil save) throws IOException, ReflectiveOperationException {
		EmergencieConfig config = new EmergencieConfig(new IEmergencieInstruction[save.buffer.getInt()]);
		for(int i = 0; i < config.instructionSet.length; i++) {
			Class clazz = Class.forName(save.getString());
			IEmergencieInstruction instruction = (IEmergencieInstruction) clazz.newInstance();
			instruction.loadFromSaveUtil(save.getSaveUtil());
			config.instructionSet[i] = instruction;
		}
		return config;
	}
	
	
	
	
	//Type Classes
	public static interface IEmergencieInstruction {
		public void Execute(EmergencieConfig config);
		public SaveUtil getSave(); //Save EVERYTHING
		public void loadFromSaveUtil(SaveUtil save);
	}
	
	public static class CallEmergencieInstruction implements IEmergencieInstruction{
		
		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
		
		@Override
		public SaveUtil getSave() {
			return null; //TODO
		}
		
		@Override
		public void loadFromSaveUtil(SaveUtil save) {
			//TODO
		}
		
	}
	
	public static class TextEmergencieInstruction implements IEmergencieInstruction{

		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
		
		@Override
		public SaveUtil getSave() {
			return null; //TODO
		}
		
		@Override
		public void loadFromSaveUtil(SaveUtil save) {
			//TODO
		}
		
	}
	
	public static class EmailEmergencieInstruction implements IEmergencieInstruction{

		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
		
		@Override
		public SaveUtil getSave() {
			return null; //TODO
		}
		
		@Override
		public void loadFromSaveUtil(SaveUtil save) {
			//TODO
		}
		
	}
}
