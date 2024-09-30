package utb.fai;

import java.io.*;
import java.net.*;



public class EmailSender {
    private Socket socket;
    private PrintWriter out;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("HELO pc");
    }



    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        out.println("MAIL FROM: <" + from + ">");
        out.println("RCPT TO: <" + to + ">");
        out.println("DATA");
        out.println("Subject: " + subject + "\r\n\r\n" + text + "\r\n.\r\n");
    }


    /*
     * Sends QUIT and closes the socket
     */
    public void close() throws IOException {
        try{
            out.println("QUIT");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
