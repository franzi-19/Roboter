package lernleistung;

public class Raum {

	private int[][] koordinaten;
	
	public Raum()
	{
		koordinaten = new int[10][10];
		verteileMuell();
	}
	
	private Raum(int[][] raum)
	{
		koordinaten = raum;
	}
	
	public void verteileMuell()
	{
		for(int i = 0; i < 10; i ++)
		{
			for(int j = 0; j < 10; j ++)
			{
				int zufall = myRandom(0,2);
				koordinaten[i][j] = zufall;
			}
		}
	}
	
	
	public boolean istMuell(Punkt punkt)
	{
		if(koordinaten[punkt.getXKon()][punkt.getYKon()] == 1)
			return true;
		else
			return false;
	}
	
	public boolean istWand(Punkt punkt)
	{
		if(punkt.getXKon()>-1 && punkt.getXKon()<10 && punkt.getYKon()>-1 && punkt.getYKon()<10)
			return false;
		else 
			return true;
	}
	
	public void muellAufsammeln(Punkt punkt)
	{
		koordinaten[punkt.getXKon()][punkt.getYKon()] = 0;
	}
	
	public Raum kopiere()
	{
		int[][] neuerRaum = new int[10][10];
		for(int i = 0; i<10; i++)
		{
			for(int k = 0; k<10; k++)
			{
				neuerRaum[i][k] = koordinaten[i][k];
			}
		}
		return new Raum(neuerRaum);
	}
	
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
