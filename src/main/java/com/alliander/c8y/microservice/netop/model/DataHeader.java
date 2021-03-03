package com.alliander.c8y.microservice.netop.model;

public class DataHeader {

	public boolean containerReadTimestamp;
	
	public int slotNumber;
	
	public boolean mightHaveError;
	
	public enum sensorFunction { NA, SHAKING, TWO_PLANE_TILT, VIBRATION, ONE_PHASE_CURRENT, THREE_PHASE_CURRENT, DRY_CONTACT, TRACKING, GEOFENCE, TEMPERATURE, HUMIDITY, TEMP_HUMID, DOOR_OPEN_CLOSE, THREE_PLANE_TILT, ONE_BUTTON, THREE_BUTTON, DOOR}
	
	public enum sensorBoardType { DEVICE_INFO, THREE_AXIS_ACCELOMETER, CURRENT_TRANSFORMER, DRY_CONTACT};
}
