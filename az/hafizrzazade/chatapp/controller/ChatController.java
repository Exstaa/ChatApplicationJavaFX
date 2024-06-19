package az.hafizrzazade.chatapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ChatController {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;

    private PrintWriter out;

    @FXML
    public void initialize() {
        // Start the networking setup in a new thread
        new Thread(() -> setupNetwork()).start();
    }

    private void setupNetwork() {
        try {
            System.out.println("Attempting to connect to the server...");
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to the server.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> messageArea.appendText(finalMessage + "\n"));
            }
        } catch (IOException e) {
            System.err.println("Failed to connect to the server");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSend() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    }
}
