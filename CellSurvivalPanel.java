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
  private static int screen = 4;
  private LinkedList<Organism> organismList;
  private LinkedList<Food> foodList;
  private int numFood = 0;
  private int numCells = 0;
  private ArrayList<Integer> population;
  private ArrayList<Integer> redPop;
  private ArrayList<Integer> bluePop;
  private ArrayList<Integer> greenPop;
  private int numRed;
  private int numGreen;
  private int numBlue;
  private int turnNum;
  private boolean choice;
  private static int numStartOrganisms;
  private static int numStartFood;
    
  
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
    redPop = new ArrayList<Integer>();
    greenPop = new ArrayList<Integer>();
    bluePop = new ArrayList<Integer>();
    numRed = 0;
    numGreen = 0;
    numBlue = 0;
    turnNum = 0;
    numStartOrganisms = 4;
    numStartFood = 8;
    choice = true;//if true choose organisms if false choose food in start menu
    addMouseListener(new Mouse());
    addKeyListener(new Key());
    setFocusable(true);
    
    //************************************************
    // THIS IS THE SECTION THAT SETS EVERYTHING UP
    //************************************************
    
    
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
        if(e.getX() < 90 && e.getY() <50)
          screen = 0;
        else if(e.getX() < 190 && e.getY() <50)
          screen = 1;
        else if(e.getX() < 290 && e.getY() <50)
          screen = 2;
        System.out.println("X: "+e.getX()+" Y: "+e.getY());
      } 
    }
  }
  //******************************************************
  // The key listener gets input from the keyboard
  //
  //******************************************************
  private class Key extends KeyAdapter
  {
    public void keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_X)//QUICK START
      {
        numStartOrganisms = 4;
        numStartFood = 8;
        /**********************************
        *  creates new food
        ***********************************/
        for(int i = 0; i < numStartFood; i++)
        {
          Food food = new Food(Math.random()*1500,Math.random()*1460+20,20,500);
          foodList.add(food);
          numFood++;
        }
        
        /**********************************
        *  creates new Organism
        ***********************************/
        for(int i = 0; i < numStartOrganisms; i++)
        {
          int tendancies[] = new int[15];
          for(int j = 0; j < 15; j++)
          {
            tendancies[j] = 100;
          }
          
          Organism organism = new Organism(Math.random()*1500,Math.random()*1500,30.0,10000,Color.BLUE,tendancies);
          organismList.add(organism);
          numCells++;
          if(organism.getGrowthInclination() >= organism.getMaternalInclination()&&organism.getGrowthInclination() >= organism.getActive())
          { //RED
            numRed++;
          }
          else if(organism.getMaternalInclination() >= organism.getActive() &&organism.getMaternalInclination() >= organism.getGrowthInclination())
          {//BLUE
            numGreen++;
          }
          else if(organism.getActive() >= organism.getMaternalInclination() &&organism.getActive() >= organism.getGrowthInclination())
          {//GREEN
            numBlue++;
          }
        }
        screen = 0;
      }
      if(choice == true)//Num organisms
      {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
          choice = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
          numStartOrganisms++;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
          numStartOrganisms--;
        }
      }
      else //start number of food
      {
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
          choice = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
          numStartFood++;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
          numStartFood--;
        }
      }
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        /**********************************
        *  creates new food
        ***********************************/
        for(int i = 0; i < numStartFood; i++)
        {
          Food food = new Food(Math.random()*1500,Math.random()*1460+20,20,500);
          foodList.add(food);
          numFood++;
        }
        
        /**********************************
        *  creates new Organism
        ***********************************/
        for(int i = 0; i < numStartOrganisms; i++)
        {
          int tendancies[] = new int[15];
          for(int j = 0; j < 15; j++)
          {
            tendancies[j] = 100;
          }
          
          Organism organism = new Organism(Math.random()*1500,Math.random()*1500,30.0,10000,Color.BLUE,tendancies);
          organismList.add(organism);
          numCells++;
          if(organism.getGrowthInclination() >= organism.getMaternalInclination()&&organism.getGrowthInclination() >= organism.getActive())
          { //RED
            numRed++;
          }
          else if(organism.getMaternalInclination() >= organism.getActive() &&organism.getMaternalInclination() >= organism.getGrowthInclination())
          {//BLUE
            numGreen++;
          }
          else if(organism.getActive() >= organism.getMaternalInclination() &&organism.getActive() >= organism.getGrowthInclination())
          {//GREEN
            numBlue++;
          }
        }
        screen = 0;
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
          
          //draws background
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
            Organism tempOrg = organismList.get(i);
            tempOrg.move();
            int temp = tempOrg.choose();
            if(temp == 1)
              tempOrg.grow();
            else if(temp == 2&&foodList!=null&&numFood>0)
              tempOrg.examine(organismList, foodList, numCells, numFood);
            else if(temp == 3)//-------SPLIT-----
            {
              Organism child;
              child = tempOrg.split();
              //curisoity red
              //temperament blue
              //active level green
              if(child.getGrowthInclination() >= child.getMaternalInclination()&&child.getGrowthInclination() >= child.getActive())
              { //RED
                numRed++;
              }
              else if(child.getMaternalInclination() >= child.getActive() &&child.getMaternalInclination() >= child.getGrowthInclination())
              {//BLUE
                numGreen++;
              }
              else if(child.getActive() >= child.getMaternalInclination() &&child.getActive() >= child.getGrowthInclination())
              {//GREEN
                numBlue++;
              }
              organismList.add(child);
              numCells++;
            }
            else if(temp == 4)
              tempOrg.move();
            else if(temp == 5)
              tempOrg.idle();
            
            //Checks for collision
            if(i>0)
            {
              for(int j = 0; j < numFood; j++)
              {
                if(tempOrg.collision(foodList.get(j).getX(),foodList.get(j).getY(),
                                     foodList.get(j).getEnergy()))
                {
                  foodList.remove(j);
                  j--;
                  numFood--;
                  //foodList.get(j).setX(Math.random()*1500);
                  //foodList.get(j).setY(Math.random()*1460+20);
                }
              }
              tempOrg.draw(buffer);
            }
            if(tempOrg.getEnergy() <= 0)//REMOVING DEAD CELLS
            {
              if(tempOrg.getGrowthInclination() >= tempOrg.getMaternalInclination()&&tempOrg.getGrowthInclination() >= tempOrg.getActive())
              { //RED
                numRed--;
              }
              else if(tempOrg.getMaternalInclination() >= tempOrg.getActive() &&tempOrg.getMaternalInclination() >= tempOrg.getGrowthInclination())
              {//BLUE
                numGreen--;
              }
              else if(tempOrg.getActive() >= tempOrg.getMaternalInclination() &&tempOrg.getActive() >= tempOrg.getGrowthInclination())
              {//GREEN
                numBlue--;
              }
              Food food = new Food(tempOrg.getX(),tempOrg.getY(),20,500);
              foodList.add(food);
              numFood++;
              organismList.remove(i);
              i--;
              numCells--;
            }
            
            if(turnNum %1000 == 0)//Adds to graph 
            {
              population.add(numCells);
              redPop.add(numRed);
              greenPop.add(numGreen);
              bluePop.add(numBlue);
            }
            if(turnNum %400 == 0)//Adds food to board
            {
              Food food = new Food(Math.random()*1500,Math.random()*1460+20,20,1000);
              foodList.add(food);
              numFood++;
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
          
          //border
          buffer.setColor(Color.BLACK);
          buffer.fillRect(100, 100, 1300, 10);
          buffer.fillRect(100, 1000, 1300, 10);
          buffer.fillRect(94, 100, 7, 910);
          buffer.fillRect(1400, 100, 7, 910);
          buffer.drawString("population:  ",20,45); 
          
          buffer.setColor(Color.GRAY);
          for(int i = 0; i < 900; i+=110)
          {       
            buffer.drawLine(100,1000-i,1400,1000-i);
          }
          
          
          buffer.setColor(Color.BLACK);
          for(int i = 0; i < population.size()-1;i++)//TOTAL POPULATION
          {
            buffer.drawLine(100+i*10,1000-population.get(i)*11,100+(i+1)*10,1000-population.get(i+1)*11);
          }
          
          buffer.setColor(Color.RED);//RED POPULATION
          for(int i = 0; i < redPop.size()-1;i++)
          {
            buffer.drawLine(100+i*10,1000-redPop.get(i)*11,100+(i+1)*10,1000-redPop.get(i+1)*11);
          }
          
          buffer.setColor(Color.BLUE);//BLUE POPULATION
          for(int i = 0; i < bluePop.size()-1;i++)
          {
            buffer.drawLine(100+i*10,1000-bluePop.get(i)*11,100+(i+1)*10,1000-bluePop.get(i+1)*11);
          }
          
          buffer.setColor(Color.GREEN);//GREEN POPULATION
          for(int i = 0; i < greenPop.size()-1;i++)
          {
            buffer.drawLine(100+i*10,1000-greenPop.get(i)*11,100+(i+1)*10,1000-greenPop.get(i+1)*11);
          }
          buffer.setColor(Color.BLACK);
          buffer.setFont(new Font("Dialog",Font.BOLD, 40));
          buffer.drawString("Current Cell Population (BLACK): " + numCells, 100, 1100);
          buffer.drawString("Current # of Food: " + numFood, 100, 1150);
          buffer.drawString("# of cells with curiosity (RED) as a primary trait: " + numRed, 100, 1200);
          buffer.drawString("# of cells with Active (BLUE) as a primary trait:  " + numBlue, 100, 1250);
          buffer.drawString("# of cells with Temperament (GREEN) as a primary trait:  " + numGreen, 100, 1300);
          break;
          
          
        case 2://about
          buffer.setColor(Color.BLACK);
          buffer.fillRect(0,0,N,N);
          buffer.setColor(Color.DARK_GRAY);
          buffer.fillRect(170,423,1050,150);
          buffer.setColor(Color.WHITE);
          buffer.setFont(new Font("Helvetica",Font.BOLD, 60));
          buffer.drawString("Welcome to...", 170, 395);
          buffer.setFont(new Font("Helvetica",Font.BOLD, 130));
          Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
          buffer.setColor(color);
          buffer.drawString("CELL SURVIVAL", 170, 550);
          buffer.setColor(Color.WHITE);
          buffer.setFont(new Font("Helvetica",Font.PLAIN, 40));
          //body
          buffer.drawString("A biologically inspired simulation where “cells\" compete", 170, 640);   
          buffer.drawString("for resources and mutate in random ways every time they split.", 170, 690);
          buffer.drawString("As they mutate, their behavior may change between cells.", 170, 740);
          buffer.drawString("This can either help or harm them in the long run.", 170, 790);
          buffer.drawString("Should a cell chase after the closest food, or go towards", 170, 840);
          buffer.drawString("the one with least competition? How big should it get?", 170, 890);
          buffer.drawString("How often should it split? As the simulation goes on, watch as", 170, 940);
          buffer.drawString("survival of the fittest takes place right before your eyes!", 170, 990);
          //credits
          buffer.setColor(Color.GRAY);
          buffer.setFont(new Font("Helvetica",Font.ITALIC, 30));            
          buffer.drawString("Note: This software is provided AS IS and is not subject to warranty. This software is", 170, 1060);
          buffer.drawString("run at the discretion of the user and is not responsible for any potential misuse.", 170, 1090);
          buffer.drawString("© 2019", 170, 1120);
          
          //graphics # 1
          int intx = 110;
          int inty = 160;
          int diam = 50;
          int pointsx[] = new int [3];
          int pointsy[] = new int [3];
          //border for right triangle
          buffer.setColor(Color.GRAY);
          pointsx[0] = intx + (int)diam/2 ; pointsx[1] = pointsx[0] + 75; pointsx[2] = pointsx[0] + 75;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 75; pointsy[2] = pointsy[0] - 75;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
          //right triangle
          buffer.setColor(Color.GREEN);
          pointsx[0] = intx + (int)diam/2 +8; pointsx[1] = pointsx[0] + 60; pointsx[2] = pointsx[0] + 60;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 60; pointsy[2] = pointsy[0] - 60;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
          //border for left triangle
          buffer.setColor(Color.GRAY);
          pointsx[0] = intx + (int)diam/2 ; pointsx[1] = pointsx[0] - 75; pointsx[2] = pointsx[0] - 75;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 75; pointsy[2] = pointsy[0] - 75;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
          //left triangle
          buffer.setColor(Color.GREEN);
          pointsx[0] = intx + (int)diam/2 -8; pointsx[1] = pointsx[0] - 60; pointsx[2] = pointsx[0] - 60;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 60; pointsy[2] = pointsy[0] - 60;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
          //circle
          buffer.setColor(Color.GRAY);
          buffer.fillOval(intx,inty,diam, diam);
          buffer.setColor(Color.WHITE);
          buffer.fillOval(intx+4, inty+4, diam-8, diam-8);
          
          //graphics # 2
          intx = 1300;
          inty = 1250;
          //border for right triangle
          buffer.setColor(Color.GRAY);
          pointsx[0] = intx + (int)diam/2 ; pointsx[1] = pointsx[0] + 75; pointsx[2] = pointsx[0] + 75;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 75; pointsy[2] = pointsy[0] - 75;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
          //right triangle
          buffer.setColor(Color.MAGENTA);
          pointsx[0] = intx + (int)diam/2 +8; pointsx[1] = pointsx[0] + 60; pointsx[2] = pointsx[0] + 60;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 60; pointsy[2] = pointsy[0] - 60;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
          //border for left triangle
          buffer.setColor(Color.GRAY);
          pointsx[0] = intx + (int)diam/2 ; pointsx[1] = pointsx[0] - 75; pointsx[2] = pointsx[0] - 75;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 75; pointsy[2] = pointsy[0] - 75;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3)); 
          //left triangle
          buffer.setColor(Color.MAGENTA);
          pointsx[0] = intx + (int)diam/2 -8; pointsx[1] = pointsx[0] - 60; pointsx[2] = pointsx[0] - 60;
          pointsy[0] = inty + 15; pointsy[1] = pointsy[0] + 60; pointsy[2] = pointsy[0] - 60;
          buffer.fillPolygon(new Polygon(pointsx, pointsy, 3));
          //circle
          buffer.setColor(Color.GRAY);
          buffer.fillOval(intx,inty,diam, diam);
          buffer.setColor(Color.WHITE);
          buffer.fillOval(intx+4, inty+4, diam-8, diam-8);   
          break;
          
          
        case 4://START SCREEN
          buffer.setColor(Color.WHITE);
          buffer.fillRect(0,0,N,N);
          buffer.setColor(Color.BLACK);
          buffer.setFont(new Font("Helvetica",Font.PLAIN, 40));
          buffer.drawString("Press \"x\" for the quick start simulation",170,150);
          buffer.drawString("How many organisms would you like to start the simulation with?  "+numStartOrganisms,170,200);
          buffer.drawString("How much food would you like to start the simulation with?  "+numStartFood,170,250);
          buffer.drawString("Press the left and right arrow key to choose how many",170,350);
          buffer.drawString("And then press up and down arrow keys to switch between the choices",170,400);
          buffer.drawString("When you are satisfied, press the enter key to start the simulation",170,500);
          
          
          break; 
      }
      
      //THIS IS CREATING THE TAB LABLES  
      buffer.setColor(Color.BLACK);
      buffer.fillRect(0,0,300,50);
      buffer.setColor(Color.WHITE);
      buffer.fillRect(5,5,290,42);
      
      buffer.setColor(Color.BLACK); 
      buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
      buffer.drawString("BOARD  ",20,35); //added 20 from previous           
      buffer.fillRect(97, 5, 5,45);
      
      buffer.setColor(Color.BLACK); 
      buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
      buffer.drawString("GRAPH  ",110,35);
      buffer.fillRect(197, 5, 5,45);
      
      buffer.setColor(Color.BLACK);
      buffer.setFont(new Font("Monospaced", Font.BOLD, 24));
      buffer.drawString("ABOUT  ",210,35);
      
      repaint();
    }
  }
  
  public void paintComponent(Graphics g)
  {
    g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
  }
}
