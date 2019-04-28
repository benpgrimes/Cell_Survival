/** A JUnit Test class for testing the Food class.
  * 
  * @author David Tooley, Zifeng Qi
  */
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;

public class FoodTest
{
  private Food foodTest;
  private Food foodTest2;
  private Food foodTest3;
  @Before
  public void setUp()
  {
    foodTest = new Food(410,500,20,500);
    foodTest2 = new Food(10,40,33,600);
    foodTest3 = new Food(-2,-5,20,300);
  }
  
  @Test
  public void testGetX() 
  {
    assertEquals(410,(int)foodTest.getX());
    assertEquals(10,(int)foodTest2.getX());
    assertEquals(-2,(int)foodTest3.getX());
  }
 
  @Test
  public void setX() 
  {
   foodTest.setX(0);
   assertNotEquals(410,(int)foodTest.getX());
  }
  @Test
  public void getY() 
  {
   assertEquals(40,(int)foodTest2.getY());
   assertEquals(-5,(int)foodTest3.getY());
  }
 
  @Test
  public void testSetY() 
  {
    foodTest.setY(600);
    assertEquals(600,(int)foodTest.getY());
    foodTest.setY(0);
    assertNotEquals(600,(int)foodTest.getY());
  }
  
  @Test
  public void testGetEnergy() { 
    assertEquals(500,foodTest.getEnergy());
    assertEquals(600,(int)foodTest2.getEnergy());
    assertEquals(300,(int)foodTest3.getEnergy());
 }
 
  @After
   public void tearDown() throws Exception {
    System.out.println("Test done.");
   }
}
  
