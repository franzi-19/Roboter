package lernleistung;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class StatusOberflaeche extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea feld;
	private JScrollPane scrollPane;
	
	public StatusOberflaeche()
	{
		super("Status");
		feld = new JTextArea();
		feld.setEditable(false);
		this.setBounds(850, 0, 400, 600); 
		this.setLayout(null);
		feld.setLineWrap(true);
		feld.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)feld.getCaret();
		caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 394, 572);
		scrollPane.add(feld);
		scrollPane.setViewportView(feld);
		this.add(scrollPane);
		this.setResizable(false);
		this.setMaximizedBounds(this.getBounds());
	}
	
	public void zeige()
	{
		this.setVisible(true);
	}
	
	public void schreibe(String text)
	{
		zeige();
		String neu = feld.getText() + text + "\r\n";
		feld.setText(neu);
	}
	
	public void schreibeDNA(Roboter rob)
	{
		int[] dna = rob.gibDNA();
		String text ="DNA:";
		for(int i=0; i<dna.length;i++)
		{
			text = text + " " + dna[i];
		}
		this.schreibe(text);
	}
}
