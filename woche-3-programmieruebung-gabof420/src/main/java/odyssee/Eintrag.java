package odyssee;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.TimeZone;

public final class Eintrag {
  private final LocalDateTime time;
  private final int answertime;

  public Eintrag(LocalDateTime time, int answertime) {
    this.time = time;
    this.answertime = answertime;
  }

  public Eintrag(String time, String answertime) {
    this(parseTime(time), Integer.parseInt(answertime));
  }

  public int getStunde() {
    return time.getHour();
  }

  public String getWochentag() {
    return time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMANY);
  }


  private static LocalDateTime parseTime(String time) {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(time)), TimeZone
        .getDefault().toZoneId());
  }

  public int getKalenderWoche() {
    return time.get(WeekFields.ISO.weekOfYear());
  }

  public LocalDateTime getZeitstempel() {
    return time;
  }

  public int getAntwortzeit() {
    return answertime;
  }

  

  @Override
  public String toString() {
    return "Eintrag[" +
        "time=" + time + ", " +
        "answertime=" + answertime + ']';
  }

}
