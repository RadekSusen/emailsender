package utb.fai;

import java.io.*;
import java.net.*;

public class EmailSender {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public EmailSender(String host, int port) throws IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        waitForResponse(); // Čekáme na první odpověď serveru
    }

    public void send(String from, String to, String subject, String messageBody) throws IOException {
        if (from == null || to == null || subject == null || messageBody == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Odesíláme jednotlivé příkazy SMTP
        sendCommand("EHLO localhost");
        sendCommand("MAIL FROM:<" + from + ">");
        sendCommand("RCPT TO:<" + to + ">");
        sendCommand("DATA");
        writer.println("Subject: " + subject);
        writer.println(messageBody);
        writer.println(".");
        writer.flush();
        waitForResponse();
    }

    private void sendCommand(String command) throws IOException {
        writer.println(command);
        writer.flush();
        waitForResponse();
    }

    private void waitForResponse() throws IOException {
        String response = reader.readLine();
        if (response == null || !(response.startsWith("2") || response.startsWith("3"))) {
            throw new IOException("Server returned an error: " + response);
        }
    }

    public void close() throws IOException {
        sendCommand("QUIT");
        socket.close();
        reader.close();
    }
}
