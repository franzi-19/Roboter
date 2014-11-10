package lernleistung;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Roboter  implements Comparable<Roboter>{
	
	private int ausrichtung;
	private Punkt standort; 
	private Zustandssammlung sammlung;
	private Raum raum;
	private int zaehler;
	private int[] dna;
	private int fitness;
	private int gesamtFitness;
	private BufferedImage bild;
	private Graphics2D stift;
	private boolean aufzeichnen;
	
	public Roboter()
	{
		sammlung = new Zustandssammlung();
		fitness = 0;
		standort = new Punkt(myRandom(0, 10),myRandom(0, 10));
		ausrichtung = 0;
		raum = new Raum();
		zaehler = 0;
		dna = new int[sammlung.gibLaenge()];
		dnaErstellen();
		gesamtFitness = 0;
	}
	
	public Roboter(int[]DNA)
	{
		this();
		dna = DNA;
	}
	
	public Roboter(int[] DNA, boolean aufzeichnen)
	{
		this(DNA);
		this.aufzeichnen = aufzeichnen;
	}
	
//	public static void main(String[] args) 
//	{
//		Roboter robby = new Roboter();
//		robby.ausgabeDNA();
//		robby.leben();
//	}
	
	public void dreheRechts()
	{
		if(ausrichtung == 3)
			ausrichtung = 0;
		else
			ausrichtung++;
	}
	
	public void dreheLinks()
	{
		if(ausrichtung == 0)
			ausrichtung = 3;
		else
			ausrichtung--;
	}
	
	public void umdrehen()
	{
		if(ausrichtung == 3)
			ausrichtung = 1;
		else if(ausrichtung == 2)
			ausrichtung = 0;
		else
			ausrichtung= ausrichtung +2;
	}
	
	public void geheEinenSchritt()
	{
		Punkt neu = KonVorne();
		if(raum.istWand(neu))
		{
			fitness -= 5;
			if(aufzeichnen)
			{
				Color alt = stift.getColor();
				stift.setColor(Color.RED);
				stift.fillOval(standort.getXKon()*50 +10, standort.getYKon()*50+10, 30, 30);
				stift.setColor(alt);
			}
		}
		else
		{
			if(aufzeichnen)
				stift.drawLine(standort.getXKon()*50+25, standort.getYKon()*50+25, neu.getXKon()*50+25, neu.getYKon()*50+25);
			standort = neu;
		}
	}
	
	public void geheZufaellig() 
	{
		Color alt = null;
		if(aufzeichnen)
		{
			alt = stift.getColor();
			stift.setColor(Color.BLUE);
		}
			
		boolean wiederholen = true;
		while(wiederholen == true)
		{
			int zufall = myRandom(0, 6);
			for(int k = 0; k< zufall; k++)
			{
				if(zufall <=2)
					dreheRechts();
				else
					dreheLinks();
			}
			zufall = myRandom(0, 5);
			for(int i = 0; i< zufall; i++)
			{;
				geheEinenSchritt();
			}
			zufall = myRandom(0, 5);
			if(zufall >2)
				wiederholen = false;
		}
		if(aufzeichnen)
		{
			stift.setColor(alt);
		}
	}
	
	public Punkt KonVorne()
	{
		int x = 0;
		int y = 0;
		if(ausrichtung == 0)
		{
			x = standort.getXKon();
			y = standort.getYKon() -1;
		}
		if(ausrichtung == 1)
		{
			x = standort.getXKon() +1;
			y = standort.getYKon();
		}
		if(ausrichtung == 2)
		{
			x = standort.getXKon();
			y = standort.getYKon() +1;
		}
		if(ausrichtung == 3)
		{
			x = standort.getXKon() -1;
			y = standort.getYKon();
		}
		Punkt vorne = new Punkt(x, y);
		return vorne;
	}
	
	public Punkt KonRechts()
	{
		int x = 0;
		int y = 0;
		if(ausrichtung == 3)
		{
			x = standort.getXKon();
			y = standort.getYKon() -1;
		}
		if(ausrichtung == 0)
		{
			x = standort.getXKon() +1;
			y = standort.getYKon();
		}
		if(ausrichtung == 1)
		{
			x = standort.getXKon();
			y = standort.getYKon() +1;
		}
		if(ausrichtung == 2)
		{
			x = standort.getXKon() -1;
			y = standort.getYKon();
		}
		Punkt rechts = new Punkt(x, y);
		return rechts;
	}
	
	public Punkt KonHinten()
	{
		int x = 0;
		int y = 0;
		if(ausrichtung == 2)
		{
			x = standort.getXKon();
			y = standort.getYKon() -1;
		}
		if(ausrichtung == 3)
		{
			x = standort.getXKon() +1;
			y = standort.getYKon();
		}
		if(ausrichtung == 0)
		{
			x = standort.getXKon();
			y = standort.getYKon() +1;
		}
		if(ausrichtung == 1)
		{
			x = standort.getXKon() -1;
			y = standort.getYKon();
		}
		Punkt hinten = new Punkt(x, y);
		return hinten;
	}
	
	public Punkt KonLinks()
	{
		int x = 0;
		int y = 0;
		if(ausrichtung == 1)
		{
			x = standort.getXKon();
			y = standort.getYKon() -1;
		}
		if(ausrichtung == 2)
		{
			x = standort.getXKon() +1;
			y = standort.getYKon();
		}
		if(ausrichtung == 3)
		{
			x = standort.getXKon();
			y = standort.getYKon() +1;
		}
		if(ausrichtung == 0)
		{
			x = standort.getXKon() -1;
			y = standort.getYKon();
		}
		Punkt links = new Punkt(x, y);
		return links;
	}
	
	public int berechneZustand() //leer = *1; müll = *2; wand = *3
	{
		int nummer = 1;
		int zustand = 0;
		Stack<Punkt> kon = new Stack<Punkt>();
		kon.push(standort);
		kon.push(KonLinks());
		kon.push(KonHinten());
		kon.push(KonRechts());
		kon.push(KonVorne());
		
		while(nummer <= 10000)
		{   
			Punkt hilfe = kon.pop();
			if(raum.istWand(hilfe))
				zustand = zustand + nummer*3;
			else if(raum.istMuell(hilfe))
					zustand = zustand + nummer*2;
			else
				zustand = zustand + nummer*1;
			nummer = nummer*10;
		}
		nummer = sammlung.gibZustandsNummer(zustand);
		return nummer;
	}
	
	public void dnaErstellen() //6 mögliche Aktionen
	{
		int zufall = 0;
		for(int i = 0; i<sammlung.gibLaenge(); i++)
		{
			zufall = myRandom(1, 7);
			dna[i] = zufall;
		}
	}
	
	public int[] gibDNA()
	{
		return dna;
	}
	
	public int gibDNALaenge()
	{
		return sammlung.gibLaenge();
	}
	
	public void ausgabeDNA()
	{
		int ueberpruefung = 0;
		String dnaCode = "";
		for(int i = 0; i<sammlung.gibLaenge(); i++)
		{
			ueberpruefung++;
			dnaCode = dnaCode + dna[i];
			
		}
		System.out.println(dnaCode);
		System.out.println(ueberpruefung);
	}
	
	public void leben() //1=links drehen; 2=rechts drehen; 3=einen Schritt; 4=zufällig gehen; 5=müll aufheben, 6= sich umdrehen
	{
		zaehler++;
		if(aufzeichnen)
		{
			bild = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
			stift = bild.createGraphics();
			stift.setColor(Color.BLACK);
		}
		fitness = 0;
		raum = new Raum();
		int action = 0;
		int umgebung = 0;
		while(action++ <300)  //darf 300 aktionen ausführen
		{
			umgebung = berechneZustand();
			fuehreAktionAus(dna[umgebung]);
		}
		gesamtFitness = gesamtFitness + gibFitness();
	}

	private void fuehreAktionAus(int action) 
	{
		if(action == 1)
		{
			dreheLinks();
			if(aufzeichnen)
				System.out.println("dreheLinks");
		}
		else if(action == 2)
		{
			dreheRechts();
			if(aufzeichnen)
				System.out.println("dreheRechts");
		}
		else if(action == 3 || action == 6)
		{
			geheEinenSchritt();
			if(aufzeichnen)
				System.out.println("einSchritt");
		}
		else if(action == 4)
		{
			geheZufaellig();
			if(aufzeichnen)
				System.out.println("Zufällig");
		}
		else if(action == 5)
		{
			if(raum.istMuell(standort))
			{
				raum.muellAufsammeln(standort);
				if(aufzeichnen)
				{
					Color alt = stift.getColor();
					stift.setColor(Color.GREEN);
					stift.fillOval(standort.getXKon()*50 +10, standort.getYKon()*50+10, 30, 30);
					stift.setColor(alt);
					System.out.println("Müll aufgehoben");
				}
				fitness = fitness +10;
			}
			else
			{
				if(aufzeichnen)
				{
					Color alt = stift.getColor();
					stift.setColor(Color.RED);
					stift.fillOval(standort.getXKon()*50 +18, standort.getYKon()*50+18, 14, 14);
					stift.setColor(alt);
					System.out.println("gebückt");
				}
				fitness = fitness -1;
			}
		}
//		else
//		{
//			umdrehen();
//			if(aufzeichnen)
//				System.out.println("umgedreht");
//		}
	}
	
	public int gibFitness()
	{
		return fitness;
	}
	
	public int gibGesamtFitness()
	{
		return gesamtFitness/zaehler;
	}
	
	public void fitnessZuruecksetzen(){
		gesamtFitness = 0;
		fitness = 0;
	}
	
	public void setzeAufzeichnen(boolean auf)
	{
		aufzeichnen = auf;
	}
	
	public Raum gibRaum()
	{
		return raum;
	}
	
	public BufferedImage gibBild()
	{
		return bild;
	}
	// low inklusiv, high exklusiv
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

	@Override
	public int compareTo(Roboter o) {
		// TODO Auto-generated method stub
		return -gibGesamtFitness() + o.gibGesamtFitness();
	}

}
