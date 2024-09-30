package utb.fai;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing

        String host = "";
        int port = 0;
        String from = "";
        String to = "";
        String subject = "";
        String message = "";

        if(args.length != 6) {
            System.out.println("Usage: java -jar email-sender.jar <host> <port> <from> <to> <subject> <message>");
            System.exit(1);
        }

        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
            from = args[2];
            to = args[3];
            subject = args[4];
            message = args[5];

        } catch (Exception e) {
            System.out.println("Usage: java -jar email-sender.jar <host> <port> <from> <to> <subject> <message>");
            System.exit(1);

        }

        
        try {
            EmailSender sender = new EmailSender(host, port);
            sender.send(from, to, subject, message);
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
