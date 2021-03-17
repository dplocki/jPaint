package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import core.tools.ToolInterface;

/**
 * Klasa panelu, po jakim można rysować.
 * 
 * @author Dawid Płocki
 */
public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -1474539629743288981L;

	/** Obiekt przechowujący oraz obsługujący bufory na rysunki */
	protected PictureBuffer picture = null;
	/** Aktualnie używane narzędzie do rysowania */
	protected ToolInterface tool = null;
	/** Aktualnie używany kolor przy rysowaniu */
	protected Color color = Color.BLACK;
	/** Zmienna określa, czy obrazek od wczytania, czy inicjacji został zmieniony */
	protected boolean isPictureChanged = false;

	/**
	 * Konstruktor 
	 *
	 * @param width	 szerokość pola rysowniczego
	 * @param height wysokość pola rysowniczego
	 */
	public PaintPanel(int width, int height, Color bgColor) {
		addMouseListener(this);
		addMouseMotionListener(this);
		newPicture(width, height, bgColor);
	}	
	
	/**
	 * Metoda tworzy nowy obrazek. Może zmienić się rozmiar, jak i kolor tła.
	 * Wszelkie zmiany w dotychczasowym obrazku przepadają.
	 *  
	 * @param width szerokość nowego obrazu
	 * @param height wysokość nowego obrazu
	 * @param bgColor kolor tła nowego obrazu
	 */
	public void newPicture(int width, int height, Color bgColor) {
		setSize(width, height);
		picture = new PictureBuffer(width, height, bgColor);
		setPictureChanged(false);
		repaint();
	}
	
	/**
	 * Delegat gettera dla maksymalnej liczby cofnięć.
	 * 
	 * @return maksymalna liczba "cofnięć"
	 */
	public int getMaxUndoDeep() {
		return picture.getMaxUndoDeep();
	}

	/**
	 * Delegat setter dla maksymalnej liczby cofnięć.
	 * 
	 * @param maxUndoDeep maksymalna liczba cofnięć
	 */
	public void setMaxUndoDeep(int maxUndoDeep) {
		picture.setMaxUndoDeep(maxUndoDeep);
	}

	/**
	 * Getter dla zmiennej określającej zmiany na obrazku.
	 * 
	 * @return czy obrazek został zmieniony
	 */
	public boolean isPictureChanged() {
		return isPictureChanged;
	}

	/**
	 * Setter dla zmiennej określającej zmiany na obrazku.
	 * 
	 * @param isPictureChanged
	 */
	protected void setPictureChanged(boolean isPictureChanged) {
		this.isPictureChanged = isPictureChanged; 
	}
	
	/**
	 * Getter narzędzia rysowania.
	 * 
	 * @return	narzędzie używane przez ten PaintPanel
	 */
	public ToolInterface getTool() {
		return tool;
	}

	/**
	 * Setter dla narzędzia rysowania.
	 * 
	 * @param tool	narzędzie rysujące
	 */
	public void setTool(ToolInterface tool) {
		if (tool != null) {
			Color tmp = getColor(); 
			this.tool = tool;
			setColor(tmp);
		}
	}
	
	/**
	 * Setter dla koloru rysowania.
	 *  
	 * @param color	kolor
	 */
	public void setColor(Color color) {
		if (color != null)
			this.color = color;
	}
	
	/**
	 * Getter dla koloru rysowania
	 * 
	 * @return kolor w jakim aktualnie rysuje ten PainPanel
	 */
	public Color getColor() {
		return color; 
	}
	
	/**
	 * Zapisuje narysowany obrazek do pliku
	 * 
	 * @param file plik przeznaczony do zapisu
	 * @param fileType format pliku do jakiego obrazek zostanie zapisany
	 * 
	 * @throws IOException w razie problemów z operacją zapisu
	 */
	public void savePicture(File file, GrahicsFileType fileType) throws IOException {
		picture.savePicture(file, fileType);
		setPictureChanged(false);
	}
	
	/**
	 * Cofnięcie ostatniej zmiany na obrazie
	 */
	public boolean undo() {
		boolean res = picture.undo();
		repaint();
		return res;
	}

	/**
	 * Otwarcie pliku graficznego i wczytanie jego zawartości.
	 * 
	 * @param	file
	 * @throws	IOException
	 */
	public void openPicture(File file) throws IOException {
		Dimension dim = picture.openPicture(file);		
		setSize(dim);
		setPictureChanged(false);
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
		picture.paint((Graphics2D)g);	
		tool.repeatPaint((Graphics2D)g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Graphics2D g = (Graphics2D)getGraphics();
			g.setColor(color);
			tool.startDrawing(e.getX(), e.getY(), g);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Graphics2D g = picture.getGraphics();
			g.setColor(color);
			tool.endDrawing(e.getX(), e.getY(), g);
			setPictureChanged(true);
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
			Graphics2D g = (Graphics2D)getGraphics();
			g.setColor(color);
			tool.drawing(e.getX(), e.getY(), g);
			repaint();
		}
	}

	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		setSize(d.width, d.height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setPreferredSize(getSize());
		setMinimumSize(getSize());
		setMaximumSize(getSize());		
	}	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		/* ignorujemy */
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* ignorujemy */
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/* ignorujemy */		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/* ignorujemy */		
	}
}
