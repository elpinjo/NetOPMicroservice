package com.alliander.c8y.microservice.netop.measurement;

import java.math.BigDecimal;

import com.cumulocity.model.measurement.MeasurementValue;

public class NetOpMeasurement {

	public void setVibration(BigDecimal aVibration) {
		
		this.vibration = new MeasurementValue(aVibration, "mm/s");
		
	}
	
	public void setVibrationValue(MeasurementValue aVibrationValue) {
		
		this.vibration = aVibrationValue;
	}
	
	private MeasurementValue vibration;
}
