import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

    public class Verwaltung {
        private final Scanner stdin = new Scanner(System.in);

        private final Map<Integer, List<Double>> studiNoten = new HashMap<>();
      
        /**
         * Gibt aus, wie viele Studis gespeichert sind, und alle Studis mit ihrer Durchschnittsnote sortiert nach Matrikelnummer.
         */
        private void reportAusgeben() {
          System.out.println(studiNoten.size() + " Studis gespeichert:");
          SortedSet<Integer> matrikelnummern = new TreeSet<>(studiNoten.keySet());
          matrikelnummern.forEach(nummer -> studiAusgeben(nummer, studiNoten.get(nummer)));
        }
        
        private void studiAusgeben(int matrikelnummer, List<Double> noten) {
          double schnitt = noten.stream().mapToDouble(Double::doubleValue).average().orElse(0);
          System.out.println(matrikelnummer + ": " + schnitt);
        }
      
        /**
         * Fügt einen neuen Eintrag hinzu.
         * @param parts parts[0]: Matrikelnummer, parts[1]: Note
         */
        private void add(String[] parts) {
          try {
            int matrikelnummer = Integer.parseInt(parts[1]);
            double note = Double.parseDouble(parts[2]);
            speichern(matrikelnummer, note);
          } catch (NumberFormatException nfe) {
            System.out.println("Ungültiges Zahlenformat.");
          }
        }
      
        private void speichern(int matrikelnummer, double note) {
          List<Double> bisherigeNoten = studiNoten.getOrDefault(matrikelnummer, new LinkedList<Double>());
          bisherigeNoten.add(note);
          studiNoten.put(matrikelnummer, bisherigeNoten);
        }

        public void run() {
          while(stdin.hasNext()) {
            readAndProcess();
          }
        }
      
        private void readAndProcess() {
          String line = stdin.nextLine();
          String[] parts = line.split(" ");
          switch (parts[0]) {
            case "add":
              add(parts);
              break;
            case "report":
              reportAusgeben();
              break;
            default:
              System.out.println("ungültiger Befehl: " + parts[0]);
          }
        }
  public static void main(String[] args) {
    Verwaltung verwaltung = new Verwaltung();
    verwaltung.run();
  }
}
