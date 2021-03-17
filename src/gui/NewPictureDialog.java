package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Klasa okienka modalnego, wyświetlanego kiedy użytkownik chce utworzyć nowy obrazek.
 *
 * @author Dawid Płocki 
 */
public class NewPictureDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	/** Pole wyboru szerokości */
	protected JSpinner weightSpinner;
	/** Pole wyboru wysokości */
	protected JSpinner heightSpinner;
	
	/** Przycisk wyboru koloru tła */
	protected JButton buttonColor;
	/** Przycisk "OK" */
	protected JButton buttonOK;
	/** Przycisk "Anuluj" */
	protected JButton buttonCancel;
	
	/** Kolor tła nowego obrazka */
	protected Color pictureColor = Color.WHITE;
	/** Wysokość nowego obrazka */
	protected int pictureHeight = 0;
	/** Szerokość nowego obrazka */
	protected int pictureWeight = 0;
	/** Czy użytkownik wybrał i zatwierdził wartości */
	protected boolean isChoose = false;

	/**
	 * Konstruktor okienka.
	 * 
	 * @param owner	
	 * @param modal
	 */
	public NewPictureDialog(JFrame owner, boolean modal) {
	    super(owner, modal);
	    
	    setSize(300, 150);
	    setResizable(false);
	    setLocationRelativeTo(owner);
	    
	    setLayout(new GridLayout(0, 2, 5, 5));	    
	    add(new JLabel("Szerokość: "));
	    weightSpinner = new JSpinner(new SpinnerNumberModel(640, 1, 2000, 10));
	    add(weightSpinner);
	    add(new JLabel("Wysokość: "));
	    heightSpinner = new JSpinner(new SpinnerNumberModel(480, 1, 2000, 10)); 
	    add(heightSpinner);
	    add(new JLabel("Kolor tła: "));
    
	    buttonColor = new JButton("...");
	    buttonColor.addActionListener(this);
	    buttonColor.setBackground(pictureColor);
	    add(buttonColor);
	    
	    buttonOK = new JButton("OK");
	    buttonOK.addActionListener(this);
	    add(buttonOK);
	 
	    buttonCancel = new JButton("Anuluj");
	    buttonCancel.addActionListener(this);
	    add(buttonCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == buttonColor) {
			 Color newColor = JColorChooser.showDialog(this, "Wybierz kolor", MainPaintPanel.getInstance().getColor());
				if (newColor != null) {
					pictureColor = newColor;
					buttonColor.setBackground(pictureColor);
				}
		} else {
			if (e.getSource() == buttonOK) {
				isChoose = true;
				setPictureHeight(((Integer)heightSpinner.getValue()).intValue());
				setPictureWeight(((Integer)weightSpinner.getValue()).intValue());
			}
			this.dispose();	
		}
		return;
	}
	
	/**
	 * Wyświetla okienko na ekranie.
	 * 
	 * @return czy użytkownik zatwierdził wybór wartości
	 */
	public boolean showDialog() {
		this.setVisible(true);
		return isChoose();
	}
	
	/**
	 * Getter dla koloru tła obrazka.
	 * 
	 * @return kolor tła obrazka
	 */
	public Color getPictureColor() {
		return pictureColor;
	}

	/**
	 * Setter dla koloru tła obrazka.
	 * 
	 * @param color kolor tła obrazka
	 */
	public void setPictureColor(Color color) {
		this.pictureColor = color;
	}

	/**
	 * Getter dla wysokości nowego obrazka.
	 * 
	 * @return wysokość nowego obrazka
	 */
	public int getPictureHeight() {
		return pictureHeight;
	}

	/**
	 * Setter dla wysokości nowego obrazka.
	 * 
	 * @param height wysokość nowego obrazka
	 */
	public void setPictureHeight(int height) {
		this.pictureHeight = height;
	}

	/**
	 * Getter dla szerokości nowego obrazka.
	 * 
	 * @return szerokość nowego obrazka
	 */
	public int getPictureWeight() {
		return pictureWeight;
	}

	/**
	 * Setter dla szerokości nowego obrazka.
	 * 
	 * @param weight szerokość nowego obrazka
	 */
	public void setPictureWeight(int weight) {
		this.pictureWeight = weight;
	}

	/**
	 * Getter dla zmiennej sprawdzającej czy zmiany zostały zatwierdzone przez użytkownika.
	 * 
	 * @return czy użytkownik zatwierdził zmiany
	 */
	public boolean isChoose() {
		return isChoose;
	}

	/**
	 * Setter dla zmiennej sprawdzającej czy zmiany zostały zatwierdzone przez użytkownika.
	 * 
	 * @param isChoose czy zatwierdzono zmiany
	 */
	protected void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}	
}

