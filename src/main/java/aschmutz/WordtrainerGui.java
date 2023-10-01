package aschmutz;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class WordtrainerGui {
	private WordtrainerBackend trainerToUse;
	private Wordpair currentWordpair;
	public WordtrainerGui(WordtrainerBackend trainerToUse){
	changeWordtainerBackend(trainerToUse);
}

	/**
	 * A Method presenting the GUI for the Wordtrainer
	 * This shows the GUI for every
	 * @return if the WordtrainerBackend is valid
	 */
	public boolean showWordpromt() {
		while(true) {
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
				response = response == null ? "":response;
				if (response.equalsIgnoreCase("quit")) return true;
				first = false;
			}
		}
	}

	public void changeWordtainerBackend(WordtrainerBackend trainerToUse){
		if (trainerToUse==null) throw new IllegalArgumentException("New Wordtrainer cannot be null");
		this.trainerToUse = trainerToUse;
	}
}
