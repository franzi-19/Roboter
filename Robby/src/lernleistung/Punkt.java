package lernleistung;

public class Punkt {
	
	private int xKoordinaten, yKoordinaten;

	public Punkt(int x, int y)
	{
		xKoordinaten = x;
		yKoordinaten = y;
	}
	
	public void setXKon(int x)
	{
		xKoordinaten = x;
	}
	
	public void setYKon(int y)
	{
		yKoordinaten = y;
	}
	
	public int getXKon()
	{
		return xKoordinaten;
	}
	
	public int getYKon()
	{
		return yKoordinaten;
	}
	
	public boolean istGleich(Punkt pkt)
	{
		return getXKon() == pkt.getXKon() && getYKon() == pkt.getYKon();
	}
}
