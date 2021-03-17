package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * Główna klasa GUI, jak i główna, startowa klasa programu.
 * 
 * @author Dawid Płocki
 */
public class MainJPaintClass {
	
	/** Niektóre z obiektów (np. FileChooser) wymagają okienka głównego. */
	public final static JFrame mainFrame = new JFrame("JPaint");  
	
	/**
	 * Funkcja startowa.
	 * 
	 * @param args parametry programu, nie obsługiwane.
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {            	
            	mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            	mainFrame.addWindowListener(new WindowAdapter() {
            		@Override
            		public void windowClosing(WindowEvent winEvt) {
            			MainJPaintClass.endProgram();
            	    }
            	});

            	MainMenu menu = new MainMenu();
            	mainFrame.setJMenuBar(menu);
            	mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
            	mainFrame.getContentPane().setLayout(null);
            	mainFrame.getContentPane().add(new JScrollPane(MainPaintPanel.getInstance()
            			//.addToJFrame()
            			));
            	MainPaintPanel.getInstance().addUndoChangeListener(menu);
            	mainFrame.pack();
            	mainFrame.setVisible(true);
            }
        });
	}
	
	/**
	 * Metoda sprawdza, czy użytkownik na prawdę chce wyłączyć program.
	 */
	public static void endProgram() {
		if (askQuestion("Zakończ program"))
				System.exit(0);
	}
	
	/**
	 * Metoda sprawdza, czy użytkownik na prawdę chce utworzyć nowy obrazek.
	 * @return czy użytkownik zaaprobował akcję
	 */
	public static boolean newPicture() {
		return askQuestion("Utwórz nowy rysunek");
	}
	
	/**
	 * Metoda pomocnicza, wyświetla komunikat z pytaniem. Używana przez metody
	 * {@link #newPicture()} oraz {{@link #endProgram()}.
	 * 
	 * @param title tytuł okienka z pytaniem, także nazwa przycisku potwierdzenia  
	 * @return czy użytkownik zaaprobował akcję
	 */
	protected static boolean askQuestion(String title) {
		Object[] options = {title, "Anuluj"};
		return (!MainPaintPanel.getInstance().isPictureChanged() || JOptionPane.showOptionDialog(
				mainFrame,
			    "Twoja praca nie została zapisana, czy na pewno chcesz ją porzucić?",
			    title,
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[1]
			) == JOptionPane.YES_OPTION);
	}
}
