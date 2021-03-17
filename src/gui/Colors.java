package gui;

/**
 * Wyliczenie domyślnych kolorów, przydatne przy generowaniu menu.
 * Zawiera też polskie etykietki kolorów.
 *
 * @author Dawid Płocki 
 */
public enum Colors {	
	/** Kolor czarny */
	BLACK ("Czarny", java.awt.Color.BLACK),
	/** Kolor jasno szary */
	LIGHT_GRAY ("Jasno szary", java.awt.Color.LIGHT_GRAY),
	/** Kolor szary */
	GRAY ("Szary", java.awt.Color.GRAY),
	/** Kolor ciemno szary */
	DARK_GRAY ("Ciemno szary", java.awt.Color.DARK_GRAY),
	/** Kolor biały */
	WHITE ("Biały", java.awt.Color.WHITE),
	/** Kolor czerwony */
	RED ("Czerwony", java.awt.Color.RED),
	/** Kolor różowy */
	PINK ("Różowy", java.awt.Color.PINK),
	/** Kolor pomarańczowy */
	ORANGE ("Pomarańczowy", java.awt.Color.ORANGE),
	/** Kolor żółty */
	YELLOW ("Żółty", java.awt.Color.YELLOW),
	/** Kolor zielony */
	GREEN ("Zielony", java.awt.Color.GREEN),
	/** Kolor karmazynowy */
	MAGENTA ("Karmazynowy", java.awt.Color.MAGENTA),
	/** Kolor cyjanowy */
	CYAN ("Cyjan", java.awt.Color.CYAN),
	/** Kolor niebieski */
	BLUE ("Niebieski", java.awt.Color.BLUE);
	
	/** Polska etykietka koloru */
	public String namePL;
	/** Wartość koloru */
	public java.awt.Color color;
	
	/**
	 * Getter dla wartości koloru danej etykietki.
	 *  
	 * @return wartość koloru danej etykietki
	 */
	public java.awt.Color getColor() {
		return color;
	}
	
	/**
	 * Konstruktor, przypisuje kolor i polską nazwę etykietce.
	 *  
	 * @param namePL polska nazwa
	 * @param color wartość koloru
	 */
	Colors(String namePL, java.awt.Color color) {
		this.namePL = namePL;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return namePL;
	}
}
