
public class Food extends Cell{
  private double x, y, diam;
  private int energy;
  private color;
  public Food (double x, double y, double diam, int energy){
    this.x = x;
    this.y = y;
    this.diam = diam;
    this.color = Color.RED;
    this.energy = energy;
  }
}
