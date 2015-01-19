package lernleistung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class LadenSpeichern {

	static ObjectOutputStream output;
	static File lastFile;
	
	public static LinkedList<int[]> generationInDNA(LinkedList<Roboter> roboter)
	{
		LinkedList<int[]> DNA = new LinkedList<int[]>();
		for(int i = 0; i<roboter.size(); i++)
		{
			DNA.add(roboter.get(i).gibDNA());
		}
		return DNA;
	}
	
	public static LinkedList<Roboter> DNAinGeneration(LinkedList<int[]> dna)
	{
		LinkedList<Roboter> generation = new LinkedList<Roboter>();
		for(int i = 0; i< dna.size(); i++)
		{
			generation.add(new Roboter(dna.get(i)));
		}
		return generation;
	}
	
	public static String DNAinString(int[] DNA)
	{
		String ergebnis = "";
		for(int i = 0; i < DNA.length; i++)
		{
			ergebnis += DNA[i];
			if(i+1 < DNA.length)
				ergebnis += ",";
		}
		return ergebnis;
	}
	
	public static String GenerationinString(LinkedList<Roboter> gen)
	{
		String ergebnis = "";
		for(int i = 0; i < gen.size(); i++)
		{
			ergebnis += DNAinString(gen.get(i).gibDNA());
			if(i+1 < gen.size())
				ergebnis += ";";
		}
		return ergebnis;
	}
	
	public static void speichern(LinkedList<Roboter> gen, File dateiName)
	{
		try {
			FileOutputStream fos = new FileOutputStream(dateiName,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(GenerationinString(gen));
			writer.newLine();
			writer.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LinkedList<Roboter> laden(File dateiName, int index)
	{
		try
		{
			FileInputStream fis = new FileInputStream(dateiName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			
			for(int i = 1; i< index; i++)
				reader.readLine();
			
			String gen = reader.readLine();
			if(gen ==  null)
			{
				reader.close();
				return null;
			}
//			gen = gen.substring(2,gen.length()-2);
			String[] DNAstrings = gen.split(";");
			
			LinkedList<Roboter> ergebnis = new LinkedList<Roboter>();
			
			for(int i = 0; i < DNAstrings.length; i++)
			{
				String[] DNAteile = DNAstrings[i].split(",");
				int[] DNA = new int[DNAteile.length];
				for(int j = 0; j < DNA.length; j++)
				{
					DNA[j] = Integer.parseInt(DNAteile[j]);
				}
				ergebnis.add(new Roboter(DNA));
			}
			
			reader.close();
			isr.close();
			fis.close();
			return ergebnis;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void erstelleStatistikDatei(File file)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write("Anfangsbevoelkerung;Generationen;Szenarien;Mutationsrate;DurchschnittsFittness;Zeit");
			writer.newLine();
			writer.close();
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void schreibenEinstellung(int anfang, int generation, int szenarien, double rate, File file)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(anfang + ";" + generation+ ";" + szenarien + ";" + rate + ";");
			writer.close();
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void speichernStatistik(LinkedList<Roboter> generation,long delta, File file)
	{
		int summe = 0;
		int i = 0;
		for( ;i<generation.size() && i<20; i++)
		{
			summe = summe + generation.get(i).gibFitness();
		}
		double durchschnitt = summe /i;
		try
		{
			FileOutputStream fos = new FileOutputStream(file,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(Double.toString(durchschnitt));
			writer.write(";" + Long.toString(delta));
			writer.newLine();
			writer.close();
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
