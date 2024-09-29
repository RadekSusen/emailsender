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

        readResponse(); 
    }

    public void send(String from, String to, String subject, String text) throws IOException {
        if (from == null || to == null || subject == null || text == null) {
            throw new IllegalArgumentException("Emailové informace nesmí zůstat prázdné");
        }

        // Odeslání příkazu EHLO
        writeCommand("EHLO localhost" + "\r\n");
        readResponse();

        // Odeslání příkazu MAIL FROM
        writeCommand("MAIL FROM:<" + from + ">" + "\r\n");
        readResponse();

        // Odeslání příkazu RCPT TO
        writeCommand("RCPT TO:<" + to + ">" + "\r\n");
        readResponse();

        // Odeslání příkazu DATA
        writeCommand("DATA" + "\r\n");
        readResponse();

        // Odeslání obsahu emailu
        writeCommand("Subject: " + subject + "\r\n");
        writeCommand("From: " + from + "\r\n");
        writeCommand("To: " + to + "\r\n");
        writeCommand("\r\n"); 

        // Tělo zprávy
        writeCommand(text + "\r\n");
        
        // Ukončení těla zprávy
        writeCommand("." + "\r\n");
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
