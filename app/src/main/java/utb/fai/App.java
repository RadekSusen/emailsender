package utb.fai;

public class App {

    public static void main(String[] args) {

        // Ověření, zda bylo předáno všech 6 potřebných parametrů
        if (args.length != 6) {
            System.out.println("Chyba: Nebyly zadány všechny parametry.");
            return;
        }

        try {
            // Načtení parametrů z pole args
            String serverAdress = args[0];       // Adresa SMTP serveru
            int port = Integer.parseInt(args[1]); // Číslo portu SMTP serveru
            String senderEmail = args[2];        // Email odesílatele
            String recipientEmail = args[3];          // Email příjemce
            String subject = args[4];          // Předmět emailu
            String messageText = args[5];          // Obsah emailu

            // Vytvoření instance EmailSender a odeslání emailu
            EmailSender sender = new EmailSender(serverAdress, port);
            sender.send(senderEmail, recipientEmail, subject, messageText);
            sender.close();

            System.out.println("Email byl úspěšně odeslán.");


        } catch (Exception e) {
            // Ošetření výjimek
            System.out.println("Nastala chyba při odesílání emailu.");
            e.printStackTrace();
        }


    }
}
