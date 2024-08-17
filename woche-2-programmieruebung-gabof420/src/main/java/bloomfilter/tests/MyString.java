package bloomfilter.tests;

import bloomfilter.Serialisierbar;

public class MyString implements Serialisierbar {
  private final String content;

  public static MyString of(String content) {
    return new MyString(content);
  }

  private MyString(String content) {
    this.content = content;
  }

  @Override
  public byte[] serialisieren() {
    return content.getBytes();
  }
}
