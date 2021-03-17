package gui;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.GrahicsFileType;

/**
 * Obiekt obsługi plików, część GUI, wczytuje i zapisuje do pliku. 
 *
 * @author Dawid Płocki 
 */
public class FileService {
	/**
	 * Główna zmienna singletonu. 
	 */
	static private FileService instance = null;
	
	/**
	 * Getter do singletonu, obiektu obsługi plików.
	 * @return
	 */
	static public FileService getInstance() {
		if (instance == null)
			instance = new FileService();
		return instance; 
	}
	
	/**
	 * Obiekt pliku
	 */
	File file = null;
	/**
	 * Obiekt wyboru plików.
	 */
	JFileChooser fileChooser;
	
	/**
	 * Konstruktor, inicjuje komponent wyboru plików. 
	 */
	private FileService() {
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Mapa bitowa (*.bmp)", "bmp"));
	}

	/**
	 * Użytkownik zapisuje plik nowy, już pod nową nazwą.
	 */
	public void saveAs() {
		int returnVal = fileChooser.showSaveDialog(MainJPaintClass.mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			save();		
		} else
			return;
	}
	
	/**
	 * Użytkownik zapisuje edytowany plik. 
	 */
	public void save() {
		if (file == null) {
			saveAs();
		} else {
			try {
				MainPaintPanel.getInstance().savePicture(file, GrahicsFileType.BMP);			
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MainJPaintClass.mainFrame, "Błąd zapisu:\n" + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Użytkownik chce wczytać plik z obrazkiem. 
	 */
	public void open() {
		int returnVal = fileChooser.showOpenDialog(MainJPaintClass.mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				file = fileChooser.getSelectedFile();
				MainPaintPanel.getInstance().openPicture(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MainJPaintClass.mainFrame, "Błąd odczytu:\n" + e.getLocalizedMessage());
			} 
		}
	}
	
	/**
	 * Metoda wywoływana przy dyrastycznych zmianach, np. utworzenia nowego obrazka. 
	 */
	public void reset() {
		file = null;
	}
}
