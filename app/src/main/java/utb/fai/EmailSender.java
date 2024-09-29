package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Odeslání HELO příkazu a čekání na odpověď
        out.println("HELO pc");
        readResponse();
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws InterruptedException, IOException {
        out.println("MAIL FROM:<" + from + ">");
        readResponse();

        out.println("RCPT TO:<" + to + ">");
        readResponse();

        out.println("DATA");
        readResponse();

        out.println("Subject: " + subject + "\r\n\r\n" + text + "\r\n.");
        readResponse();
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            out.println("QUIT");
            readResponse();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Helper method to read server response and print it
     */
    private void readResponse() throws IOException {
        String response = in.readLine();
        System.out.println("Server: " + response);
    }
}
