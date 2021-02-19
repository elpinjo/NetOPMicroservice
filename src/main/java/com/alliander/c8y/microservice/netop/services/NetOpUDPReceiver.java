package com.alliander.c8y.microservice.netop.services;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;

import com.alliander.c8y.microservice.netop.model.NetOpMessage;
import com.cumulocity.model.ID;
import com.cumulocity.rest.representation.identity.ExternalIDRepresentation;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.cumulocity.sdk.client.identity.IdentityApi;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.measurement.MeasurementApi;

import c8y.Hardware;
import c8y.IsDevice;

@MessageEndpoint
public class NetOpUDPReceiver {
	
	@Bean
	public IntegrationFlow udpIn() {
	  return IntegrationFlows.from(Udp.inboundAdapter(3434))
	          .channel("udpChannel")
	          .get();
	}
	
	@ServiceActivator(inputChannel = "udpChannel")
	public void processNetOpMessage(byte[] in) {
		
		System.out.println("Echo: " + new String(in));
		
		MessageDecoder netopDecoder = new MessageDecoder();
		
		NetOpMessage myMessage = netopDecoder.decode(in);
		
		ExternalIDRepresentation myDeviceId;
		
		try {
			myDeviceId = identityAPI.getExternalId(new ID("c8y_Serial", myMessage.serialNumber));
		} catch (RuntimeException anException) {
			// Device could not be found, so we create a new device
			myDeviceId = createDevice(myMessage.serialNumber);
		}
				
		MeasurementRepresentation myMeasurement = new MeasurementRepresentation();
		
		myMeasurement.setSource(myDeviceId.getManagedObject());
		myMeasurement.setDateTime(new DateTime());
		myMeasurement.set(myMessage);
				
		measurementAPI.create(myMeasurement);
		
	}
	
	private ExternalIDRepresentation createDevice(String aSerialNumber) {
		
		ExternalIDRepresentation myExternalId = new ExternalIDRepresentation(); 
				
		ManagedObjectRepresentation myDevice = new ManagedObjectRepresentation();
		
		myDevice.setName("NetOp - " + aSerialNumber);
		myDevice.setType("netop_sensor");
		myDevice.set(new IsDevice());

		Hardware myHardware = new Hardware();
		myHardware.setModel("NetOp door sensor");
		myHardware.setRevision("1.0"); // replace with revision from the decoding of the message??
		myHardware.setSerialNumber(aSerialNumber);
		
		myDevice.set(myHardware);
		
		inventoryAPI.create(myDevice);
		
		myExternalId.setExternalId(aSerialNumber);
		myExternalId.setType("c8y_Serial");
		myExternalId.setManagedObject(myDevice);
		
		identityAPI.create(myExternalId);
		
		return myExternalId;
		
	}

	@Autowired
	private IdentityApi identityAPI;
	
	@Autowired
	private MeasurementApi measurementAPI;
	
	@Autowired
	private InventoryApi inventoryAPI;

}
