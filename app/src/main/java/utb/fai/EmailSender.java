package utb.fai;

import java.io.*;
import java.net.Socket;

public class EmailSender {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private byte[] response = new byte[1024];
    private int len;

    /*
     * Konstruktor otevře spojení se zadaným SMTP serverem na daném portu.
     */
    public EmailSender(String host, int port) throws IOException {
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();

        // Přečtení uvítací zprávy serveru
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }

    /*
     * Metoda send() odesílá emailovou zprávu z určeného emailu na zadaný email
     * příjemce s daným předmětem a textem.
     */
    public void send(String from, String to, String subject, String text) throws IOException, InterruptedException {
        String message;
        byte[] buffer;

        // Odeslání EHLO příkazu
        message = "EHLO " + from + "\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Čeká na odpověď
        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        // Odeslání MAIL FROM příkazu
        message = "MAIL FROM:<" + from + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Čeká na odpověď
        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        // Odeslání RCPT TO příkazu
        message = "RCPT TO:<" + to + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Čeká na odpověď
        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        // Odeslání DATA příkazu
        message = "DATA\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Čeká na odpověď
        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        // Odeslání těla emailu (předmět a obsah)
        message = "Subject: " + subject + "\r\n\r\n" + text + "\r\n.\r\n";
        buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Čeká na odpověď
        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }

    /*
     * Metoda close() odesílá příkaz QUIT na SMTP server a zavírá spojení.
     */
    public void close() throws IOException {
        // Odeslání QUIT příkazu
        String message = "QUIT\r\n";
        byte[] buffer = message.getBytes();
        output.write(buffer);
        output.flush();

        // Zavře socket a streamy
        input.close();
        output.close();
        socket.close();
    }
}
