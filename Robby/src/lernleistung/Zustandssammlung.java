package lernleistung;

import java.util.LinkedList;

public class Zustandssammlung {
	
	private LinkedList<Integer> zustaende;
	
	public Zustandssammlung()
	{
		zustaende = new LinkedList<Integer>();
		erstelleZustaende();
		gibLaenge();
	}
 
	private void erstelleZustaende()
	{
		erstelleZustandsliste(0, 1);
	}
	
	private void erstelleZustandsliste(int aktuellerZustand, int position)
	{ //vorne=1; rechts=10; hinten=100; links=1000; unten=10000

		for(int i = 1; i<4; i++)
		{
			int neu = aktuellerZustand;
			neu = neu + position*i;
			if(position == 10000)
			{
				zustaende.add(neu);

			}
			else
				erstelleZustandsliste(neu, position*10);
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
