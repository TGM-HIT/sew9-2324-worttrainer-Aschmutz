package aschmutz;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * The Backend of a Wordtrainer System. This is responsable for Managing the Wordpairs, and the in and output of them. Use a WordtrainerGui object in combination with a Backend to get a full Wordtrainer Game.
 */
public class WordtrainerBackend {

	private List<Wordpair> wordpairs;

	private int correct = 0;

	private int wrong = 0;

	private WordtrainerSaveManager saveManager;
	/**
	 * The Constructor for the Wordtrainer Backend
	 */
	public WordtrainerBackend(){
		wordpairs = new LinkedList<Wordpair>();
	}

	/**
	 * Creates a new instance of the WordtrainerBackend, and uses the given savemanager to initialise
	 * with the values stored on the file of the given path
	 * @param saveManager The Savemanager that is used for the initial load.
	 * @param path The path from where the object will be loaded. It must not be null
	 * @throws IllegalArgumentException Throws an {@link IllegalArgumentException} if the path is null
	 */
	public WordtrainerBackend(WordtrainerSaveManager saveManager, String path ) throws IOException {
		wordpairs = new LinkedList<Wordpair>();
		setSaveManager(saveManager);
		if(path == null) throw new IllegalArgumentException("Cannot load from null, the path cannot be null.");
		load(path);
	}

	/**
	 * Creates a new instance of a WordtrainerBackend with a saveManager already set.
	 * @param saveManager The SaveManager that is to be set.
	 */
	public WordtrainerBackend (WordtrainerSaveManager saveManager){
		wordpairs = new LinkedList<Wordpair>();
		setSaveManager(saveManager);
	}

	/**
	 * Loads the WordtrainerBackend from a given path.
	 * Uses the set {@link WordtrainerSaveManager} to load the WordtrainerBackend Object.
	 * @param path The path from where the object will be loaded.
	 * @throws IllegalArgumentException If the passed Argument for the path is NULL;
	 * @throws IllegalStateException If there is no {@link WordtrainerSaveManager} set, as this specifies the behaviour of the save functionality
	 * @throws IOException If the there are Problems with reading the File or if the file does not exist
	 */
	public void load(String path) throws IOException {
		if(saveManager == null) throw new IllegalStateException("There must be WordtrainerSaveManager set, in order to use the included save and load functionalities.");
		if(path == null) throw new IllegalArgumentException("Cannot load from null");
		WordtrainerBackend temporaryWordtrainerBackend = saveManager.load(path);
		this.correct = temporaryWordtrainerBackend.getStatistics(StatType.correct);
		this.wrong = temporaryWordtrainerBackend.getStatistics(StatType.wrong);
		this.wordpairs.clear();
		this.wordpairs.addAll(temporaryWordtrainerBackend.wordpairs);
	}

	/**
	 * Saves the WordtrainerBackend to a given path.
	 * Uses the set {@link WordtrainerSaveManager} to save the WordtrainerBackend Object.
	 * @param path The path where the Object will be saved.
	 * @throws IllegalArgumentException If the passed Argument for the path is NULL;
	 * @throws IllegalStateException If there is no {@link WordtrainerSaveManager} set, as this specifies the behaviour of the save functionality
	 * @throws IOException If there are Problems when writing the File to Disk
	 */
	public void save(String path) throws IOException {
		if(saveManager == null) throw new IllegalStateException("There must be WordtrainerSaveManager set, in order to use the included save and load functionalities.");
		if(path == null) throw new IllegalArgumentException("Cannot load from null");
		saveManager.save(path,this);
	}

	/**
	 * Sets the {@link WordtrainerSaveManager} that is used in the included Save and load Methodes of the WordtrainerBackend
	 * @param saveManager The saveManager that is to be set.
	 */
	public void setSaveManager(WordtrainerSaveManager saveManager){
		// No Null check is intetnionall to allow for removal of the saveManager
		this.saveManager = saveManager;
	}

	/**
	 * Compares two WordtrainerBackend with each other. Returns true if the statistics
	 * and all stored Wordtrainers are equal and have the same order.
	 * The Savemanager does not account to Equality.
	 * @param o The WordtrainerBackend that is compared
	 * @return If the Object is Equal
	 */
	public boolean equals(Object o){
		if(!(o instanceof WordtrainerBackend)){
			return false;
		}
		WordtrainerBackend wtb = (WordtrainerBackend) o;
		// Comparing the statistics stored inside
		if (wtb.getStatistics(StatType.correct) != this.getStatistics(StatType.correct)) return false;
		if (wtb.getStatistics(StatType.wrong) != this.getStatistics(StatType.wrong)) return false;
		//Compares the wordpairs stored inside
		Wordpair[] wpaWTB = wtb.getWordpairs();
		Wordpair[] wpaTHIS = this.getWordpairs();
		if(wpaWTB.length != wpaTHIS.length) return false;
		for(int i = 0; i < wpaTHIS.length;i++){
			if(!wpaTHIS[i].equals(wpaWTB[i]))return false;
		}

		return true;
	}

	/**
	 * returns a Wordpair with a given ID
	 * @param id The ID of the Wordpair ranges from 0 to length-1
	 * @return The Wordpair at a given ID
	 */
	public Wordpair getWordpair(int id) {
		if(id < 0 || id >=wordpairs.size()) throw new IndexOutOfBoundsException("Wordpair ID must be greater than or equal to 0 and smaller than "+wordpairs.size());
		return wordpairs.get(id);
	}

	/**
	 * Adds a wordpair to the WordtrainerBackend to be used in the GUI
	 * @param wordpair The wordpair that is to be added
	 * @throws IllegalArgumentException if null is passed
	 */
	public void addWordpair(Wordpair wordpair) {
		if(wordpair == null) throw new IllegalArgumentException("Cannot add a NULL Wordpair to Wordtrainer");
		this.wordpairs.add(wordpair);
	}

	/**
	 * returns a statistic value from a given type
	 * @param type the Type of statistic that is to be retrieved
	 * @return the value of the type that is requested
	 * @throws IllegalArgumentException If an unsopported StatType is passed
	 */
	public int getStatistics(StatType type) {
		return switch (type) {
			case correct -> correct;
			case wrong -> wrong;
			case tries -> correct+wrong;
			default -> throw new IllegalArgumentException("Unsupportet StatType given");
		};
	}

	/**
	 * sets the given statistics value. The Values are clamped at a minimum of 0, and only "correct" and "wrong" can be set
	 * @param value the Value that is to be set.  as a Statistic value. It may not be smaller than 0. The value is clamped at 0 as a minimum
	 * @param type the Type of statistic that is to be set. It can only be "correct" or "wrong" as the rest is calculated out of that.
	 * @throws IllegalArgumentException The Stat Type can only be "correct" or "wrong", other wise an IllegalArgumentException will be thrown.
	 */
	public void setStatistics(int value, StatType type) {
		if(value < 0) value = 0;
		switch (type) {
			case correct -> correct = value;
			case wrong -> wrong = value;
			default -> throw new IllegalArgumentException("Unsupportet StatType given for setting");
		}
	}

	/**
	 * Returns all the Wordpairs that are stored in that WordtrainerBackend.
	 * @return The stored Wordpairs as an Array. The individual Wordpairs are not guaranteed to match in referencce.
	 */
	public Wordpair[] getWordpairs(){return wordpairs.toArray(Wordpair[]::new);}

	/**
	 * Removes a Wordpair from the WordtrainerBackend, and Shifftes the IDs accordingliy
	 * @param id the of the Id that is to be deleted
	 * @return The Removed wordpair
	 * @throws IllegalArgumentException Is thrown when the ID that is to be deleted does not exist in the WordtrainerBackend.
	 */
	public Wordpair removeWordpair(int id){
		if(wordpairs.size()==0) throw new IndexOutOfBoundsException("There are no elements currently, nothing to Delete");
		if(id < 0 || id >=wordpairs.size()) throw new IndexOutOfBoundsException("Wordpair ID must be greater than or equal to 0 and smaller than "+wordpairs.size());
		return wordpairs.remove(id);}

}
