package lernleistung;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Evolution {
	
	public Zustandssammlung sammlung;
	public LinkedList<Roboter> generation;
	//public LinkedList<LinkedList<Roboter>> generationSammlung;
	File dateiName;
	//        + JFileChoose benutzen  Videos? SkypeLink 19.22.4
	
	public Evolution()
	{
		sammlung = new Zustandssammlung();
		//generationSammlung = new LinkedList<LinkedList<Roboter>>(); 
	}
	
	public static void main(String[] args) 
	{
		//Evolution evo = new Evolution();
		//evo.evolution(200, 50, 100);
	}
	
	public int[][] crossover(int[] frau, int[] mann)
	{
		int[] kind1 = new int[sammlung.gibLaenge()];
		int[] kind2 = new int[sammlung.gibLaenge()];
		int zufall = myRandom(0, sammlung.gibLaenge());
		System.arraycopy(frau, 0, kind1, 0, zufall);
		System.arraycopy(mann, zufall, kind1, zufall, sammlung.gibLaenge()-zufall);
		System.arraycopy(mann, 0, kind2, 0, zufall);
		System.arraycopy(frau, zufall, kind2, zufall, sammlung.gibLaenge()-zufall);
		return new int[][] {kind1,kind2};
	}
	
	public void mutation(int[] roboter, int prozent)
	{
		int wiederholungen = sammlung.gibLaenge() * (prozent /100);
		for(int i = 0; i< wiederholungen; i++)
		{
			int zufallPlatz = myRandom(0, sammlung.gibLaenge()+1);
			int zufallGen = myRandom(0, 6);
			roboter[zufallPlatz] = zufallGen;
		}
	}
	
	public void erstelleGeneration(int groesse)
	{
		generation = new LinkedList<Roboter>();
		for(int i = 0; i<groesse; i++)
		{
			generation.add(new Roboter());
		}
		LadenSpeichern.speichern(generation, dateiName);
		//generationSammlung.add(generation);
	}
	
	public void evolution(int generationGroesse, int generationen, int szenarien)
	{
		erstelleGeneration(generationGroesse);
		lebenSimulieren(generationGroesse, szenarien);
		System.out.println("Anfang");
		generationAusgeben();
		for(int i = 0; i < generationen; i++)
		{
			lebenSimulieren(generationGroesse, szenarien);
			LinkedList<Roboter> neueGeneration = new LinkedList<Roboter>();
			List<Roboter> besten = generation.subList(0, generation.size()/2);
			for(int j = 0; j < besten.size(); j++)
			{
				LinkedList<Roboter> partnerListe = new LinkedList<Roboter>(besten);
				partnerListe.remove(besten.get(j));
				Collections.shuffle(partnerListe); //Partner mischen
				int[][] kinder = crossover(besten.get(j).gibDNA(), partnerListe.getFirst().gibDNA());
				Roboter kind1 = new Roboter(kinder[0]);
				Roboter kind2 = new Roboter(kinder[1]);
				neueGeneration.add(kind1);
				neueGeneration.add(kind2);
			}
			generation = neueGeneration;
			LadenSpeichern.speichern(generation, dateiName);
			//generationSammlung.add(neueGeneration);
		}
		lebenSimulieren(generationGroesse, szenarien);
		System.out.println("Ende");
		generationAusgeben();
	}

	private void lebenSimulieren(int generationGroesse, int szenarien) {
		for(int j = 0; j < generationGroesse; j++)
		{
			generation.get(j).fitnessZuruecksetzen();
			for(int k = 0; k<szenarien; k++)
			{
				generation.get(j).leben();
			}
		}
		Collections.sort(generation); //liste wird sortiert
	}
	
	public LinkedList<Roboter> gibGeneration(int generation)
	{
		return LadenSpeichern.laden(dateiName, generation);
		//return generationSammlung.get(generation);
	}
	
	public void setzteFile(File file)
	{
		dateiName = file;
	}
	
	public void generationAusgeben(){
		for(int i = 0; i < generation.size(); i++)
			System.out.println(generation.get(i).gibGesamtFitness());
	}
	
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
}
