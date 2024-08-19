package odyssee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import odyssee.collect.EintragCollector;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Odyssee {


  public static void main(String[] args) {

    Odyssee odyssee = new Odyssee();

    /* Erster Aufgabenteil - Daten sind schon vorhanden
      Es sollten 115 Datensätze sein
     */

    List<Eintrag> eintraege = SampleData.DATA;

    /* Zweiter Aufgabenteil - Einlesen der Daten aus einer Datei
     Wenn EintragCollector und EintragSammeler korrekt implementiert wurden,
     Sollten 15141 Datensätze in der Liste sein.*/

    // FIXME: Kommentar entfernen für den zweiten Aufgabenteil
    // List<Eintrag> eintraege = odyssee.readFromInputfile("odyssey.txt");

    System.out.println("Anzahl der Einträge: " + eintraege.size());

    // Hier sollen die einzelnen Analysen durchgeführt werden

    // FIXME Berechnen Sie die minimale Antwortzeit
    int minAntwortzeit = computeMinAntwortzeit(eintraege);
    // Für die Sampledaten: 2064 ms

    System.out.printf("Minimale Antwortzeit: %d ms%n", minAntwortzeit);

    // FIXME Berechnen Sie die maximale Antwortzeit
    int maxAntwortzeit = computeMaxAntwortzeit(eintraege);
    // Sampledaten: 15094 ms
    System.out.printf("Maximale Antwortzeit: %d ms%n", maxAntwortzeit);

    // FIXME Berechnen Sie die mittlere Antwortzeit
    double avgAntwortzeit = computeMittlereAntwortzeit(eintraege);
    // Sampledaten: 2881,48 ms
    System.out.printf("Mittlere Antwortzeit %.02f ms%n", avgAntwortzeit);

    // FIXME Berechnen Sie ein Histogramm der Antwortzeiten in 100ms Blöcken
    Map<Integer, Long> histogramm = histogramm(eintraege);
    // Sampledaten: Map sollte 2500 -> 13 enthalten
    System.out.printf("Histogramm: %s %n", histogramm.toString());

    // FIXME Berechnen Sie die mittlere Antwortzeit pro Wochentag
    Map<String, Double> wochentagAntwortzeiten = avgAntwortzeitWochentag(eintraege);
    System.out.println("Mittlere Antwortzeit nach Wochentag");
    // Sampledaten: Map sollte Mittwoch -> 2471,57 enthalten
    wochentagAntwortzeiten.forEach((k, v) -> System.out.printf("%s -> %.02f%n", k, v));


    // FIXME Berechnen Sie die mittlere Antwortzeit pro Stunde
    Map<Integer, Double> stundeAntwortzeiten = avgAntwortzeitStunde(eintraege);
    System.out.println("Mittlere Antwortzeit nach Stunde");
    // Sampledaten: Map sollte 9 -> 2290,00 enthalten
    stundeAntwortzeiten.forEach((k, v) -> System.out.printf("%d -> %.02f%n", k, v));

    // FIXME Berechnen Sie die mittlere Antwortzeit pro Kalenderwoche
    //  für einen festen Wochentag und eine feste Stunde

    // Tipp: Es gibt unten eine Methode, die für einen Zeitstempel die Kalenderwoche umwandelt
    Map<Integer, Double> verlauf = verlaufKalender();
    System.out.println("Mittlere Antwortzeit in der zeitlichen Änderung:");
    // Sampledaten: Relativ uninteressant, da die Sampledaten zeitlich zu nah beieinander sind.
    // für Mittwoch, 22 Uhr bekommen Sie aber zumindest einen Wert: 20 -> 2577,00
    verlauf.forEach((k, v) -> System.out.printf("%s -> %.02f%n", k, v));

    // FIXME Berechnen Sie die Verfügbarkeit des Systems
    long online = verfügbarkeit(eintraege);
    long offline = eintraege.size() - online;
    // Sampledaten: 98,26%
    System.out.printf("Verfügbarkeit: %.02f%%%n", (100.0 * online) / (online + offline));

  }


  // Ab hier kann alles so bleiben

  private int getKalenderWoche(LocalDateTime time) {
    return time.get(WeekFields.ISO.weekOfYear());
  }

  
  private List<Eintrag> readFromInputfile(String name) {
    Path inputfile = Path.of(name);
    try (Stream<String> lines = Files.lines(inputfile)) {
      // Parallele Ausführung ist nicht möglich
      return lines.sequential().collect(new EintragCollector());
    } catch (IOException e) {
      throw new RuntimeException("Could not read file: " + name);
    }
  }

  private static long verfügbarkeit(List<Eintrag> daten) {
    return daten.stream()
        .filter(zeit -> zeit.getAntwortzeit() < 10000)
        .count();
  }

  private static Map<Integer, Double> verlaufKalender() {
    return SampleData.DATA.stream()
        .collect(Collectors.groupingBy(zeit -> zeit.getKalenderWoche(),
        Collectors.averagingDouble(Eintrag::getAntwortzeit)));
  }

  
  private static int computeMinAntwortzeit(List<Eintrag> daten) {
    return daten.stream()
        .mapToInt(Eintrag::getAntwortzeit)
        .min()
        .orElse(0);     
  }

  private static int computeMaxAntwortzeit(List<Eintrag> daten) {
    return daten.stream()
        .mapToInt(Eintrag::getAntwortzeit)
        .max()
        .orElse(0);
  }

  private static Double computeMittlereAntwortzeit(List<Eintrag> daten) {
    return daten.stream()
        .mapToDouble(Eintrag::getAntwortzeit)
        .average()
        .orElse(0);
  }

  private static Map<Integer, Long> histogramm(List<Eintrag> daten) {
    return daten.stream()
        .collect(Collectors.groupingBy(zeit -> (zeit.getAntwortzeit() / 100) * 100, 
        Collectors.counting()));
  }

  private static Map<String, Double> avgAntwortzeitWochentag(List<Eintrag> daten) {
    return daten.stream()
        .collect(Collectors.groupingBy(Eintrag::getWochentag, 
        Collectors.averagingDouble(Eintrag::getAntwortzeit)));  
  }

  private static Map<Integer, Double> avgAntwortzeitStunde(List<Eintrag> daten) {
    return daten.stream()
        .collect(Collectors.groupingBy(stunde -> stunde.getStunde(),
        Collectors.averagingDouble(zeit -> zeit.getAntwortzeit())));
  }

}
