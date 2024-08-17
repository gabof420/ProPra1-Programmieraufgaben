import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math; 

public class Ausgabenbuch {

    public static void main(String[] args) {
        
        Scanner stdin = new Scanner(System.in);
        System.out.println("Ausgabenbuch ist bereit für die Eingaben");
        
        //Map zum Speichern der shops und Waren
        Map<String, Double> categories = new HashMap<>();
        Map<String, Double> shops = new HashMap<>();

        //Scanner
        while(stdin.hasNext()) {
            String eingabe = stdin.nextLine();
            String[] splitted = eingabe.split(" ");

            String geschaeft;
            String ware;
            double preis;
            
            // Wenn der Benutzer "add" eingibt, wird die Eingabe in die Liste hinzugefügt
            if(splitted[0].equals("add")) {
                geschaeft = splitted[1];
                ware = splitted[2];
                preis = Double.parseDouble(splitted[3]);

                addToList(geschaeft, ware, preis, categories, shops);

            }

            //Wenn der Nutzer "report" eingibt, wird die Liste abhängig von splitted[1] ausgegeben

            if(splitted[0].equals("report")) {

                try {
                    if(splitted[1].equals("category")) {
                        for(Map.Entry<String, Double> entry : categories.entrySet()) {
                            System.out.println(entry.getKey() + ": " + Math.round(entry.getValue() * 100.0) / 100.0 + "€");
                        
                    }
                    
                }   else if(splitted[1].equals("shop")) {
                        for(Map.Entry<String, Double> entry : shops.entrySet()) {
                            System.out.println(entry.getKey() + ": " + Math.round(entry.getValue() * 100.0) / 100.0 + "€");
                        }

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("category oder shop fehlt");
            }
            
        }
            
            // Wenn der Benutzer "exit" eingibt, wird das Programm beendet
            if(eingabe.equals("exit")) {
                System.out.println("Bye.");
                break;
            }


            }
    }

    private static void addToList(String geschaeft, String ware, double preis, Map<String, Double> categories, Map<String, Double> shops) {
        categories.put(ware, categories.getOrDefault(ware, 0.0) + preis);
        shops.put(geschaeft, shops.getOrDefault(geschaeft, 0.0) + preis);

        System.out.println("Eingabe hinzugefügt zum Shop " + geschaeft + " in der Kategorie " + ware + ": " + preis + "€");

    }
}