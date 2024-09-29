package utb.fai;

public class App {
    public static void main(String[] args) {
        // Ověření počtu argumentů
        if (args.length != 6) {
            System.out.println("Email nelze odeslat.");
            return;
        }

        // Získání parametrů z args
        String smtpServer = args[0];  // Adresa SMTP serveru
        int port = Integer.parseInt(args[1]);  // Port serveru
        String from = args[2];  // Email odesílatele
        String to = args[3];    // Email příjemce
        String subject = args[4];  // Předmět emailu
        String content = args[5];  // Obsah emailu

        // Kód s použitím argumentů z příkazové řádky
        try {
            EmailSender sender = new EmailSender(smtpServer, port);
            sender.send(from, to, subject, content);
            System.out.println("Email byl úspěšně odeslán.");
            sender.close();  // Zavření spojení
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
