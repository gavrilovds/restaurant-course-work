package ru.gavrilovds.restaurant;


import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthContollerTest extends AbstractComponentTest {

  @Test
  public void test() {
    Assertions.assertEquals(true, true);
  }

  @Test
  public void test6() {
    Assertions.assertEquals(true, true);
  }

  @Test
  public void test2() throws InterruptedException {
    Thread.sleep(new Random().nextInt(2, 3));
    Assertions.assertEquals(true, true);
  }

  @Test
  public void test3() {
    Assertions.assertEquals(true, true);
  }

  @Test
  public void test4() {
    Assertions.assertEquals(true, true);
  }

  @Test
  public void test5() {
    Assertions.assertEquals(true, true);
  }
}
