package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        
        socket = new Socket(host, port);
        
        // Inicializace BufferedReader a BufferedWriter pro komunikaci přes soket
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        // Přečtení odpovědi serveru
        readResponse();

    }

    /*
     * Sends email from an email address to an email address with some subject and text.
     * If the Socket throws an exception during sending, the exception is not handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        
        sendCommand("HELO localhost");
        sendCommand("MAIL FROM:<" + from + ">"); // Odesílatel
        sendCommand("RCPT TO:<" + to + ">"); // Příjemce
        sendCommand("DATA");
        
        writer.write("Subject: " + subject + "\r\n"); // Předmět
        writer.write("\r\n"); 
        writer.write(text + "\r\n"); // Text
        writer.write(".\r\n");
        writer.flush();

        readResponse();
        
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() throws IOException {
        sendCommand("QUIT");
        socket.close();

    }

     // Pomocná metoda pro odesílání příkazů serveru
     private void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush(); // Odeslání dat serveru
        readResponse(); // Přečtení odpovědi serveru
    }

    // Pomocná metoda pro čtení odpovědí serveru
    private void readResponse() throws IOException {
        String response = reader.readLine();
        System.out.println("SERVER: " + response); // Vypsání odpovědi serveru
    }
}
