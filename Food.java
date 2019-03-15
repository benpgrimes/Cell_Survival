import java.awt.*;
public class Food extends Cell{
  
  public Food (double x, double y, double diam, int energy){
    this.x = x;
    this.y = y;
    this.diam = diam;
    this.color = Color.RED;
    this.energy = energy;
  }
  //****************************************************************
  //  draw(Graphics)
  //  @param graphic- the buffer we are using to draw the objects
  //  @return void
  //  draws the food object to the buffer
  //****************************************************************
  public void draw(Graphics myBuffer) 
  {
    myBuffer.setColor(color);
    myBuffer.fillRect((int)x, (int)y, (int)diam, (int)diam);
  } 
  public double getX()
  {
    return x;
  }
  
  public void setX(double x)
  {
    this.x = x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public void setY(double y)
  {
    this.y = y;
  }
  
  public int getEnergy()
  {
    return energy;
  }
  
}
