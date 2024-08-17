package bloomfilter.tests;

import static org.assertj.core.api.Assertions.assertThat;

import bloomfilter.Bloomfilter;

/*
Um nicht zu viel über die Generics zu verraten, werden hier Rawtypes verwendet.
Nachdem die Klasse Bloomfilter generisch gemacht wurde, müssen die Tests angepasst
und das @SuppressWarnings entfernt werden.
*/

// FIXME: Suppress Warnings entfernen
// @SuppressWarnings({"unchecked", "rawtypes"})
public class TestsStart {

  public static void main(String[] args) {
    System.out.print("Test 1 ... ");
    storeAndRetrieve();
    System.out.print("ok\nTest 2 ... ");
    emptyFilter();
    System.out.print("ok\nTest 3 ... ");
    collisionFree();
    System.out.print("ok\nTest 4 ... ");
    // Hier wird überspezifiziert.
    // Diesen Testfall würden wir vermutlich gar nicht schreiben.
    withCollision();
    System.out.println("ok");
  }


  // In einem Bloomfilter kann ein Wert gespeichert und wiedergefunden werden
  static void storeAndRetrieve() {
    // FIXME Generics verwenden
    Bloomfilter filter = new Bloomfilter(3, 3);
    filter.add(MyString.of("foo"));
    boolean b = filter.contains(MyString.of("foo"));
    // FIXME: Es muss die passende Bibliothek eingebunden werden, damit die Klasse kompiliert
    assertThat(b).isTrue();
  }

  // In einem leeren Filter kann kein Wert wiedergefunden werden
  static void emptyFilter() {
    Bloomfilter filter = new Bloomfilter(3, 3);
    boolean b = filter.contains(MyString.of("foo"));
    assertThat(b).isFalse();
  }


  // In einem Bloomfilter mit 65536 Bit und drei Hashfunktionen
  // kollidieren 'foo' und 'item517555' nicht. Wenn nur 'foo' gespeichert wird,
  // kann 'item517555' nicht gefunden werden
  static void collisionFree() {
    Bloomfilter filter = new Bloomfilter(2, 3);
    filter.add(MyString.of("foo"));
    boolean b = filter.contains(MyString.of("item517555"));
    assertThat(b).isFalse();
  }

  // In einem Bloomfilter mit 256 Bit und drei Hashfunktionen
  // kollidieren 'foo' und 'item517555' aber. Wenn nur 'foo' gespeichert wird,
  // kann 'item517555' fälschlicherweise gefunden werden
  static void withCollision() {
    Bloomfilter filter = new Bloomfilter(1, 3);
    filter.add(MyString.of("foo"));
    boolean b = filter.contains(MyString.of("item517555"));
    assertThat(b).isTrue();
  }

  // FIXME: Diese Methode darf nicht mehr compilieren, wenn die Generics eingeführt wurden
  // Kann dann gelöscht werden
  static void shouldNotCompile() {
    Bloomfilter filter = new Bloomfilter(3, 3);
    filter.add("foo");
    assertThat(filter.contains(42)).isFalse();
  }


}
