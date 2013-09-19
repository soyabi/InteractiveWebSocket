package com.insideradius.testserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;


public class App 
{
	private static final int DEFAULT_PORT = 9000;
	private static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
				
			} catch(NumberFormatException nfe) {
				// Do nothing and use the default port
				logger.info("Invalid port specified, defaulting to port " + DEFAULT_PORT);
			}
		}

		Server server = new Server(port);
		WebSocketHandler wsHandler = new WebSocketHandler();
		wsHandler.setHandler(new DefaultHandler());
		server.setHandler(wsHandler);
		
		try {
			server.start();
			while(true){
				System.out.println("Enter Message:>");
		        try {
		        	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    	    String s = bufferRead.readLine();
		    	    if(s.length()>0)
		    	    	wsHandler.sendMessage(s);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
			//server.join();
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
}
