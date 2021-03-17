package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 * Klasa obsługująca bufor na rysunek.  
 *
 * @author Dawid Płocki 
 */
public class PictureBuffer {
	/** Maksymalna liczba cofnięć +1 (dla optymalizacji) */
	protected int maxUndoDeep = 4;

	/** Przechowywane obrazki */
	protected LinkedList<BufferedImage> changeBuffer;
	
	/**
	 * Konstruktor
	 * 
	 * @param width  szerokość obrazka
	 * @param height wysokość obrazka
	 */
	public PictureBuffer(int width, int height, Color bgColor) {
		BufferedImage picture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D pg = (Graphics2D)picture.getGraphics();
		pg.setBackground(bgColor);
		pg.clearRect(0, 0, width, height);
		setPicture(picture);
	}
	
	/**
	 * Getter dla bufora obrazka, 
	 * 
	 * @return
	 */
	protected BufferedImage getPicture() {
		return changeBuffer.getLast();
	}

	/**
	 * Setter dla bufora na obrazek, wykorzystywana przy wczytywaniu
	 * z pliku (metoda openPicture), jak również w konstruktorze tej
	 * klasy. Metoda czyści poprzednie wersje obrazków.
	 *  
	 * @param image	obrazek jaki teraz jest bazowym
	 */
	protected void setPicture(BufferedImage image) {
		changeBuffer = new LinkedList<BufferedImage>();
		changeBuffer.add(image);
	}
	
	/**
	 * Pobiera kontekst graficzny obrazka, aby można było coś na nim
	 * narysować.
	 * 
	 * @return kontekst graficzny
	 */
	public Graphics2D getGraphics() {
		BufferedImage source = getPicture();
		// BufferedImage.clone jest ukryty
		BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());  
		img.getGraphics().drawImage(source, 0, 0, null);
		changeBuffer.add(img);
		
		if (changeBuffer.size() > maxUndoDeep)
			changeBuffer.removeFirst();
		return (Graphics2D)getPicture().getGraphics();
	}
	
	/**
	 * Odrysowuje przechowywany obraz na zadanym kontekście.
	 *  
	 * @param g	kontekst na jakim obraz ma zostać namalowany
	 */
	void paint(Graphics2D g) {
		g.drawImage(getPicture(), null, 0, 0);	
	}

	/**
	 * Cofnięcie ostatniej operacji.
	 * 
	 * @return czy możliwe jest kolejne cofnięcie. 
	 */
	boolean undo() {
		if (changeBuffer.size() > 1)
			changeBuffer.removeLast();

		return (changeBuffer.size() > 1);
	}

	/**
	 * Getter dla maksymlanej liczby cofnięć zmiań na rysunku.
	 * 
	 * @return aktualna liczba maksymalnych cofnięć 
	 */
	public int getMaxUndoDeep() {
		return maxUndoDeep -1;
	}

	/**
	 * Setter dla maksymalnej liczby cofnięć zmiań na rysunku.
	 * 
	 * @param maxUndoDeep nowa maksymalna liczba cofnięć
	 */
	public void setMaxUndoDeep(int maxUndoDeep) {
		if (maxUndoDeep >= 3) { 
			while(changeBuffer.size() > maxUndoDeep +1)
				changeBuffer.removeFirst();
			this.maxUndoDeep = maxUndoDeep +1;
		}
	}	
	
	/**
	 * Zapis aktualnego obrazka do pliku.
	 * 
	 * @param file	plik do jakiego dane są zapisywane
	 * @param fileType	format pliku graficznego
	 * @throws IOException	błąd przy zapisie
	 */
	void savePicture(File file, GrahicsFileType fileType) throws IOException {
		if (!ImageIO.write(getPicture(), fileType.toString(), file))
			throw new IOException("Nieznany problem przy zapisie do pliku"); 
	}
	
	/**
	 * Wczytanie obrazka z podanego pliku.
	 * 
	 * @param file obiekt pliku z jakiego zostaną wczytane dane.
	 * @return wymiary nowo wczytanego obrazka.
	 * @throws IOException w razie problemów z operfacają odczytu
	 */
	public Dimension openPicture(File file) throws IOException {
		BufferedImage tmp = ImageIO.read(file);
		if (tmp == null)
			throw new IOException("Nie prawidłowy format pliku");
		setPicture(tmp);
		tmp = getPicture();		
		return new Dimension(tmp.getWidth(), tmp.getHeight());
	}
}