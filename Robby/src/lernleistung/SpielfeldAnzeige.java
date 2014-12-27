package lernleistung;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SpielfeldAnzeige extends JPanel{
	
	private static final long serialVersionUID = 1L; //Versionsnummerierung 
	private Roboter rob;
	
	public SpielfeldAnzeige()
	{

	}

//	public void createFrame() {
//
//		JFrame frame = new JFrame("Robby");
//		frame.add(this);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(517, 540);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		
		int w = getWidth();
		int h = getHeight();

		g.fillRect(0, 0, w, h);
		
		g.setColor(Color.BLACK);
		for( int i=0; i<550;i=i+50)
		{
			g.drawLine(i, 0, i, 500);
			g.drawLine(0, i, 500, i);
		}
		if(rob != null)
		{
			Raum spielfeld = rob.gibRaum();
			for(int x = 0; x<10; x++)
			{
				for(int y = 0; y<10; y++)
				{
					Punkt punkt = new Punkt(x,y);
					if(spielfeld.istMuell(punkt))
					{
						g.setColor(Color.GREEN.darker());
						g.fillRect(x*50+1, y*50+1, 48, 48);
					}
				}
			}
			g.drawImage(rob.gibBild(), 0, 0, null);
		}
	}
	
	public void setzeRoboter(Roboter r)
	{
		rob = r;
//		JOptionPane.showMessageDialog(null, "Durchschnitt Fitness: "+r.gibGesamtFitness());
		rob.setzeAufzeichnen(true);
		rob.leben();
		repaint();
		JOptionPane.showMessageDialog(null, "Fitness: "+r.gibFitness());
		
	}
	
	
}
