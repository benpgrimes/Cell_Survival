import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class CellSurvivalPanel extends JPanel
{
  //Intinatiating variables
  private BufferedImage myImage;
  private Graphics buffer;
  private Timer t;
  private static final int N = 1500;
  private static int screen = 0;
  public CellSurvivalPanel()
      {
        //declare variables
         myImage = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         buffer = myImage.getGraphics();    
         t = new Timer(10, new Listener());
         t.start();
         addMouseListener(new Mouse());
         
         //************************************************
         // THIS IS THE MAIN SECTION THE MATH WILL BE DONE
         //************************************************
         
         switch(screen)
         {
          case 0: //board
            break;
          case 1: //graph
            break;
           case 2: //about
            break;
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
             buffer.setColor(Color.WHITE);
             buffer.fillRect(0,0,N,N);
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
