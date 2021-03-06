package com.dexter.manager;

import java.net.UnknownHostException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import java.util.Date;

public class ManagerDAO {
	private static MongoClient client=null;
	
	public static MongoClient Connect(){
		if(client!=null)
			return client;
		try{
		client = new MongoClient("localhost" , 27017);
		}catch(UnknownHostException ex){
			System.err.println(ex);
		}
		return client;
	}


	public static void InsertRecord(JSONObject obj) throws JSONException{
		DB db = client.getDB("Record");
		//String maker = (String)obj.get("Manufacturer");
		DBCollection clnt=db.getCollection("FRIDGE");
		BasicDBObject dbj = (BasicDBObject)JSON.parse(obj.toString());
		clnt.insert(dbj);
	}
	
	public static int CountRecord() throws JSONException{
		DB db = client.getDB("Record");
		//String maker = (String)obj.get("Manufacturer");
		DBCollection clnt=db.getCollection("FRIDGE");
		DBCursor rst = clnt.find(new BasicDBObject());
		return rst.count();
	}
	/*
	public static boolean FindModel(String manufacturer,String model){
		DB db=client.getDB("RegServer1");
		DBCollection clnt = db.getCollection("inventory");
		DBCursor rst = clnt.find(new BasicDBObject().append("Manufacturer", manufacturer).append("Model",model));
		if(rst.count()==1){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean FindSubscriber(JSONObject device) throws JSONException{
		DB db=client.getDB("RegServer1");
		String collection=(String) device.get("Manufacturer");
		
		DBCollection clnt= db.getCollection(collection);
		try{
			DBCursor rst= clnt.find(new BasicDBObject()
										.append("Manufacturer", device.get("Manufacturer"))
										.append("Model",device.get("Model"))
										.append("SN",device.get("SN")));
			if(rst.count()==1) {
				return true;
			}else{
				return false;
			}
		}catch(JSONException ex){
			return false;
		}
	}
	
	public static String UpdateSubscriber(JSONObject device) throws JSONException{
		DB db=client.getDB("RegServer1");
		String collection=(String) device.get("Manufacturer");
		
		DBCollection clnt= db.getCollection(collection);
		try{
			BasicDBObject query = new BasicDBObject()
										.append("Manufacturer", device.get("Manufacturer"))
										.append("Model",device.get("Model"))
										.append("SN",device.get("SN"));
	        BasicDBObject update = new BasicDBObject();
	        update.put("$set", new BasicDBObject("Resources",(BasicDBObject)JSON.parse(device.get("Resources").toString())));
			
	        WriteResult rst= clnt.update(query,update);
	        if(rst.getN()==1)
	        	return "Update Done";
	        else
	        	return "Update aborted";
		}catch(JSONException ex){
			return ex.toString();
		}
	}
	
	public static String DeregisterSubscriber(JSONObject device) throws JSONException{
		DB db=client.getDB("RegServer1");
		String collection=(String) device.get("Manufacturer");
		
		DBCollection clnt= db.getCollection(collection);
		try{
			BasicDBObject query = new BasicDBObject()
										.append("Manufacturer", device.get("Manufacturer"))
										.append("Model",device.get("Model"))
										.append("SN",device.get("SN"));
	        BasicDBObject update = new BasicDBObject();
	        update.put("$set", new BasicDBObject("EndTime",new Date().toString()));
			
	        WriteResult rst= clnt.update(query,update);
	        if(rst.getN()==1)
	        	return "De-register Done";
	        else
	        	return "De-register failed";
		}catch(JSONException ex){
			return ex.toString();
		}
	}
		*/
	
	public static void DisConnect(){
		client.close();
	}

}
