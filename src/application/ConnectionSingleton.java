package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConnectionSingleton {
	public static String serverResponse;
	public static final String HOSTNAME = "localhost";
	public static final int PORT = 9001;
	ExecutorService es = Executors.newCachedThreadPool();
    private static ConnectionSingleton instance;
    
    private ConnectionSingleton(){}
    
    public static synchronized ConnectionSingleton getInstance(){
        if(instance == null){
            instance = new ConnectionSingleton();
        }
        return instance;
    }
    
    private SocketClientCallable getCommandWithSocket(String command, Object obj) {
    	String payload = new Gson().toJson(obj);
    	System.out.println("Sending command to server with data: " + payload);
    	SocketClientCallable commandWithSocket = new SocketClientCallable(HOSTNAME, PORT, command, payload);
    	return commandWithSocket;
    }
    
    public JsonObject get(String command, Object obj) throws Exception {
    	Future<String> response = es.submit(getCommandWithSocket(command, obj));
		try {
			// Blocking this thread until the server responds
			serverResponse = response.get();
			System.out.println("Response from server is: " + serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("get Connection to server terminated");
		if(serverResponse == null) {
			throw new Exception("Null server response error!");
		}
		JsonElement sr = new JsonParser().parse(serverResponse);
		if(!sr.isJsonObject()) {
			throw new Exception("Not a json Object."); 
		}
		JsonObject jsonObject = sr.getAsJsonObject();
		return jsonObject;
    }

	public JsonArray gets(String command, Object obj) throws Exception {
     	Future<String> response = es.submit(getCommandWithSocket(command, obj));
		try {
			// Blocking this thread until the server responds
			serverResponse = response.get();
			System.out.println("Response from server is: " + serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("gets Connection to server terminated");
		if(serverResponse == null) {
			throw new Exception("Null server response error!");
		}
		JsonElement sr = new JsonParser().parse(serverResponse);
		if(!sr.isJsonArray()) {
			throw new Exception("Not a json Array."); 
		}
		JsonArray jsonArray = sr.getAsJsonArray();
		return jsonArray;
    }
    
    public void shutdown() {
		this.es.shutdown();
		try {
		    if (!this.es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		        this.es.shutdownNow();
		    } 
		} catch (InterruptedException e) {
		    this.es.shutdownNow();
		}
    }

}
