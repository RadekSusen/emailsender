package utb.fai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing

        String smtpServer = args[0];
        int smtpPort = Integer.parseInt(args[1]);
        String senderEmail = args[2];
        String recipientEmail = args[3];
        String emailSubject = args[4];
        String emailMessage = args[5];

        try {
            EmailSender sender = new EmailSender(smtpServer, smtpPort);
            sender.send(senderEmail, recipientEmail, emailSubject, emailMessage);
            sender.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
