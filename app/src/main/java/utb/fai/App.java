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

        // Tvůj blok kódu s pevnými hodnotami
        try {
            EmailSender sender = new EmailSender("smtp.utb.cz", 25);
            sender.send("you@utb.cz", "you@utb.cz", "Email from Java", "Funguje to?\nSnad...");
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kód s použitím argumentů z příkazové řádky
        try {
            EmailSender sender = new EmailSender(smtpServer, port);
            sender.send(from, to, subject, content);
            sender.close();  // Zavření spojení
            System.out.println("Email byl úspěšně odeslán.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
