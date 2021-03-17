package core.tools;

import java.awt.Graphics2D;

/**
 * Interface dla narzędzi rysujących, wykorzystywany przez klasę PaintPanel. 
 *
 * @author Dawid Płocki 
 */
public interface ToolInterface {
	/**
	 * Użytkownik rozpoczyna rysowanie danym narzędziem. Podany kontekst po
	 * jakim ma zostać narysowany obiekt przy pomocy tego narzędzia jest
	 * zwykle tylko tym wyświetlanym użytkownikowi.
	 * 
	 * @param x	współrzędna punktu startu
	 * @param y współrzędna punktu startu
	 * @param g kontekst graficzny, na jakim ma zostać wyświetlony początek rysowania.
	 */
	void startDrawing(int x, int y, Graphics2D g);
	
	/**
	 * Użytkownik rozpoczął rysowanie, ale jeszcze go nie skończył. Ten etap
	 * wyświetla mu co jak będzie wyglądał aktualny efekt, wciąż jeszcze nie 
	 * zapisany na stałe.  
	 * 
	 * @param x	współrzędna aktualnego punktu rysowania 
	 * @param y współrzędna aktualnego punktu rysowania
	 * @param g kontekst graficzny, na jakim wyświetlany jest stan operacji
	 */
	void drawing(int x, int y, Graphics2D g);
	
	/**
	 * Wyświetlenie aktualnego stanu rysowania danym narzędziem, nie zbędne
	 * przy odświerzeniu kontekstu "chwilowego", stąd też nie ma współrzędnych
	 * do wyświetlania.
	 *  
	 * @param g kontekst na jakim ma zostać odrysowany stan aktualny narzędzia.
	 */
	void repeatPaint(Graphics2D g);
	
	/**
	 * Użytkownik zaczończył rysowanie narzędziem. Efekt zostanie zapisany
	 * na kontekscie bufora ("na stałe").
	 * 
	 * @param x	współrzędna końcowgo punktu rysowania
	 * @param y współrzędna końcowgo punktu rysowania
	 * @param g kontekst graficzny, na jakim ma zostać narysowany efekt końcowy.
	 */
	void endDrawing(int x, int y, Graphics2D g);
}
