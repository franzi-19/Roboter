package lernleistung;

import java.util.LinkedList;

public class Zustandssammlung {
	
	public LinkedList<Integer> zustaende;
	
	public Zustandssammlung()
	{
		zustaende = new LinkedList<Integer>();
		erstelleZustaende();
		gibLaenge();
	}
 
	public void erstelleZustaende()
	{
		erstelleZustandsliste(0, 1);
	}
	
	public void erstelleZustandsliste(int aktuellerZustand, int Position) //vorne=1; rechts=10; hinten=100; links=1000; unten=10000
	{

		for(int i = 1; i<4; i++)
		{
			int neu = aktuellerZustand;
			neu = neu + Position*i;
			if(Position == 10000)
			{
				zustaende.add(neu);

			}
			else
				erstelleZustandsliste(neu, Position*10);
		}
		
	}
		
	public int gibZustandsNummer(int zustand)
	{
		int nummer = zustaende.indexOf(zustand);
		return nummer;
	}
	
	public int gibLaenge()
	{
		return zustaende.size();
	}
}
