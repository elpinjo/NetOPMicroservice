package com.alliander.c8y.microservice.netop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;

import com.cumulocity.sdk.client.identity.IdentityApi;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.measurement.MeasurementApi;

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
	}
	
	@Autowired
	private IdentityApi identityAPI;
	
	@Autowired
	private MeasurementApi measurementAPI;
	
	@Autowired
	private InventoryApi inventoryAPI;

}
