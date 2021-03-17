package core.tools;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Narzędzie do rysowania ciągłych o dowolnym kształcie.
 *
 * @author Dawid Płocki 
 */
public class AnyShape implements ToolInterface {
	/**
	 * Własna wewnętrzna, implementacja struktury punkt.
	 * Pracuje tylko na zmiennych typu int. 
	 *
	 * @author Dawid Płocki 
	 */
	class Point {
		/** Współrzędna punktu */
		public int x;
		/** Współrzędna punktu */
		public int y;
		
		/**
		 * Konstruktor 
		 * 
		 * @param x współrzędna punktu
		 * @param y współrzędna punktu
		 */
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	/** Lista punktów jakie zostały przez użytkownika wybrane */
	LinkedList<Point> list = null; 
	
	/* (non-Javadoc)
	 * @see core.tools.ToolInterface#drawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void drawing(int x, int y, Graphics2D g) {
		list.addLast(new Point(x, y));
		repeatPaint(g);
	}

	/* (non-Javadoc)
	 * @see core.tools.ToolInterface#endDrawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void endDrawing(int x, int y, Graphics2D g) {
		drawing(x, y, g);
		list = null;
	}

	/* (non-Javadoc)
	 * @see core.tools.ToolInterface#repeatPaint(java.awt.Graphics2D)
	 */
	@Override
	public void repeatPaint(Graphics2D g) {
		if (list != null && list.size() > 1) {
			Point prev = list.getFirst();
			for(Point point : list) {
				g.drawLine(prev.x, prev.y, point.x, point.y);
				prev = point;
			}
		}
	}

	/* (non-Javadoc)
	 * @see core.tools.ToolInterface#startDrawing(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void startDrawing(int x, int y, Graphics2D g) {
		list = new LinkedList<Point>();
		drawing(x, y, g);
	}
}
