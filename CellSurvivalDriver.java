import javax.swing.JFrame;

public class CellSurvivalDriver
{
  public static void main (String[] args)
  {
    JFrame frame = new JFrame("Cell Survival");
    frame.setSize(1500, 1500);
    frame.setLocation(0, 0);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new CellSurvivalPanel());
    frame.setVisible(true);
      
  }
}
