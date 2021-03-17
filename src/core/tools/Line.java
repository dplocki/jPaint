package core.tools;
import java.awt.Graphics2D;

/**
 * Narzędzie do rysowania linii. 
 *
 * @author Dawid Płocki 
 */
public class Line extends AbstractTool {
	/* (non-Javadoc)
	 * @see tools.AbstractTool#drawFigure(java.awt.Graphics2D)
	 */
	@Override
	public void drawFigure(Graphics2D g) {
		g.drawLine(x1, y1, x2, y2);
	}
}
