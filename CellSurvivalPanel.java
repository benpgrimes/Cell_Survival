import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.LinkedList;
import java.util.ArrayList;

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
  private int numFood = 0;
  private int numCells = 0;
  private ArrayList<Integer> population;
  private int turnNum;
  
  public CellSurvivalPanel()
      {
        //declare variables
         myImage = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         buffer = myImage.getGraphics();    
         t = new Timer(10, new Listener());
         t.start();
         organismList = new LinkedList<Organism>();
         foodList = new LinkedList<Food>();
         population = new ArrayList<Integer>();
         turnNum = 0;
         addMouseListener(new Mouse());
         
         //************************************************
         // THIS IS THE SECTION THAT SETS EVERYTHING UP
         //************************************************
          /**********************************
             *  creates new food
             ***********************************/
             for(int i = 0; i < 8; i++)
             {
                Food food = new Food(Math.random()*1500,Math.random()*1460+20,20,20);
                foodList.add(food);
                numFood++;
              }
             foodList.get(1).setColor(Color.PINK);
             foodList.get(2).setColor(Color.GREEN);
             foodList.get(3).setColor(Color.ORANGE);
             /**********************************
             *  creates new Organism
             ***********************************/
             for(int i = 0; i < 4; i++)
             {
               int tendancies[] = new int[15];
               for(int j = 0; j < 15; j++)
               {
                 tendancies[j] = 1;
               }
               Organism organism = new Organism(Math.random()*1500,Math.random()*1500,30.0,100000,Color.BLUE,tendancies);
               organismList.add(organism);
               numCells++;
             }
               
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
            
             
             //Draws Background
             buffer.setColor(Color.WHITE);
             buffer.fillRect(0,0,N,N);
             
             //Iterates through food list
             for(int i = 0; i < numFood; i++)
             {
               foodList.get(i).draw(buffer);
             }
               
             //Iterates through Organism list
             for(int i = 0; i < numCells; i++)
             {
               organismList.get(i).move();
               int temp = organismList.get(i).basicChoose();
               if(temp < 6)
                 organismList.get(i).grow();
               else if(temp < 36)
                 organismList.get(i).examine(foodList);
               else if(temp  < 38 && organismList.get(i).getDiam() > 50)
               {
                 Organism child;
                 child = organismList.get(i).split();
                 organismList.add(child);
                 numCells++;
               }
               else if(temp < 76)
                 organismList.get(i).move();
               else
                 organismList.get(i).idle();
              
               //Checks for collision
               if(i>0)
               {
                 for(int j = 0; j < numFood; j++)
                 {
                   if(organismList.get(i).collision(foodList.get(j).getX(),foodList.get(j).getY(),
                                                    foodList.get(j).getEnergy()))
                   {
                     foodList.get(j).setX(Math.random()*1500);
                     foodList.get(j).setY(Math.random()*1460+20);
                   }
                 }
                 organismList.get(i).draw(buffer);
               }
               if(organismList.get(i).getEnergy() <= 0)
               {
                 organismList.remove(i);
                 i--;
                 numCells--;
               }
               if(turnNum %1000 == 0)
               {
                 population.add(numCells);
               }
               turnNum++;
             }
             break;
           case 1://graph
             buffer.setColor(Color.WHITE);
             buffer.fillRect(0,0,N,N);
             buffer.setColor(Color.BLACK);
             buffer.drawLine(100,100,100,1000);
             buffer.drawLine(100,1000,1400,1000);
             buffer.drawString("population:  ",20,45);
             
             buffer.setColor(Color.GRAY);
             for(int i = 0; i < 900; i+=80)
             {
               
               buffer.drawLine(100,1000-i,1400,1000-i);
             }
         
             
             buffer.setColor(Color.BLACK);
             for(int i = 0; i < population.size()-1;i++)
             {
               buffer.drawLine(100+i*10,1000-population.get(i)*8,100+(i+1)*10,1000-population.get(i+1)*8);
             }
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
           
           buffer.drawString("Graph:  ",100,25);
           buffer.drawRect(100,10,90,20);
           
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
