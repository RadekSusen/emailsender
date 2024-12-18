package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
	Socket s;
	OutputStream out;
	
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        s = new Socket(host, port);
		out = s.getOutputStream();
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String recipient, String subject, String text) throws IOException {
                out.write((from+recipient+subject+text).getBytes());
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close()  throws IOException{
        out.write("QUIT\r\n".getBytes());
		
		InputStream inp = s.getInputStream();
		int znak;
		while((znak=inp.read())!=-1)
			System.out.write(znak);
		s.close();
		
    }
}
