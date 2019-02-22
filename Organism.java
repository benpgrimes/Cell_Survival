
public class Organism extends Cell {
  //the point at which the cells likelihood of choosing growth begins to drop off
  private int growthLimit;
  
  //how likely it is to use size
  private int growthInclination;
  
  //How often it uses examine
  private int curiosity;
  
  //minimum for spltting
  private int maternalMin;
    
  //likelihood of splitting
  private int maternalInclination;
  
  //likelihood of movement vs idle
  private int active;

  //How these values change when the organism is at critical energy levels
  //[0]; energy level required to activate
  //[1]; new growth limit
  //[2]; new growth inclination
  //[3]; new curiosity
  //[4]; new maternal min
  //[5]; new maternal inclination
  //[6]; new active level
  //[7]; new temperment
  private int[8] preservation;
  
  //likelihood of moving towards food
  private int temperment;
}
