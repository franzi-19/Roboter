package lernleistung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class AutoStatistik {
	private static final File statistikFile = new File("C:\\Users\\Franziska\\Documents\\Schule\\Informatik\\Lernleistung\\Generationen\\Automatik\\tabelle.txt");
	private static final String evoDir = "C:\\Users\\Franziska\\Documents\\Schule\\Informatik\\Lernleistung\\Generationen\\Automatik\\";
	private static final int anzahlEvos=1;
	
	private static final int minGenSz=100, maxGenSz=500; //Generationsgroesse
	private static final double minMut=0.1d,maxMut=3d; //Mutationsrate
	private static final int minGen=700, maxGen=2000; //Generationsanzahl
	private static final int minSzen=50, maxSzen=500; //Szenarien
	
	public static void main(String[] args){
		int offset=0;
		try{
			InputStream oin = new FileInputStream("autooffset");
			offset = Integer.parseInt(new BufferedReader(new InputStreamReader(oin)).readLine());
			oin.close();
		}catch(Exception e){
			System.out.println("Could not read old offset:");
			e.printStackTrace(System.out);
		}
		
		if(!statistikFile.exists())
  	  	{
  		  LadenSpeichern.erstelleStatistikDatei(statistikFile);
  	  	}
		
		Random r = new Random();
		for (int i =0; i < anzahlEvos; i++){
			Evolution evo = new Evolution();
			evo.setzeFile(new File(evoDir + (i+1+offset) +".txt"));
			evo.setzeStatistik(statistikFile);
			double mut = r.nextDouble() * (maxMut - minMut) + minMut;
			int genSz = r.nextInt((maxGenSz - minGenSz) / 2) * 2 + minGenSz;
			int gen = r.nextInt(maxGen - minGen) + minGen;
			int szen = r.nextInt(maxSzen - minSzen) + minSzen;
			System.out.print("Nr. " + i + ", GenSize " + genSz + ", Gens " + gen + ", Szens " + szen + ", Mut " + mut +"...");
			evo.evolution(genSz, gen, szen, mut);
			System.out.println(" Finished.");
		}
		
		try{
			OutputStream oout =  new FileOutputStream("autooffset",false);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(oout));
			wr.write(Integer.toString(anzahlEvos + offset) + "\r\n");
			wr.flush();
			oout.close();
		} catch(Exception e){
			System.out.println("Could not write new offset:");
			e.printStackTrace(System.out);
		}
		
		System.out.println("Finished all tasks.");
	}
}
