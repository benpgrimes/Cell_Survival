/** A JUnit Test class for testing the Organism class.
  * 
  * @author David Tooley, Zifeng Qi
  */
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;

public class OrganismTest
{
  private Organism organismTest; 
  private Food foodTest;
  private int tendancies[] = new int[15];

  
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
  public void idle() {
   organismTest.idle();
   assertEquals(organismTest.energy, 99);
  }

  @Test
  public void reduceEnergy() {
   organismTest.reduceEnergy(10);
   assertEquals(organismTest.energy, 90);
   organismTest.reduceEnergy(100);
   assertEquals(organismTest.energy,0);
  } 
 
 @Test
    public void getGrowthLimit() {
        assertEquals(10, organismTest.getGrowthLimit());

    }

    @Test
    public void setGrowthLimit() {
        organismTest.setGrowthLimit(50);
        assertNotEquals(10, organismTest.getGrowthLimit());
        organismTest.setGrowthLimit(100);
        assertEquals(100, organismTest.getGrowthLimit());
        organismTest.setGrowthLimit(-10);
        assertNotEquals(-10, organismTest.getGrowthLimit());
    }

    @Test
    public void getGrowthInclination() {
        assertEquals(10, organismTest.getGrowthLimit());

    }

    @Test
    public void setGrowthInclination() {
        organismTest.setGrowthInclination(50);
        assertNotEquals(10, organismTest.getGrowthInclination());
        organismTest.setGrowthInclination(100);
        assertEquals(100, organismTest.getGrowthInclination());
        organismTest.setGrowthInclination(-10);
        assertNotEquals(-10, organismTest.getGrowthInclination());

    }

    @Test
    public void getCuriosity() {
        assertEquals(12, organismTest.getCuriosity());

    }

    @Test
    public void setCuriosity() {
        organismTest.setCuriosity(50);
        assertNotEquals(12, organismTest.getCuriosity());
        organismTest.setCuriosity(100);
        assertEquals(100, organismTest.getCuriosity());
        organismTest.setCuriosity(-10);
        assertNotEquals(-10, organismTest.getCuriosity());
    }

    @Test
    public void getMaternalMin() {
        assertEquals(13, organismTest.getMaternalMin());

    }

    @Test
    public void setMaternalMin() {
        organismTest.setMaternalMin(50);
        assertNotEquals(13, organismTest.getMaternalMin());
        organismTest.setMaternalMin(100);
        assertEquals(100, organismTest.getMaternalMin());
        organismTest.setMaternalMin(-10);
        assertNotEquals(-10, organismTest.getMaternalMin());
    }

    @Test
    public void getMaternalInclination() {
        assertEquals(14, organismTest.getMaternalInclination());

    }

    @Test
    public void setMaternalInclination() {
        organismTest.setMaternalInclination(50);
        assertNotEquals(14, organismTest.getMaternalInclination());
        organismTest.setMaternalInclination(100);
        assertEquals(100, organismTest.getMaternalInclination());
        organismTest.setMaternalInclination(-10);
        assertNotEquals(-10, organismTest.getMaternalInclination());
    }

    @Test
    public void getActive() {
        assertEquals(15, organismTest.getActive());

    }

    @Test
    public void setActive() {
        organismTest.setActive(50);
        assertNotEquals(15, organismTest.getActive());
        organismTest.setActive(100);
        assertEquals(100, organismTest.getActive());
        organismTest.setActive(-10);
        assertNotEquals(-10, organismTest.getActive());
    }

    @Test
    public void getTemperament() {
        assertEquals(16, organismTest.getTemperament());

    }

    @Test
    public void setTemperament() {
        organismTest.setTemperament(50);
        assertNotEquals(16, organismTest.getTemperament());
        organismTest.setTemperament(100);
        assertEquals(100, organismTest.getTemperament());
        organismTest.setTemperament(-10);
        assertNotEquals(-10, organismTest.getTemperament());
    }



    @Test
    public void getChoice() {
        assertEquals(-1, organismTest.getChoice());

    }

    @Test
    public void setChoice() {
        organismTest.setChoice(5);
        assertNotEquals(-1, organismTest.getChoice());
        organismTest.setChoice(5);
        assertEquals(5, organismTest.getChoice());
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
  @After
    public void tearDown() throws Exception {
        System.out.println("test done.");
    }
}
