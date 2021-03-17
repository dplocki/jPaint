package core.tools;

import java.awt.Graphics2D;

/**
 * Narzędzie do rysowania niewypełnionych kolorem prostokątów.
 *
 * @author Dawid Płocki 
 */
public class EmptyRectangle extends AbstractUnReverseable {

	@Override
	public void drawFigure(Graphics2D g) {
		makeCalculations();
		g.drawRect(tX, tY, lenX, lenY);
	}
}
