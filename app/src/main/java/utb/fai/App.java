package utb.fai;

public class App {

    public static void main(String[] args) {
        // Ověření, zda byly zadány správné parametry
        if (args.length < 4) {
            System.out.println("Použití: java App <server> <port> <odEmail> <doEmail> <předmět> <zpráva>");
            return;
        }

        String server = args[0];
        int port = Integer.parseInt(args[1]);
        String fromEmail = args[2];
        String toEmail = args[3];
        String subject = args.length > 4 ? args[4] : "Bez předmětu";
        String messageBody = args.length > 5 ? args[5] : "Žádná zpráva.";

        // Vytvoření instance EmailSender a odeslání e-mailu
        try {
            EmailSender sender = new EmailSender(server, port);
            sender.send(fromEmail, toEmail, subject, messageBody);
            System.out.println("Email dosel");
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
