package core.tools;
import java.awt.Graphics2D;

/**
 * Narzędzie do rysowania prostokątów, wypełnionych kolorem.
 *
 * @author Dawid Płocki 
 */
public class Rectangle extends AbstractUnReverseable {

	@Override
	public void drawFigure(Graphics2D g) {
		makeCalculations();
		g.fillRect(tX, tY, lenX, lenY);
	}
}
