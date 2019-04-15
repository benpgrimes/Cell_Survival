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
    int intx = (int) x;
    int inty = (int) y;
    int pointsx[] = new int [3];
    int pointsy[] = new int [3];
    //border for right triangle
    myBuffer.setColor(color.BLACK);
    pointsx[0] = intx + (int)diam/2 - 5 ; pointsx[1] = pointsx[0] + 27; pointsx[2] = pointsx[0] + 27;
    pointsy[0] = inty + 8; pointsy[1] = pointsy[0] + 27; pointsy[2] = pointsy[0] - 27;
    myBuffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
    //right triangle
    myBuffer.setColor(color);
    pointsx[0] = intx + (int)diam/2 +3; pointsx[1] = pointsx[0] + 17; pointsx[2] = pointsx[0] + 17;
    pointsy[0] = inty + 8; pointsy[1] = pointsy[0] + 17; pointsy[2] = pointsy[0] - 17;
    myBuffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
    //border for left triangle
    myBuffer.setColor(color.BLACK);
    pointsx[0] = intx + (int)diam/2 + 5 ; pointsx[1] = pointsx[0] - 27; pointsx[2] = pointsx[0] - 27;
    pointsy[0] = inty + 8; pointsy[1] = pointsy[0] + 27; pointsy[2] = pointsy[0] - 27;
    myBuffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
    //left triangle
    myBuffer.setColor(color);
    pointsx[0] = intx + (int)diam/2 -3; pointsx[1] = pointsx[0] - 17; pointsx[2] = pointsx[0] - 17;
    pointsy[0] = inty + 8; pointsy[1] = pointsy[0] + 17; pointsy[2] = pointsy[0] - 17;
    myBuffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
    //circle
    myBuffer.setColor(color.BLACK);
    myBuffer.fillOval((int)x,(int)y,(int)diam, (int)diam);
    myBuffer.setColor(color.WHITE);
    myBuffer.fillOval((int)x+4,(int)y+4,(int)diam-8, (int)diam-8);
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
