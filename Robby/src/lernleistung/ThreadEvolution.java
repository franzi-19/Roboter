package lernleistung;

public class ThreadEvolution implements Runnable{
	
	private boolean weiterAusfuehren;
	
	
	public synchronized boolean sollWeiterAusfuehren()
	{
		return weiterAusfuehren;
	}
	
	public synchronized void stopp()
	{
		weiterAusfuehren = false;
	}
	
	public synchronized void weiter()
	{
		weiterAusfuehren = true;
	}

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		
	}
}
