import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.LinkedList;

public class CellSurvivalPanel extends JPanel
{
  //Intinatiating variables
  private BufferedImage myImage;
  private Graphics buffer;
  private Timer t;
  private static final int N = 1500;
  private static int screen = 0;
  private LinkedList<Organism> organismList;
  private LinkedList<Food> foodList;
  
  public CellSurvivalPanel()
      {
        //declare variables
         myImage = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         buffer = myImage.getGraphics();    
         t = new Timer(10, new Listener());
         t.start();
         organismList = new LinkedList<Organism>();
         foodList = new LinkedList<Food>();
         addMouseListener(new Mouse());
         
         //************************************************
         // THIS IS THE SECTION THAT SETS EVERYTHING UP
         //************************************************
         Food food = new Food(100.0,100.0,20,20);
         foodList.addFirst(food);
         //[0]; energy level required to activate
         //[1]; new growth limit
         //[2]; new growth inclination
         //[3]; new curiosity
         //[4]; new maternal min
         //[5]; new maternal inclination
         //[6]; new active level
         //[7]; new temperament
         int tendancies[] = new int[15];
         for(int i = 0; i < 15; i++)
         {
           tendancies[i] = 0;
         }
         Organism organism = new Organism(200.0,200.0,30.0,10000000,Color.BLUE,tendancies);
         organismList.add(organism);
    
         
      }
      //**************************************************
      // The mouse adapter is what recognizes where the 
      // mouse is being clicked
      //**************************************************
      private class Mouse extends MouseAdapter
      {
        public void mousePressed( MouseEvent e )
        {
          if(e.isMetaDown() == false)//checks if left click
          {
            if(e.getX() < 100 && e.getY() <20)
              screen = 0;
            else if(e.getX() < 190 && e.getY() <20)
              screen = 1;
            else if(e.getX() < 280 && e.getY() <20)
              screen = 2;
            System.out.println("X: "+e.getX()+" Y: "+e.getY());
          } 
          
        }
      }
      //**************************************************
      //  the action listener is the section where things
      //  get drawn to the screen.  i.e. every thime the
      //  screen refreshes this code runs
      //**************************************************
       private class Listener implements ActionListener
       {
         public void actionPerformed(ActionEvent e)
         {
           switch(screen)
           {
           case 0://board
             buffer.setColor(Color.WHITE);
             buffer.fillRect(0,0,N,N);
             foodList.element().draw(buffer);
             organismList.element().examine(foodList.element().getX(),foodList.element().getY());
             organismList.element().move();
             if(organismList.element().collision(foodList.element().getX(),foodList.element().getY(),foodList.element().getEnergy()))
             {
               foodList.element().setX(Math.random()*1500);
               foodList.element().setY(Math.random()*1500);
             }
             organismList.element().draw(buffer);
             break;
           case 1://graph
             buffer.setColor(Color.BLUE);
             buffer.fillRect(0,0,N,N);
             break;
           case 2://about
             buffer.setColor(Color.RED);
             buffer.fillRect(0,0,N,N);
             break;
           }
           
           //THIS IS CREATING THE TAB LABLES        
           buffer.setColor(Color.BLACK); 
           buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
           buffer.drawString("Board:  ",10,25);
           buffer.drawRect(10,10,90,20);
           
           buffer.setColor(Color.BLACK); 
           buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
           buffer.drawString("Graph:  ",100,25);
           buffer.drawRect(100,10,90,20);
           
           buffer.setColor(Color.BLACK); 
           buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
           buffer.drawString("About:  ",200,25);
           buffer.drawRect(190,10,90,20);
           repaint();
      }
   }

      public void paintComponent(Graphics g)
      {
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
      }
   }
