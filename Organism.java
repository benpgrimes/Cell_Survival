
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
  
  //likelihood of moving towards food
  private int temperment;
  
  //How these values change when the organism is at critical energy levels
  //[0]; energy level required to activate
  //[1]; new growth limit
  //[2]; new growth inclination
  //[3]; new curiosity
  //[4]; new maternal min
  //[5]; new maternal inclination
  //[6]; new active level
  //[7]; new temperment
  private int preservation[] = new int[8];
  
  
  public Organism(double x, double y, double diam, int energy, Color color, int[] tendancies){
    this.x = x;
    this.y = y;
    this.diam = diam;
    this.color = color;
    this.energy = energy;
    this.growthLimit = tendancies[0];
    this.growthInclination = tendancies[1];
    this.curiosity = tendancies[2];
    this.maternalMin = tendancies[3];
    this.maternalInclination = tendancies[4];
    this.active = tendancies[5];
    this.temperment = tendancies[6];
    for(int i = 0; i < 8; i++){
      this.preservation[i] = tendancies[7+i];
    }
  }
}
