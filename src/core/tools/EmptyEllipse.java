package core.tools;

import java.awt.Graphics2D;

/**
 * Narzędzie do rysowania elips niewypełnionych kolorem.
 *
 * @author Dawid Płocki 
 */
public class EmptyEllipse extends AbstractUnReverseable {

	@Override
	public void drawFigure(Graphics2D g) {
		makeCalculations();
		g.drawOval(tX, tY, lenX, lenY);
	}
}
