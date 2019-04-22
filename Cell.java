/*************************************
* 
* Parent of Food and Organism
*
***************************/
import java.awt.*;

public abstract class Cell {
  protected double x, y, diam;
  protected int energy;
  protected Color color;
  
  public void setX (double x){ //mutator
    this.x = x;
  }
  
  public void setY (double y){ //mutator
    this.y = y;
  }
  
  public void setDiam (double diam){ //mutator
    this.diam = diam;
  }
  
  public void setEnergy (int energy){ //mutator
    this.energy = energy;
  }
  
  public void setColor (Color color){ //mutator
    this.color = color;
  }
  
  public double getX(){ //accessor
    return x;
  }
  
  public double getY(){ //accessor
    return y;
  }
  
  public double getDiam(){ //accessor
    return diam;
  }
  
  public int getEnergy(){ //accessor
    return energy;
  }
  
  public Color getColor(){ //accessor
    return color;
  } 
  public int[] garbageTendencies(){
	  int[] tendencies = new int[15];
	  for(int i = 0; i < 15; i++) {
		  tendencies[i] = 0;
	  }
	  return tendencies;
  }
  public abstract void draw(Graphics myBuffer);

  public double getArea() {
	double r = this.diam / 2;
	double area = 3.14159*(r*r);
	return area;
  }
}
