package core.tools;

/**
 * Niektóre z metod rysujących nie mogą rysować "do tyłu", przyjmują jako
 * parametry współrzędne punktu startewego (lewy górny "róg") oraz szerokość
 * i wysokość obiektu. Aby użytkownik mógł używać ich również do malowania
 * "od dołu", należy wcześniej współrzędne przeliczyć, co realizuje ta właśnie
 * klasa.
 * 
 * @author Dawid Płocki 
 */
abstract public class AbstractUnReverseable extends AbstractTool {
	/** Szerokość rysowanego obiektu */
	int lenX;
	/** Wysokość rysowanego obiektu */
	int lenY;
	/** Zmienna pomocnicza, przechowująca współrzędną x górego lewego rógu */
	int tX;
	/** Zmienna pomocnicza, przechowująca współrzędną y górego lewego rógu */
	int tY;
	
	/**
	 * Przelicza parametry rysunku, tak jakby obiekt był rysowany "od góry",
	 * nie "od dołu". 
	 */
	protected void makeCalculations() {
		lenX = Math.abs(x2 - x1);
		lenY = Math.abs(y2 - y1);
		
		tX = x1;
		tY = y1;
		
		if (x2 < x1) tX -= lenX;
		if (y2 < y1) tY -= lenY;
	}
}
