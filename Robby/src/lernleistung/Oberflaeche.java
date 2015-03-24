package lernleistung;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;


public class Oberflaeche  extends JFrame{
	
	private static final long serialVersionUID = 1L;  
	private JTextField textFieldAnfBevoelkerung;
	private JTextField textFieldGenerationen;
	private JTextField textFieldSzenarien;
	private JTextField textFieldAnzeigeGeneration;
	private JTextField textFieldMutationsWahrscheinlichkeit;
	private SpielfeldAnzeige anzeige;
	private Evolution evo;
	private StatusOberflaeche status;

	public Oberflaeche()
	{
		super();
		anzeige = new SpielfeldAnzeige();
		evo = new Evolution();
		this.setBounds(0, 0, 807, 540);
		this.setLayout(null);
		createComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		status = new StatusOberflaeche();
		this.setMaximizedBounds(this.getBounds());
	}
	
	public static void main(String[] args) 
	{
		Oberflaeche ob = new Oberflaeche();
		ob.setVisible(true);
	}
	
	private void createComponents()  
	{
		textFieldAnfBevoelkerung = new JTextField();
		textFieldAnfBevoelkerung.setBounds(14, 20, 116, 22);
		this.add(textFieldAnfBevoelkerung);
		textFieldAnfBevoelkerung.setColumns(10);
		
		textFieldGenerationen = new JTextField();
		textFieldGenerationen.setBounds(14, 80, 116, 22);
		this.add(textFieldGenerationen);
		textFieldGenerationen.setColumns(10);
		
		textFieldSzenarien = new JTextField();
		textFieldSzenarien.setBounds(14, 140, 116, 22);
		this.add(textFieldSzenarien);
		textFieldSzenarien.setColumns(10);
		
		textFieldAnzeigeGeneration = new JTextField();
		textFieldAnzeigeGeneration.setBounds(159, 20, 116, 22);
		this.add(textFieldAnzeigeGeneration);
		textFieldAnzeigeGeneration.setColumns(10);
		
		textFieldMutationsWahrscheinlichkeit = new JTextField();
		textFieldMutationsWahrscheinlichkeit.setBounds(14, 200, 116, 22);
		this.add(textFieldMutationsWahrscheinlichkeit);
		textFieldMutationsWahrscheinlichkeit.setColumns(10);
		
		JLabel Anfangsbevoelkerung = new JLabel("Anfangsbevoelkerung");
		Anfangsbevoelkerung.setBounds(10, 0, 130, 22);
		this.add(Anfangsbevoelkerung);
		
		JLabel Generationen = new JLabel("Generationen");
		Generationen.setBounds(14, 60, 116, 22);
		this.add(Generationen);
		
		JLabel Szenarien = new JLabel("Szenarien");
		Szenarien.setBounds(14, 120, 116, 22);
		this.add(Szenarien);
		
		JLabel GenerationAnzeige = new JLabel("Generation anzeigen");
		GenerationAnzeige.setBounds(159, 0, 116, 22);
		this.add(GenerationAnzeige);
		
		JLabel MutationsWahrscheinlichkeit = new JLabel("Mutationswahrscheinlichkeit");
		MutationsWahrscheinlichkeit.setBounds(0, 180, 150, 22);
		this.add(MutationsWahrscheinlichkeit);
		
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String anfBev = textFieldAnfBevoelkerung.getText();
				String gen = textFieldGenerationen.getText();
				String szen = textFieldSzenarien.getText();
				String mut = textFieldMutationsWahrscheinlichkeit.getText();
				String datei = "Bev " + anfBev + ", Gen " + gen + ", Szen " + szen + ", Mut " + mut + ".txt";
				File pref = new File("C:\\Users\\Franziska\\Documents\\Schule\\Informatik\\Lernleistung\\Generationen\\Daten\\"+datei);
				JFileChooser fc = new JFileChooser();
				fc.setSelectedFile(pref);
				
				int state = fc.showSaveDialog(null);
				 if ( state == JFileChooser.APPROVE_OPTION )
				 {
					  evo = new Evolution();
				      File file = fc.getSelectedFile();
				      evo.setzeFile(file);
				      status.zeige();
				      status.schreibe("Evolution startet");
				      try
				      {
				    	  evo.evolution(Integer.parseInt(textFieldAnfBevoelkerung.getText()), Integer.parseInt(textFieldGenerationen.getText()),Integer.parseInt(textFieldSzenarien.getText()), Double.parseDouble(textFieldMutationsWahrscheinlichkeit.getText()));
				    	  status.schreibe("Evolution beendet");
				      }
				      catch(NumberFormatException n)
				      {
				    	  status.schreibe("Evolution konnte doch nicht gestartet werden. Fehlerhafte Eingabe!");
				      }
				 }
			}
		});
		
		start.setBounds(22,300, 100, 30);
		this.add(start);
		
		JButton laden = new JButton("Generationen laden");
		laden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				File pref = new File("C:\\Users\\Franziska\\Documents\\Schule\\Informatik\\Lernleistung\\Generationen\\Daten");
				if(!pref.exists())
					pref = null;
				JFileChooser fc = new JFileChooser(pref);
				int state = fc.showOpenDialog(null);
				if ( state == JFileChooser.APPROVE_OPTION )
				 {
				      File file = fc.getSelectedFile();
				      String datei = file.getName();
				      datei = datei.replaceAll(" ", ""); //Löscht  alle Leerzeichen
				      for(int i = datei.length() - 1; i >= 0; i--)
				      { //Löscht die Dateiendung (Sucht den ersten Punkt von hinten)
				    	  if(datei.charAt(i) == '.')
				    	  {
				    		  datei = datei.substring(0,i);
				    		  break;
				    	  }
				      }
				      String[] props = datei.split(",");
				      for(String p : props)
				      {
				    	  String number = p.replaceAll("[^0123456789\\.]", ""); //Löscht alle nicht-Ziffern außer Punkte (also zum Beispiel Buchstaben)
				    	  String text = p.replaceAll("\\d|\\.", ""); //Löscht alle Ziffern und Punkte
				    	  switch(text){
				    	  case "Bev": textFieldAnfBevoelkerung.setText(number); break;
				    	  case "Gen": textFieldGenerationen.setText(number); break;
				    	  case "Szen": textFieldSzenarien.setText(number); break;
				    	  case "Mut": textFieldMutationsWahrscheinlichkeit.setText(number); break;
				    	  default: status.schreibe("Kann nicht erkennen: " + text);
				    	  }
				      }
				      evo.setzeFile(file);
				      status.zeige();
				      status.schreibe("Generationen wurden geladen");
				 }
			}
		});
		laden.setBounds(0,350,145, 30);
		this.add(laden);
		
		
		JButton anzeigen = new JButton("Anzeigen");
		anzeigen.addActionListener(new ActionListener() {
			boolean aufrufen = true;
			
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int hilfe = Integer.parseInt(textFieldAnzeigeGeneration.getText());
					if(hilfe<0)
						status.schreibe("negative Generationen existieren nicht");
					else
					{
						LinkedList<Roboter> gen = evo.gibGeneration(hilfe);
						if(gen == null)
						{
							status.schreibe("Generation nicht vorhanden");
						}
						else
						{
							if(aufrufen)
							{
								status.schreibe("Beschreibung: ");
								String text = "Auf der rechten Seite wird nun der Laufweg des ausgewählten Roboters angezeigt. Mit den Knöpfen 'weiter', 'zurück' und 'zum Ende' kann der Weg schrittweise oder ganz gezeigt werden. Dabei stellen die grünen Quadrate die Müllhaufen, das schwarze X die Startposition und das graue Quadrat die aktuelle Position des Roboters da. Wenn nun der Laufweg angezeigt wird, verdeutlichen die schwarzen Striche den zurückgelegten Weg. Falls der Weg blau ist, bewegt sich der Roboter zufällig. Bei den grünen Kreisen hat der Roboter Müll aufgehoben und die Roten bedeuten, dass sich der Roboter unnötig gebückt hat. Wenn der Roboter gegen eine Wand gelaufen ist, dann erscheit ein Strich Richtung Wand.";
								status.schreibe(text);
								aufrufen = false;
							}
							Roboter rob = gen.getFirst();
							anzeige.setzeRoboter(rob);
							status.zeige();
							status.schreibe("Fitness: "+ anzeige.gibFitness());
							status.schreibeDNA(rob);
						}
					}
				}
				catch(NumberFormatException n)
				{
					status.schreibe("Fehlerhafte Eingabe!");
				}
			}
		});
		anzeigen.setBounds(170,300, 100, 30);
		this.add(anzeigen);
		
		JButton weiter = new JButton("weiter");
		weiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!anzeige.weiter())
					status.schreibe("Der Schritt konnte nicht angezeigt werden. (Kein Roboter geladen oder alle Schritte ausgeführt)");
			}
		});
		weiter.setBounds(170,380, 100, 30);
		this.add(weiter);
		
		JButton zurueck = new JButton("zurück");
		zurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!anzeige.zurueck())
					status.schreibe("Der Schritt konnte nicht angezeigt werden. (Kein Roboter geladen oder noch kein Schritt ausgeführt)");
			}
		});
		zurueck.setBounds(170,420, 100, 30);
		this.add(zurueck);
		
		JButton ende = new JButton("zum Ende");
		ende.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				anzeige.vorspulen();
			}
		});
		ende.setBounds(170,460, 100, 30);
		this.add(ende);
		
		JSeparator strich = new JSeparator(JSeparator.VERTICAL);
		strich.setForeground(Color.BLACK);
		strich.setBounds(145, 0, 1, 501);
		this.add(strich);
		
		this.add(anzeige);
		anzeige.setBounds(290, 0, 501, 501);
	}
}
