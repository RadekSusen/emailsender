package utb.fai;

import java.io.*;
import java.net.*;

public class EmailSender {

    private Socket socket;
    private OutputStream output;
    private BufferedReader input;

    public EmailSender(String host, int port) throws IOException {
        socket = new Socket(host, port);
        output = socket.getOutputStream();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        readResponse(); // Čtení úvodní odpovědi serveru
    }

    public void send(String from, String to, String subject, String text) throws IOException {
        if (from == null || to == null || subject == null || text == null) {
            throw new IllegalArgumentException("Email details cannot be null");
        }

        String lineEditing = "\r\n";

        // Odeslání příkazu EHLO
        writeCommand("EHLO localhost" + lineEditing);
        readResponse();

        // Odeslání příkazu MAIL FROM
        writeCommand("MAIL FROM:<" + from + ">" + lineEditing);
        readResponse();

        // Odeslání příkazu RCPT TO
        writeCommand("RCPT TO:<" + to + ">" + lineEditing);
        readResponse();

        // Odeslání příkazu DATA
        writeCommand("DATA" + lineEditing);
        readResponse();

        // Odeslání obsahu emailu
        writeCommand("Subject: " + subject + lineEditing);
        writeCommand("From: " + from + lineEditing);
        writeCommand("To: " + to + lineEditing);
        writeCommand(lineEditing); 

        // Tělo zprávy
        writeCommand(text + lineEditing);
        
        // Ukončení těla zprávy
        writeCommand("." + lineEditing);
        readResponse();
    }

    public void close() throws IOException {
        // Odeslání příkazu QUIT
        writeCommand("QUIT\r\n");
        readResponse();

        // Zavření streamů a socketu
        output.close();
        input.close();
        socket.close();
    }

    // Pomocná metoda pro zápis příkazů na server
    private void writeCommand(String command) throws IOException {
        output.write(command.getBytes());
        output.flush();
    }

    // Pomocná metoda pro čtení odpovědi ze serveru
    private void readResponse() throws IOException {
        String response = input.readLine();
        if (response.startsWith("4") || response.startsWith("5")) {
            throw new IOException("SMTP error: " + response);
        }
        System.out.println("Response: " + response); // Volitelně, pro ladění
    }
}
