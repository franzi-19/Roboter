package lernleistung;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SpielfeldAnzeige extends JPanel{
	
	private static final long serialVersionUID = 1L; //Versionsnummerierung 
	private Roboter rob;
	private int index;
	
	public SpielfeldAnzeige()
	{
		
	}
	
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
			Raum spielfeld = rob.gibUrsprungsraum();  //da sonst der Raum gezeichnet würde, wo der Roboter schon Müll aufgehoben hat
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
			g.drawImage(maleWeg(), 0, 0, null);
		}
	}
	
	private BufferedImage maleWeg()
	{
		BufferedImage laufweg = new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB);
		Graphics2D stift = laufweg.createGraphics();
		boolean zufall = false;
		Stroke s= new BasicStroke(3);
		stift.setStroke(s);
		stift.setColor(Color.BLACK);
		int rx = 0, ry = 0;
		for(int i = 0; i<=index; i++) //da der Laufweg jedes Mal neu gezeichnet werden soll
		{
			String aktion = rob.gibLaufweg().get(i);
			String[] teile = aktion.split(";");
			if(teile[0].equals("Start"))
			{
				String[] koordinaten = teile[1].split(" ");
				int x = rx= Integer.parseInt(koordinaten[0]) * 50;
				int y = ry=Integer.parseInt(koordinaten[1]) * 50;
				stift.drawLine(x+5, y+5, x+45, y+45);   //Zeichnung der Startposition
				stift.drawLine(x+45, y+5, x+5, y+45);
			}
			else if(teile[0].equals("Muell"))
			{
				stift.setColor(Color.GREEN);
				String[] koordinaten = teile[1].split(" ");
				int x = Integer.parseInt(koordinaten[0])*50;
				int y = Integer.parseInt(koordinaten[1])*50;
				stift.fillOval(x+10, y+10, 30, 30);
			}
			else if(teile[0].equals("gebueckt"))
			{
				stift.setColor(Color.RED);
				String[] koordinaten = teile[1].split(" ");
				int x = Integer.parseInt(koordinaten[0])*50;
				int y = Integer.parseInt(koordinaten[1])*50;
				stift.fillOval(x +18, y+18, 14, 14);
			}
			else if(teile[0].equals("Schritt"))
			{
				if(zufall)
					stift.setColor(Color.BLUE);
				else
					stift.setColor(Color.BLACK);
				String[] koordinatenAnfang = teile[1].split(" ");
				String[] koordinatenEnde = teile[2].split(" ");
				int xA = Integer.parseInt(koordinatenAnfang[0])*50;
				int yA = Integer.parseInt(koordinatenAnfang[1])*50;
				int xE = rx=Integer.parseInt(koordinatenEnde[0])*50;
				int yE = ry=Integer.parseInt(koordinatenEnde[1])*50;
				stift.drawLine(xA+25, yA+25, xE+25, yE+25);
			}
			else if(teile[0].equals("Wand"))
			{
				stift.setColor(Color.RED);
				String[] koordinatenAnfang = teile[1].split(" ");
				String[] koordinatenEnde = teile[2].split(" ");
				int xA = Integer.parseInt(koordinatenAnfang[0])*50 + 25;
				int yA = Integer.parseInt(koordinatenAnfang[1])*50 + 25;
				int xE = Integer.parseInt(koordinatenEnde[0])*50 + 25;
				int yE = Integer.parseInt(koordinatenEnde[1])*50 + 25;
				if(xE<0)
					xE=0;
				else if(xE>=500)	
					xE=499;
				if(yE<0)
					yE=0;
				else if(yE>=500)	
					yE=499;
				stift.drawLine(xA, yA, xE, yE);
				
			}
			else if(teile[0].equals("Anfang Zufall"))
			{
				zufall = true;
			}
			else if(teile[0].equals("Ende Zufall"))
			{
				zufall = false;
			}
			else
			{
				System.out.println(teile[0]);
			}
			
			
		}
		stift.setColor(Color.gray);
		stift.drawRect(rx + 20, ry + 20, 10, 10);
		return laufweg;
	}
	
	public void setzeRoboter(Roboter robby)
	{
		rob = robby;
		rob.setzeAufzeichnen(true);
		rob.leben();
		index = 0;
		repaint();
	}
	
	public boolean weiter()
	{
		if(rob == null)
			return false;
		index++;
		if(index>= rob.gibLaufweg().size())
		{
			index--;
			return false;
		}
		repaint();
		return true;
		
	}
	
	public boolean zurueck()
	{
		if(rob == null)
			return false;
		index--;
		if(index< 0)
		{
			index++;
			return false;
		}
		repaint();
		return true;
	}
	
	public void vorspulen()
	{
		if(rob!=null)
		{
			index = rob.gibLaufweg().size()-1;
			repaint();
		}
	}
	
	public int gibFitness()
	{
		return rob.gibFitness();
	}
	
	
}
