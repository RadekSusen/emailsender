package utb.fai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing
        System.out.println("Sending email...");
        try {
            EmailSender sender = new EmailSender("smtp.utb.cz", 25);
            sender.send("r_susen@utb.cz", "r_susen@utb.cz", "Email from Java", "Funguje to?\nSnad...");
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
