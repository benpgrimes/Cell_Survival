/** A JUnit Test class for testing the Food class.
  * 
  * @author David Tooley
  */
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;

public class FoodTest
{
  private Food foodTest;
  
  @Before
  public void setUp()
  {
    foodTest = new Food(410,500,20,500);
  }
  
  @Test
  public void testGetX() 
  {
    assertEquals(410,(int)foodTest.getX());
  }
  
  @Test
  public void testSetY() 
  {
    foodTest.setY(600);
    assertEquals(600,(int)foodTest.getY());
  }
  
  @Test
  public void testGetEnergy() { 
    assertEquals(500,foodTest.getEnergy());
 }
}
