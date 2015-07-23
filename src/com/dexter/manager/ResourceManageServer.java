package com.dexter.manager;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/")
public class ResourceManageServer {
	public static String log="tmp:";
	public static int count = 0;
	
	@Path("/get")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(){
		String url = "http://localhost:8080/com.dexter.device/get";
		try {
			Client client = Client.create();
			WebResource r = client.resource(url);
			ClientResponse response = r.accept("text/plain").get(ClientResponse.class);
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			
			String retstr=response.getEntity(String.class);
			return retstr+"\n"+log;
		  } catch (Exception e) {
			e.printStackTrace();
			return "we got problem here\n";
		  }
	}
	
	@Path("/notification")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setName(String objString){
		JSONObject obj;
		ManagerDAO.Connect();
		try {
			obj = new JSONObject(objString);
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Calendar calobj = Calendar.getInstance();
			obj.put("Time",df.format(calobj.getTime()));
			//log+="\n"+obj.toString();
			ManagerDAO.InsertRecord(obj);
			if(ManagerDAO.CountRecord()==10){
				return Response.status(201).entity("stop").build();
			}else
				return Response.status(201).entity("go on").build();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(202).entity("rename failed").build();
		}
	}
	
	@Path("/set/{new}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String set(@PathParam("new") String nname){
		String url = "http://localhost:8080/com.dexter.device/set";
		try {
			Client client = Client.create();
			WebResource r = client.resource(url);
			ClientResponse response = r.type("application/json")
					.post(ClientResponse.class, new JSONObject().put("Name", nname));
	
			if (response.getStatus() > 202) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			
			String retstr=response.getEntity(String.class);
			return retstr;
		  } catch (Exception e) {
			e.printStackTrace();
			return "we got problem here\n";
		  }
	}
}
