package odyssee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import odyssee.Eintrag;
import odyssee.collect.EintragCollector;

public class Main {
    public static void main(String[] args) {
        try {
            // Pfad zur Datei festlegen
            Path filePath = Path.of("odyssey.txt");
            
            // Zeilen aus der Datei einlesen und in eine Liste von Eintrag-Objekten umwandeln
            List<Eintrag> eintraege = Files.lines(filePath)
                                           .collect(new EintragCollector());

            // Ausgabe der eingelesenen Einträge
            eintraege.forEach(System.out::println);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}