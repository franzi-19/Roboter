package lernleistung;

import java.util.LinkedList;
import java.util.Stack;

public class Roboter  implements Comparable<Roboter>    //wie extends, um es sortierbar zu machen
{
	
	private int ausrichtung;
	private Punkt standort; 
	private Zustandssammlung sammlung;
	private Raum raum;
	private Raum ursprungsraum;
	private int zaehler;
	private int[] dna;
	private int fitness;
	private int gesamtFitness;
	private LinkedList<String> laufweg;
	private boolean aufzeichnen;
	
	public Roboter()
	{
		sammlung = new Zustandssammlung();
		fitness = 0;
		standort = new Punkt(myRandom(0, 10),myRandom(0, 10));
		ausrichtung = 0;
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
	
	
	public void geheEinenSchritt()
	{
		Punkt neu = KonVorne();
		if(raum.istWand(neu))
		{
			fitness -= 5;
			if(aufzeichnen)
			{
				laufweg.add("Wand;" + standort.getXKon()+ " "+ standort.getYKon() + ";" + neu.getXKon()+ " "+ neu.getYKon());
				//stift.fillOval(standort.getXKon()*50 +10, standort.getYKon()*50+10, 30, 30); rot
			}
		}
		else
		{
			if(aufzeichnen)
				laufweg.add("Schritt;" + standort.getXKon()+ " "+ standort.getYKon() + ";" + neu.getXKon()+ " " + neu.getYKon());

			standort = neu;
		}
	}
	
	public void geheZufaellig() 
	{
		if(aufzeichnen)
		{
			laufweg.add("Anfang Zufall");
			//stift.setColor(Color.BLUE);
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
			{
				geheEinenSchritt();
			}
			zufall = myRandom(0, 5);
			if(zufall >2)
				wiederholen = false;
		}
		if(aufzeichnen)
		{
			laufweg.add("Ende Zufall");
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
	
	public void leben() //1=links drehen; 2=rechts drehen; 3/6=einen Schritt; 4=zufällig gehen; 5=müll aufheben
	{
		zaehler++;
		if(aufzeichnen)
		{
			laufweg = new LinkedList<String>();
			laufweg.add("Start;" + standort.getXKon() + " " + standort.getYKon());
		}
		fitness = 0;
		raum = new Raum();
		ursprungsraum = raum.kopiere();
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
			geheEinenSchritt();
		}
		else if(action == 2)
		{
			dreheRechts();
			geheEinenSchritt();
		}
		else if(action == 3 || action == 6)
		{
			geheEinenSchritt();
		}
		else if(action == 4)
		{
			geheZufaellig();
		}
		else if(action == 5)
		{
			if(raum.istMuell(standort))
			{
				raum.muellAufsammeln(standort);
				if(aufzeichnen)
				{
					laufweg.add("Muell;" + standort.getXKon() + " " + standort.getYKon());
				}
				fitness = fitness +10;
			}
			else
			{
				if(aufzeichnen)
				{
					laufweg.add("gebueckt;" + standort.getXKon() + " " + standort.getYKon());
				}
				fitness = fitness -1;
			}
		}
//		
	}
	
	public int gibFitness()
	{
		return fitness;
	}
	
	public int gibGesamtFitness()
	{
		return gesamtFitness/zaehler;
	}
	
	public void fitnessZuruecksetzen()
	{
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
	
	public Raum gibUrsprungsraum()
	{
		return ursprungsraum;
	}
	
	public LinkedList<String> gibLaufweg()
	{
		return laufweg;
	}
	
	// low inklusiv, high exklusiv
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

	@Override
	public int compareTo(Roboter rob) {
		// TODO Auto-generated method stub
		return rob.gibGesamtFitness()-gibGesamtFitness();
	}

}
