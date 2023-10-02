package aschmutz;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
//TODO add statistic Updates and Display;
/**
 * The WordtrainerGui is used as a Frontend that uses a WordtrainerBackend to show the GUI for Wordtraining
 */
public class WordtrainerGui {
	private WordtrainerBackend trainerToUse;
	private Wordpair currentWordpair;

	/**
	 * Creates a GUI forthe Wordtrainer.
	 * @param trainerToUse The WordtrainerBackend that is used in the Frontend.
	 */
	public WordtrainerGui(WordtrainerBackend trainerToUse){
	changeWordtainerBackend(trainerToUse);
}

	/**
	 * A Method presenting the GUI for the Wordtrainer
	 * This shows multiple Wordpairs.
	 * This is the way to Start the Frontend of the Wordtrainer. If propageted correctly it will not need any further input.
	 * @return if the WordtrainerBackend is valid
	 */
	public boolean showWordpromt() {
		while(true) {
			synchronized (this){
				if (trainerToUse.getWordpairs().length == 0) {
					JOptionPane.showMessageDialog(null, "There are no Wordpairs in this Wordtrainer", "Invalid Wordtrainer", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				currentWordpair = trainerToUse.getWordpair((int) (Math.random() * trainerToUse.getWordpairs().length));
				URL imageUrl = null;
				try {
					imageUrl = new URL(currentWordpair.getImageUrl());
				} catch (MalformedURLException ignored) {
				}
				Icon webImage = new ImageIcon(imageUrl);
				String response = "";
				boolean first = true;
				while (!currentWordpair.validate(response)) {
					response = (String) JOptionPane.showInputDialog(null, first ? "Guess the Word" : "Wrong\nGuess again", "Worttrainer", JOptionPane.INFORMATION_MESSAGE, webImage, null, null);
					response = response == null ? "" : response;
					if (response.equalsIgnoreCase("quit")) return true;
					first = false;
				}
			}
		}
	}

	/**
	 * Changes the WordtrainerBackend that is used for the Wordtraining in the GUI.
	 * Backend changing is Threadsave, so it can be changed during active promting.
	 * Backend will change after correctly guesing the current word. The calling method will not haave to wait for that
	 * @param trainerToUse The new WordtrainerBackend that is to be used by the Gui, as aword Library
	 * @throws IllegalArgumentException Throws an IllegalArgumentException if the new Wordtrainer is Null
	 */
	public synchronized void changeWordtainerBackend(WordtrainerBackend trainerToUse){
		if (trainerToUse==null) throw new IllegalArgumentException("New Wordtrainer cannot be null");
		new Thread(()->this.trainerToUse = trainerToUse).start();
	}
}
