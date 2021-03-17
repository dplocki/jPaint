package core.tools;

/**
 * Zbiór etykiet do narzędzi.
 *
 * @author Dawid Płocki 
 */
public enum Tools {
	/** Prosta linia łącząca dwa punkty */
	LINE ("Linia"),
	/** Wypełniony kolorem prostokąt */
	RECTANGLE ("Prostokąt"),
	/** Prostokąt niewypełniony kolorem (tylko obwód) */
	EMPTY_RECTANGLE ("Kontur prostokąta"),
	/** Elipsa wypełniona kolorem */
	ELLIPSE ("Elipsa"),
	/** Elipsa niewypełniona kolorem (tylko obwód) */
	EMPTY_ELLIPSE ("Kontur elipsy"),
	/** Umożliwia rysowanie linii o dowwolnym kształcie */
	ANY_SHAPE ("Dowolny kształt");

	/**
	 * Polska etykietka narzędzia
	 */
	public String namePL;

	/**
	 * Konstruktor, przypisuje wartość jej etykietkę z polską nazwą.
	 * 
	 * @param namePL
	 */
	Tools(String namePL) {
		this.namePL = namePL;
	}
	
	/**
	 * Zwraca obiekt narzędzia odpowiadającej podanej wartości tego typu
	 * wyliczeniowego. W przypadku nie przyporządkowania obiektu wartości
	 * zwraca wartość null.  
	 *  
	 * @param toolName	wartość typu wyliczeniowego
	 * @return obiekt narzędzia 
	 */
	public static ToolInterface getTool(Tools toolName) {
		switch(toolName) {
			case LINE: return new Line();
			case RECTANGLE: return new Rectangle();
			case ELLIPSE: return new Ellipse();
			case EMPTY_RECTANGLE: return new EmptyRectangle();
			case EMPTY_ELLIPSE: return new EmptyEllipse();
			case ANY_SHAPE: return new AnyShape();
			default:
				return null;				
		}
	}
	
	/**
	 * Zwraca obiekt narzędzia, reprezentowanego przez wartość tego typu
	 * wyliczeniowego. Bazuje na metodzie statycznej {@link Tools#getTool}
	 *  
	 * @return obiekt narzędzia
	 */
	public ToolInterface getToolsObject() {
		return Tools.getTool(this);
	}

	@Override
	public String toString() {
		return namePL;
	}
}