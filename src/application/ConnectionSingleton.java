package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

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
    
    public String get(String command, Object obj) {
    	String payload = new Gson().toJson(obj);
    	System.out.println("Sending command to server with data: " + payload);
    	SocketClientCallable commandWithSocket = new SocketClientCallable(HOSTNAME, PORT, command, payload);
    	Future<String> response = es.submit(commandWithSocket);
		try {
			// Blocking this thread until the server responds
			serverResponse = response.get();
			System.out.println("Response from server is : " + serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Connection to server terminated");
    	return serverResponse;
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
