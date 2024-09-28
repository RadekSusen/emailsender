package utb.fai;

import java.net.*;
import java.io.;

public class EmailSender {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private byte[] response = new byte[1024];
    private int len;

    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();

        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }
    public void send(String from, String to, String subject, String text) throws IOException {
        String message;
        byte[] buffer;

        message = "EHLO " + from + "\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        message = "MAIL FROM:<" + from + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        message = "RCPT TO:<" + to + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        message = "DATA\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        message = "Předmět: " + subject + "\r\n\r\n" + text + "\r\n.\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }

    public void close() throws IOException {
        String message = "QUIT\r\n";
        byte[] buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        input.close();
        output.close();
        socket.close();
    }
}
