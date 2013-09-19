package com.insideradius.testserver;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.WebSocket;


public class WebSocketHandler extends
		org.eclipse.jetty.websocket.WebSocketHandler {
	
	private static final Logger logger = Logger.getLogger(WebSocketHandler.class);
	private JsonWebSocket ws = new JsonWebSocket();
	
	public void sendMessage(String msg){
		try {
			ws.getConnection().sendMessage(msg);
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}
	
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest req, String proto) {
		return this.ws;
	}


	private class JsonWebSocket implements WebSocket.OnTextMessage {

		private Connection connection;

		@Override
		public void onOpen(Connection connection) {
			this.connection = connection;
			logger.info("Client Connected");
		}

		@Override
		public void onClose(int closeCode, String message) {
			logger.info("Client Disconnected");
		}

		@Override
		public void onMessage(String msg) {
			System.out.println(msg);
		}

		public Connection getConnection() { return this.connection; }
		
	}
}
