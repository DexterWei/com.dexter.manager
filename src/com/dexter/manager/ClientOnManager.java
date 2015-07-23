package com.dexter.manager;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientOnManager {
	private static String ManagerUrl = "http://localhost:8080/com.dexter.manager/";
	private static String DeviceUrl = "http://localhost:8080/com.dexter.device/";
	
	public static void discover(String device){
		try {
			System.out.println("Sending discover request to client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"discover");
			ClientResponse response = r.accept("text/plain")
					.get(ClientResponse.class);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class)+"\n");
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	

	public static void read(String device, String RscName){
		try {
			System.out.println("Reading "+RscName+" value from client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"read/"+RscName);
			ClientResponse response = r.accept("text/plain")
					.get(ClientResponse.class);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void execute(String device, String RscName,String op){
		try {
			System.out.println("Reading "+RscName+" value from client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"execute/"+RscName+"/"+op);
			ClientResponse response = r.accept("text/plain")
					.get(ClientResponse.class);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void write(String device, String RscName,String val){
		try {
			System.out.println("Writing "+RscName+" value to client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"write/"+RscName);
			ClientResponse response = r.type("text/plain")
					.post(ClientResponse.class,val);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void create(String device,JSONObject nrc){
		try {
			System.out.println("Creating new resource"+ nrc.toString()+" on client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"create");
			ClientResponse response = r.type("application/json")
					.post(ClientResponse.class,nrc);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void observe(String device,String rsc,String url){
		try {
			System.out.println("Start observing resource"+ rsc.toString()+" on client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"observe");
			JSONObject obj = new JSONObject().put("Resource", rsc).put("Url", url);
			ClientResponse response = r.type("application/json")
					.post(ClientResponse.class,obj);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void writeAttr(String device,String rsc){
		try {
			System.out.println("Start observing resource"+ rsc.toString()+" on client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"writeAttr");
			JSONObject obj = new JSONObject().put("Resource", rsc).put("Cancel", "True");
			ClientResponse response = r.type("application/json")
					.post(ClientResponse.class,obj);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void delete(String device,String rsc){
		try {
			System.out.println("Deleting resource"+ rsc+" on client ...");
			Client client = Client.create();
			WebResource r = client.resource(device+"delete");
			ClientResponse response = r.type("text/plain")
					.post(ClientResponse.class,rsc);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			System.out.println("echo:\n"+response.getEntity(String.class));
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	public static void main(String[] args) throws JSONException{
		// TODO Auto-generated method stub
		discover(DeviceUrl);
		read(DeviceUrl,"Thermometer");
		writeAttr(DeviceUrl,"Thermometer");

		write(DeviceUrl,"Thermometer","4 Celcius");
		read(DeviceUrl,"Thermometer");
		JSONObject nrc = new JSONObject().put("Name","Light").put("Value", "Off").put("Observed", "N").put("Observer", "");
		create(DeviceUrl,nrc);
		discover(DeviceUrl);
		delete(DeviceUrl,"Light");
		discover(DeviceUrl);
		execute(DeviceUrl,"IceMaker","On");
		discover(DeviceUrl);
		observe(DeviceUrl,"Thermometer",ManagerUrl+"notification");
		discover(DeviceUrl);
		for(int i=0;i<12;i++){
			write(DeviceUrl,"Thermometer",i+" Celcius");
			read(DeviceUrl,"Thermometer");
		}
		discover(DeviceUrl);
	}

}
