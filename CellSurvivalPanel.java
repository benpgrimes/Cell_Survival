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
  private ArrayList<Integer> redPop;
  private ArrayList<Integer> bluePop;
  private ArrayList<Integer> greenPop;
  private int numRed;
  private int numGreen;
  private int numBlue;
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
         redPop = new ArrayList<Integer>();
         greenPop = new ArrayList<Integer>();
         bluePop = new ArrayList<Integer>();
         numRed = 0;
         numGreen = 0;
         numBlue = 0;
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
                Food food = new Food(Math.random()*1500,Math.random()*1460+20,20,1000);
                foodList.add(food);
                numFood++;
              }
             
             /**********************************
             *  creates new Organism
             ***********************************/
             for(int i = 0; i < 4; i++)
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
               if(turnNum %350 == 0)//Adds food to board
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
             buffer.drawString("Current Cell Population: " + numCells, 100, 1100);
             buffer.drawString("Current # of Food: " + numFood, 100, 1150);
             buffer.drawString("# of cells with curiosity as a primary trait: " + numRed, 100, 1200);
             buffer.drawString("# of cells with temperament as a primary trait:  " + numBlue, 100, 1250);
             buffer.drawString("# of cells with Active as a primary trait:  " + numGreen, 100, 1300);
             break;
             case 2://about
               buffer.setColor(Color.BLACK);
               buffer.fillRect(0,0,N,N);
               buffer.setColor(Color.LIGHT_GRAY);
               buffer.fillRect(170,313,800,100);
               buffer.setColor(Color.GRAY);
               buffer.drawString("CoolDevelopers welcomes you to...", 170, 300);
               buffer.setFont(new Font("Helvetica",Font.BOLD, 100));
               buffer.setColor(Color.BLACK);
               Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
               buffer.setColor(color);
               buffer.drawString("CELL SURVIVAL", 170, 400);
               buffer.setColor(Color.WHITE);
               buffer.setFont(new Font("Helvetica",Font.PLAIN, 40));
               buffer.drawString("A biologically inspired simulation where “cells\"compete", 170, 500);   
               buffer.drawString("for resources and mutate in random ways every time they split.", 170, 550);
               buffer.drawString("As they mutate, their behavior may change between cells.", 170, 600);
               buffer.drawString("This can either help or harm them in the long run.", 170, 650);
               buffer.drawString("Should a cell chase after the closest food, or go towards", 170, 700);
               buffer.drawString("the one with least competition? How big should it get?", 170, 750);
               buffer.drawString("How often should it split? As the simulation goes on, watch as", 170, 800);
               buffer.drawString("survival of the fittest takes place right before your eyes!", 170, 850);
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
