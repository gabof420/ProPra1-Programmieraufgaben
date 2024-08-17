package bloomfilter;

import java.util.BitSet;
import org.apache.commons.codec.digest.DigestUtils;

// FIXME: Der Filter soll generisch sein
public class Bloomfilter<T extends Serialisierbar> {

  private final int nrAddressBytes;
  private final int nrHashFunktionen;

  private final BitSet filter;

  public Bloomfilter(int nrAddressBytes, int nrHashFunktionen) {
    if (nrAddressBytes * nrHashFunktionen > 20) {
      throw new IllegalArgumentException("Filter zu groß");
    }
    if (nrAddressBytes > 3) {
      throw new IllegalArgumentException();
    }
    this.nrAddressBytes = nrAddressBytes;
    this.nrHashFunktionen = nrHashFunktionen;
    int adrBits = 8 * nrAddressBytes;
    filter = new BitSet((int) Math.pow(2, adrBits));
  }

  // FIXME: Der Parametertyp muss angepasst werden
  public void add(T elem) {
    byte[] bytes = serialisieren(elem);
    for (int i = 0; i < nrHashFunktionen; i++) {
      filter.set(bitAdresse(i, bytes));
    }
  }

  // FIXME: Der Parametertyp muss angepasst werden
  private <T extends Serialisierbar> byte[] serialisieren(T elem) {
    // FIXME: Der Cast muss entfernt werden
    // FIXME: Der Code kompiliert erst, wenn eine passende Bibliothek eingebunden ist
    return DigestUtils.sha1(elem.serialisieren());
  }

  // FIXME: Der Parametertyp muss angepasst werden
  public boolean contains(T elem) {
    byte[] bytes = serialisieren(elem);
    for (int i = 0; i < nrHashFunktionen; i++) {
      if (!filter.get(bitAdresse(i, bytes))) {
        return false;
      }
    }
    return true;
  }

  int bitAdresse(int bitIndex, byte[] hashBytes) {
    int adr = 0;
    int offset = bitIndex * nrAddressBytes;
    for (int i = 0; i < nrAddressBytes; i++) {
      int bte = hashBytes[offset + i] & 0xFF;
      adr += bte << (8 * i);
    }
    return adr;
  }


}
