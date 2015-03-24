package lernleistung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class LadenSpeichern {
	
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
	
	public static LinkedList<Roboter> laden(File dateiName, int index) // index = generation
	{
		try
		{
			FileInputStream fis = new FileInputStream(dateiName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			
			for(int i = 1; i< index; i++)  //sucht die richtige, ausgewählte Generation 
				reader.readLine();
			
			String gen = reader.readLine();
			if(gen ==  null)
			{
				reader.close();
				return null;
			}
			String[] DNAstrings = gen.split(";");  //trennt die DNAs der Roboter von einander
			
			LinkedList<Roboter> ergebnis = new LinkedList<Roboter>();
			
			for(int i = 0; i < DNAstrings.length; i++)
			{
				String[] DNAteile = DNAstrings[i].split(",");  //trennt einzelne DNA - Teile von einander
				int[] DNA = new int[DNAteile.length];
				for(int j = 0; j < DNA.length; j++)
				{
					DNA[j] = Integer.parseInt(DNAteile[j]); //string in int
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
}
	