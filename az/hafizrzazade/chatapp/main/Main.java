package az.hafizrzazade.chatapp.main;

import java.io.IOException;
import java.net.ServerSocket;
import az.hafizrzazade.chatapp.server.ChatServer.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String args[]) {
	    try (ServerSocket serverSocket = new ServerSocket(12345)) {
	        System.out.println("Chat server started and listening on port " + 12345);
	        while (true) {
	            new ClientHandler(serverSocket.accept()).start();
	            System.out.println("Accepted a new connection");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		AnchorPane pane;
		try {
			pane = FXMLLoader
					.load(getClass().getClassLoader().getResource("az/hafizrzazade/chatapp/design/app.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Chat Application");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
