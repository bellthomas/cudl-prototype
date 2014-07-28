package com.yrs.util;

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
	
	
	
	
	
	
	
	//Type Classes
	public static interface IEmergencieInstruction {
		public void Execute(EmergencieConfig config);
	}
	
	public static class CallEmergencieInstruction implements IEmergencieInstruction{
		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
	}
	
	public static class TextEmergencieInstruction implements IEmergencieInstruction{
		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
	}
	
	public static class EmailEmergencieInstruction implements IEmergencieInstruction{
		@Override
		public void Execute(EmergencieConfig config) {
			//TODO
		}
	}
}
