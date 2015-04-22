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
		if(punkt.getXKon() <0 || punkt.getYKon()<0)
		{
			return false;
		}
		else if(punkt.getXKon() >9 || punkt.getYKon()>9)
		{
			return false;
		}
		return koordinaten[punkt.getXKon()][punkt.getYKon()] == 1;
	}
	
	public boolean istWand(Punkt punkt)
	{
		return !(punkt.getXKon()>-1 && punkt.getXKon()<10 && punkt.getYKon()>-1 && punkt.getYKon()<10);
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
	
	private static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
