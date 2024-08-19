package odyssee.collect;

import java.util.ArrayList;
import java.util.List;
import odyssee.Eintrag;

class EintragSammler {

  final List<Eintrag> eintraege = new ArrayList<>();
  private String tempZeitstempel;

  public void reduce(String s) {
    if (tempZeitstempel == null) {
      // FIXME: erste Zeile eines Eintrags aus der Textdatei verarbeiten
      tempZeitstempel = s;
    } else {
      // FIXME: zweite Zeile eines Eintrags aus der Textdatei verarbeiten
      eintraege.add(new Eintrag(tempZeitstempel, s));
      tempZeitstempel = null;
    }
  }

  public void join(EintragSammler that) {
    // FIXME:  Zwei EintragSammler-Objekte kombinieren
    this.eintraege.addAll(that.eintraege);
  }


}




