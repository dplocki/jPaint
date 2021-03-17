package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import core.tools.*;

/**
 * Klasa głównego menu, tworzy go oraz obsługuje jego zdarzenia. 
 *
 * @author Dawid Płocki 
 */
public class MainMenu extends JMenuBar implements UndoChangeListener {
	private static final long serialVersionUID = -4905765460781590696L;
	/** Element menu, jaki powinien być wyszarzany i wznawiany,
	 * wraz ze zmianą możliwości cofnięcia zmian na obrazku */
	private JMenuItem undoButton = null;
		
	/**
	 * Konstruktor, inicjuje menu, przypisuje słuchaczy do zdarzeń.
	 */
	public MainMenu() {
		super();
		add(makeFileMenu());
		add(makeEditMenu());
		
		// -- Menu narzędzie --
		boolean isFirst = true;		
		JMenu menu = new JMenu("Narzędzia");
		menu.setMnemonic('n');
		JMenuItem tmp = null;
		ButtonGroup group = new ButtonGroup();
		for(Tools toolName : Tools.values()) {
			tmp = new MainMenuToolItem(toolName);
			if (isFirst) {
				tmp.setSelected(true);
				MainPaintPanel.getInstance().setToolName(toolName);
				isFirst = false;
			}
			group.add(tmp);
			menu.add(tmp);
		}
		add(menu);
				
		// -- Menu kolorów --
		isFirst = true;
		menu = new JMenu("Kolory");
		menu.setMnemonic('k');
		group = new ButtonGroup();
		for(gui.Colors colorName : gui.Colors.values()) {
			tmp = new MainMenuColorItem(colorName);
			if (isFirst) {
				tmp.setSelected(true);
				MainPaintPanel.getInstance().setColor(colorName.getColor());
				isFirst = false;
			}			
			menu.add(tmp);
			group.add(tmp);
		}
		
		// -- Dowolny kolor --
		menu.add(new JSeparator());
		tmp = new JMenuItem("Dowolny kolor");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newColor = JColorChooser.showDialog(MainJPaintClass.mainFrame, "Wybierz kolor", MainPaintPanel.getInstance().getColor());
				if (newColor != null)
					MainPaintPanel.getInstance().setColor(newColor);
			}
		});
		menu.add(tmp);		
		add(menu);
	}
	
	/**
	 * Metoda tworzy i zwraca obiekt menu "Plik". Wywołuje ją konstruktor.
	 *  
	 * @return menu "Plik"
	 */
	protected JMenu makeFileMenu() {
		JMenuItem tmp;
		JMenu menu = new JMenu("Plik");
		menu.setMnemonic('p');

		// -- Nowy obrazek --
		tmp = new JMenuItem("Nowy");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MainJPaintClass.newPicture()) {
					NewPictureDialog dialog = new NewPictureDialog(MainJPaintClass.mainFrame, true);
					if (dialog.showDialog()) {
						MainPaintPanel.getInstance().newPicture(dialog.getPictureWeight(), dialog.getPictureHeight(), dialog.getPictureColor());
						FileService.getInstance().reset();
						MainJPaintClass.mainFrame.pack();
					}
				}
			}
		});
		tmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menu.add(tmp);
		
		// -- Wczywatnie pliku --
		tmp = new JMenuItem("Otwórz");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileService.getInstance().open();
			}
		});
		tmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menu.add(tmp);
		
		// -- Zapis do pliku --
		tmp = new JMenuItem("Zapisz");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileService.getInstance().save();
			}
		});
		tmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menu.add(tmp);
		
		// -- Zapisz jako -- 
		tmp = new JMenuItem("Zapisz jako ...");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileService.getInstance().saveAs();
			}
		});		
		menu.add(tmp);		
		menu.add(new JSeparator());
		
		// -- Zamknij --
		tmp = new JMenuItem("Zamknij");
		tmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainJPaintClass.endProgram();
			}
		});	
		tmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		menu.add(tmp);
		return menu;
	}
	
	/**
	 * Metoda tworzy i zwraca obiekt menu "Edycja". Wywołuje ją konstruktor.
	 *  
	 * @return menu "Edycja"
	 */
	protected JMenu makeEditMenu() {
		JMenuItem tmp;
		JMenu menu = new JMenu("Edycja");
		menu.setMnemonic('e');
		undoButton = new JMenuItem("Cofnij");
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainPaintPanel.getInstance().undo();
			}
		});
		undoButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		undoButton.setEnabled(false);
		menu.add(undoButton);
		add(menu);
		
		// -- MENU "Wybierz liczbę cofnięć" --
		JMenu subMenu = new JMenu("Liczba cofnięć");
		ButtonGroup group = new ButtonGroup();
		tmp = new MainMenuUndoLevelItem(new Integer(3));
		group.add(tmp);
		tmp.setSelected(true);
		subMenu.add(tmp);
		tmp = new MainMenuUndoLevelItem(new Integer(9));
		group.add(tmp);
		subMenu.add(tmp);		
		tmp = new MainMenuUndoLevelItem(new Integer(12));
		group.add(tmp);
		subMenu.add(tmp);
		menu.add(new JSeparator());
		menu.add(subMenu);
		return menu;
	}
	
	/* (non-Javadoc)
	 * @see gui.UndoChangeListener#setEnabledOfUndo(boolean)
	 */
	public void setEnabledOfUndo(boolean b) {
		undoButton.setEnabled(b);
	}
	
}

/**
 * Klasa bazowa, pod klasy elementu menu.
 * 
 * @param <T>	obiekt jaki przycisk dodatkowo przechowuje
 * @author Dawid Płocki 
 */
abstract class MainMenuItem<T> extends JRadioButtonMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Obiekt jaki dodaktowo ten element menu przetrzymuje. 
	 */
	protected T obj;
	
	/**
	 * Konstruktor, jego główny zadaniem jest ustawić słucha.
	 * @param obj
	 */
	public MainMenuItem(T obj) {
		super(obj.toString());
		this.obj = obj;
		addActionListener(this);
	}
}

/**
 * Klasa elementu menu, która przetrzymuje dodatkowo nazwę narzędzia
 * jakie ma być użyte.
 *
 * @author Dawid Płocki 
 */
class MainMenuToolItem extends MainMenuItem<Tools> {
	/**	*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor
	 * @param toolName nazwa narzędzia przypisanego elementowi menu   
	 */
	public MainMenuToolItem(Tools toolName) {
		super(toolName);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MainPaintPanel.getInstance().setToolName(obj);
	}
}

/**
 * Klasa elementu menu, która przetrzymuje dodatkowo kolor jaki
 * teraz ma być używaty. 
 *
 * @author Dawid Płocki 
 */
class MainMenuColorItem extends MainMenuItem<Colors> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor
	 * @param colorName nazwa koloru przypisana elementowi menu
	 */
	public MainMenuColorItem(gui.Colors colorName) {
		super(colorName);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MainPaintPanel.getInstance().setColor(obj.getColor());		
	}	
}

/**
 * Klasa elementu menu, która przetrzymuje dodatkowo liczbę
 * maksymalnych cofnięć zmian na plikach. 
 *
 * @author Dawid Płocki 
 */
class MainMenuUndoLevelItem extends MainMenuItem<Integer> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor
	 * 
	 * @param undoLevel maksymalna liczba cofnięć zmian na obrazku 
	 */
	public MainMenuUndoLevelItem(Integer undoLevel) {
		super(undoLevel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		MainPaintPanel.getInstance().setMaxUndoDeep(obj.intValue());
	}
}