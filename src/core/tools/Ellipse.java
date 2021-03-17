package core.tools;

import java.awt.Graphics2D;

/**
 * Narzędzie do rysowania elips i okręgów.
 *
 * @author Dawid Płocki 
 */
public class Ellipse extends AbstractUnReverseable {
	@Override
	public void drawFigure(Graphics2D g) {
		makeCalculations();
		g.fillOval(tX, tY, lenX, lenY);
	}
}
