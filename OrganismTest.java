/** A JUnit Test class for testing the Organism class.
  * 
  * @author David Tooley
  */
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;

public class OrganismTest
{
  private Organism organismTest; 
  private Food foodTest;
  
  @Before
  public void setUp()
  {
    int tendancies[] = new int[15];
    for(int j = 0; j < 15; j++)
    {
      tendancies[j] = 10+j;
    }
    
    organismTest = new Organism(400,500,30.0,100,Color.BLUE,tendancies);
    foodTest = new Food(410,500,20,500);
  }
  
  @Test
  public void testGetX() 
  {
    assertEquals(400,(int)organismTest.getX());
  }
  
  @Test
  public void testGetY() 
  {
    assertEquals(500,(int)organismTest.getY());
  }
  
  @Test
  public void testGetColor() 
  {
    assertEquals(Color.BLUE,organismTest.getColor());
  }
  
  @Test
  public void testGetGrowthLimit() {
    assertEquals(10,organismTest.getGrowthLimit());
 }

  
  @Test
  public void testSetMaternalInclination() {
    organismTest.setMaternalInclination(60);
    assertEquals(60,organismTest.getMaternalInclination());
 }

  @Test
  public void testCollision() {
    
    assertTrue(organismTest.collision(foodTest.getX(),foodTest.getY(),foodTest.getEnergy()));
 }
  
  @Test
  public void testReduceEnergy() { 
    organismTest.reduceEnergy(10);
    assertEquals(90,organismTest.getEnergy());
 }  
}
