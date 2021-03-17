package core.tools;
import java.awt.Graphics2D;

/**
 * Klasa bazowa dla narzędzi do rysowania.
 *
 * @author Dawid Płocki 
 */
public abstract class AbstractTool implements ToolInterface {

	/**
	 * Współrzędne punktu początkowego i końcowego obiektu
	 * rysowanego przez narzędzie
	 */
	protected int x1, x2, y1, y2;

	/* (non-Javadoc)
	 * @see tools.Tools#drawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void drawing(int x, int y, Graphics2D g) {
		x2 = x;
		y2 = y;
		drawFigure(g);
	}

	/* (non-Javadoc)
	 * @see tools.Tools#endDrawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void endDrawing(int x, int y, Graphics2D g) {
		drawing(x, y, g);
		x1 = x2 = y1 = y2 = -1; 
	}

	/* (non-Javadoc)
	 * @see tools.Tools#startDrawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void startDrawing(int x, int y, Graphics2D g) {
		x1 = x2 = x;
		y1 = y2 = y;		
	}
	
	/* (non-Javadoc)
	 * @see core.tools.ToolInterface#repeatPaint(java.awt.Graphics2D)
	 */
	@Override
	public void repeatPaint(Graphics2D g) {
		drawFigure(g);
	}
	
	/**
	 * Metoda rysuje danym narzędziem.
	 * 
	 * @param g kontekst graficzny na jakim zostać obiekt narysowany
	 */
	abstract public void drawFigure(Graphics2D g);
}
