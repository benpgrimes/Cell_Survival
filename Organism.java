import java.awt.Color;
import java.util.*;

/******************************************
* Class for the moving organisms
*
*
*
******************************************/
public class Organism extends Cell {
  //the point at which the cells likelihood of choosing growth begins to drop off
  private int growthLimit;
  
  //how likely it is to use size
  private int growthInclination;
  
  //How often it uses examine
  private int curiosity;
  
  //minimum for splitting
  private int maternalMin;
    
  //likelihood of splitting
  private int maternalInclination;
  
  //likelihood of movement vs idle
  private int active;
  
  //likelihood of moving towards food
  private int temperament;
  
  //How these values change when the organism is at critical energy levels
  //[0]; energy level required to activate
  //[1]; new growth limit
  //[2]; new growth inclination
  //[3]; new curiosity
  //[4]; new maternal min
  //[5]; new maternal inclination
  //[6]; new active level
  //[7]; new temperament
  private int preservation[] = new int[8];
  
  //stores the next choice the cell will execute
  private int choice = -1;
  
  /*************************
   * Constructor for Organism
   * @param x: The starting x value on the coordinate plane
   * @param y: The starting y value on the coordinate plane
   * @param diam: starting size
   * @param energy: starting energy
   * @param color: color of organism
   * @param tendencies: tendencies of organism
   */
  public Organism(double x, double y, double diam, int energy, Color color, int[] tendencies){
    this.x = x;
    this.y = y;
    this.diam = diam;
    this.color = color;
    this.energy = energy;
    this.growthLimit = tendencies[0];
    this.growthInclination = tendencies[1];
    this.curiosity = tendencies[2];
    this.maternalMin = tendencies[3];
    this.maternalInclination = tendencies[4];
    this.active = tendencies[5];
    this.temperament = tendencies[6];
    for(int i = 0; i < 8; i++){
      this.preservation[i] = tendencies[7+i];
    }
  }
  /********************************************
  * Description: Puts all the tendencies into a single array
  * @param None
  * @return integer array of all tendencies (15 values) in the order shown:
  * [0]; energy level required to activate
  * [1]; new growth limit
  * [2]; new growth inclination
  * [3]; new curiosity
  * [4]; new maternal minimum
  * [5]; new maternal inclination
  * [6]; new active level
  * [7]; new temperament
  * [8-14] preservation in same order
  *******************************************/
  public int[] getTendencies(){
    int[] tendencies = new int[15];
    tendencies[0] = this.growthLimit;
    tendencies[1] = this.growthInclination;
    tendencies[2] = this.curiosity;
    tendencies[3] = this.maternalMin;
    tendencies[4] = this.maternalInclination;
    tendencies[5] = this.active;
    tendencies[6] = this.temperament;
    for(int i = 0; i < 8; i++){
      tendencies[7+i] = this.preservation[i];
    }
    return tendencies;
  }
  
  /*******************************************
  * returns the choice made as well as makes its next choice.
  * @param None
  * 
  ***********************************************/
  public int choose(){
    boolean first;
    int result = -1;
    do{
      first = this.choice == -1;
      result = this.choice;
      //TO DO
    }while(first);
    return result;
  } 
  
  /*
  public void die(){
    Food food = new Food(this.x, this.y, this.diam, int(this.diam));
    
  }  */
  
  public int getGrowthLimit() {
	  return growthLimit;
  }

  public void setGrowthLimit(int growthLimit) {
	  this.growthLimit = growthLimit;
  }

  public int getGrowthInclination() {
	  return growthInclination;
  }

  public void setGrowthInclination(int growthInclination) {
	  this.growthInclination = growthInclination;
  }

  public int getCuriosity() {
	  return curiosity;
  }

  public void setCuriosity(int curiosity) {
  	this.curiosity = curiosity;
  }

  public int getMaternalMin() {
	  return maternalMin;
  }

  public void setMaternalMin(int maternalMin) {
	  this.maternalMin = maternalMin;
  }

  public int getMaternalInclination() {
	  return maternalInclination;
  }

  public void setMaternalInclination(int maternalInclination) {
	  this.maternalInclination = maternalInclination;
  }

  public int getActive() {
	  return active;
  }

  public void setActive(int active) {
	  this.active = active;
  }

  public int getTemperament() {
	  return temperament;
  }
  
  public void setTemperament(int temperament) {
  	this.temperament = temperament;
  }
  
  public int[] getPreservation() {
	  return preservation;
  }
  
  public void setPreservation(int[] preservation) {
	  this.preservation = preservation;
  }

  public int getChoice() {
  	return choice;
  }

  public void setChoice(int choice) {
  	this.choice = choice;
  } 
  
}
