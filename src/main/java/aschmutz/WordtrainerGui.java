package aschmutz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * The WordtrainerGui is used as a Frontend that uses a WordtrainerBackend to show the GUI for Wordtraining
 */
public class WordtrainerGui {
	private static int MAX_IMAGE_HEIGHT_AND_WIDTH = 512;
	private WordtrainerBackend trainerToUse;
	private Wordpair currentWordpair;

	/**
	 * Creates a GUI forthe Wordtrainer.
	 * @param trainerToUse The WordtrainerBackend that is used in the Frontend.
	 */
	public WordtrainerGui(WordtrainerBackend trainerToUse){
	changeWordtainerBackend(trainerToUse,true);
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
				boolean invalid= true;
				while(invalid) {
					try {
						imageUrl = new URL(currentWordpair.getImageUrl());
					} catch (MalformedURLException ignored) {
					}
					//Checks if an Image is actualy an Image, by loading it into an ImageObject, and getting its Width
					if (!checkIfImageExists(imageUrl)) { //TODO MBY Bug that it does not wait for image to be loaded
						System.out.println(currentWordpair.getImageUrl());
						//System.out.println(new ImageIcon(currentWordpair.getImageUrl()).getImage().getWidth(null));
						String newURL = JOptionPane.showInputDialog("The Image URL \"" + currentWordpair.getImageUrl() + "\" for the word\"" + currentWordpair.toString() + "\" is outdated. Please enter a new URL for this Image. After that it will be shuffeld back into the System.\n If the Problem persists please close the Program and remove the Entery from the JSON File accouring to the Readme File");
						try{currentWordpair.setImageUrl(newURL);}catch (IllegalArgumentException e){continue;}
					}else
						invalid = false;
				}

				//Loading the Image an scaling it
				BufferedImage unscaledImage = null;
				try {
					unscaledImage = ImageIO.read(imageUrl);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Invalid Image URL in wordpair for Word: "+currentWordpair.toString()+"\nWordpair will be skiped","ERROR Invalid Image URL",JOptionPane.ERROR_MESSAGE);
					continue;
				}
				System.out.println(imageUrl);
				int referenceSize = Math.max(unscaledImage.getHeight(), unscaledImage.getWidth());
				double referenceFactor = (double)MAX_IMAGE_HEIGHT_AND_WIDTH / referenceSize;

				int scaledHeight = (int) (unscaledImage.getHeight()*referenceFactor);
				int scaledWidth = (int) (unscaledImage.getWidth()*referenceFactor);
				BufferedImage scaledImage = new BufferedImage(scaledWidth,scaledHeight,BufferedImage.TYPE_INT_ARGB);
				new AffineTransformOp(AffineTransform.getScaleInstance(referenceFactor,referenceFactor),AffineTransformOp.TYPE_BILINEAR).filter(unscaledImage,scaledImage);
				//Creating the Icon out of the oaded image
				Icon webImage = new ImageIcon(scaledImage);
				String response = "";
				boolean first = true;
				while (!currentWordpair.validate(response)) {
					if(!first) trainerToUse.setStatistics(trainerToUse.getStatistics(StatType.wrong)+1,StatType.wrong);
					response = (String) JOptionPane.showInputDialog(null, (first ? "Guess the Word" : "Wrong\nGuess again"), "Statistics:\tTries: "+trainerToUse.getStatistics(StatType.tries)+"\tCorrect: "+trainerToUse.getStatistics(StatType.correct)+"\tWrong: "+trainerToUse.getStatistics(StatType.wrong), JOptionPane.INFORMATION_MESSAGE, webImage, null, null);
					response = response == null ? "" : response;
					if (response.equalsIgnoreCase("quit")) return true;
					first = false;
				}
				trainerToUse.setStatistics(trainerToUse.getStatistics(StatType.correct)+1,StatType.correct);
			}
		}
	}

	private boolean checkIfImageExists(URL path) {
		ImageIcon ImIc = new ImageIcon(path);
		Thread imageChecker = new Thread(()->{
			while(ImIc.getImageLoadStatus()== MediaTracker.LOADING){
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
			}
		}
		);
		try {
			imageChecker.join();
		} catch (InterruptedException ignored) {}
		//return !(ImIc.getImageLoadStatus() == MediaTracker.ABORTED || ImIc.getImageLoadStatus() == MediaTracker.ERRORED);
		if(ImIc.getImageLoadStatus() != MediaTracker.COMPLETE) return false;
		return ImIc.getImage().getWidth(null) >= -1;
	}

	/**
	 * Changes the WordtrainerBackend that is used for the Wordtraining in the GUI.
	 * Backend changing is Threadsave, so it can be changed during active promting.
	 * Backend will change after correctly guesing the current word. The calling method will not haave to wait for that
	 * @param trainerToUse The new WordtrainerBackend that is to be used by the Gui, as aword Library
	 * @param sychonized If true, the execution will wait until the Worttrainerbackend is changed. Do this if you plan to instantly call it afterward, with new Backend, or for initialising
	 * @throws IllegalArgumentException Throws an IllegalArgumentException if the new Wordtrainer is Null
	 */
	public void changeWordtainerBackend(WordtrainerBackend trainerToUse,boolean sychonized){
		if (trainerToUse==null) throw new IllegalArgumentException("New Wordtrainer cannot be null");
		Thread t = new Thread( ()->{synchronized(this){this.trainerToUse = trainerToUse;}});
		t.start();
		if (sychonized) {
			try {
				t.join();
			} catch (InterruptedException ignore) {}
		}
	}
}
