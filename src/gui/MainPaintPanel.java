package gui;

import java.awt.Color;
import javax.swing.JPanel;
import core.PaintPanel;
import core.tools.Tools;

/**
 * Klasa rozszerza klasę PainPanel o 
 *
 * @author Dawid Płocki 
 */
public class MainPaintPanel extends PaintPanel {
	private static final long serialVersionUID = 1L;
	
	/** Zmienna potrzebna do singletonu */
	private static MainPaintPanel instance = null;  
	/** Domyślna szerokość okienka po uruchomieniu programu */
	private static int width = 500;
	/** Domyślna wyskość okienka po uruchomieniu programu */
	private static int height = 600;
	/** Domyślny kolor tłą okienku po uruchomieniu programu */
	private static Color bgColor = Color.WHITE;
		
	/**
	 * Głowna metoda singletonu.
	 * 
	 * @return jedyna instancja tego obiektu 
	 */
	public static MainPaintPanel getInstance() {
		if (instance == null)
			instance = new MainPaintPanel(width, height, bgColor);
		return instance;
	}
		
	/**
	 * Przeciążony konstruktor obiektu PaintPanel - w celu ukrycia go. 
	 * 
	 * @param width szerokość obrazka
	 * @param height wysokość obrazka
	 * @param bgColor kolo tła obrazka
	 */
	private MainPaintPanel(int width, int height, Color bgColor) {
		super(width, height, bgColor);
	}
	
	/**
	 * Wstawia do obiektu narzędzie na podstawie jego nazwy.
	 * 
	 * @param toolName nazwa narzędzia.
	 */
	public void setToolName(Tools toolName) {
		setTool(Tools.getTool(toolName));
	}

	/**
	 * Metoda pomocnicza: wstawienie do dodatkowego JPanelu właściwego
	 * obiektu (czyli tego) zapobiega jego rozszerzaniu się wraz
	 * z obiektem JFrame. Dodatkowo umożliwa opakowanie w JScroolPane. 
	 * 
	 * @return element  JPanel zawierajacy obiekt PaintPanel gotowy do wstawienia.
	 */
	public JPanel addToJFrame() {
		JPanel tmp = new JPanel();
		tmp.add(this);
    	return tmp;
	}

	/** Słuchacz zmian możliwość cofnięcia zmian na obrazku */
	protected UndoChangeListener listener = null;
	
	/**
	 * Dodaje nowego słuchacza zmian możliwości cofnięcia.
	 * 
	 * @param listener słuchacz
	 */
	public void addUndoChangeListener(UndoChangeListener listener) {
		this.listener = listener; // i tak tylko jednego przewidziałem...
	}
	
	/**
	 * Przesyła wszystkim słuchaczą informację o zmianie możliwości
	 * cofnięcia zmian na obrazku.
	 * 
	 * @param b czy można dokonać cofnięcia
	 */
	protected void setEnabledOfUndo(boolean b) {
		if (listener != null)
			listener.setEnabledOfUndo(b);
	}
	
	@Override
	protected void setPictureChanged(boolean isPictureChanged) {
		super.setPictureChanged(isPictureChanged);
		setEnabledOfUndo(isPictureChanged);
	}

	@Override
	public boolean undo() {
		boolean res = super.undo();
		setEnabledOfUndo(res);
		return res;
	}
}

/**
 * Interface dla słuchacza zmiany możliwości wykonania cofnięcia ostatnich
 * zmian na obrazku.
 *
 * @author Dawid Płocki 
 */
interface UndoChangeListener {
	/**
	 * Nastąpiła zmiana w możliwości wykonania cofnięcia.
	 * 
	 * @param b	czy użytkownik może dokonać cofnięcia
	 */
	void setEnabledOfUndo(boolean b);
}