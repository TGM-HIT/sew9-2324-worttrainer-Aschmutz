package aschmutz;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Stores a wordpair, containing a Word and a URL poninting to an image, that is descriptive of the Word.
 * The wordpair is intendet to be used with the WordtrainerBackend.
 */
public class Wordpair {

	private String imageUrl;

	private String word;

	/**
	 * Creates a new Wordpair that stores a Word and its Descriptive Image
	 * @param word the Word that it will store
	 * @param url The URL of an Immage. It must be a full domain starting with "http(s)://"
	 */
	public Wordpair(String word, String url){
		setWord(word);
		setImageUrl(url);
	}

	/**
	 * Sets the Image URL for the Wordpair
	 * @param url The stirng o of an URL that will be used as the image
	 * @throws IllegalArgumentException Throws an IllegalArgument Exception if the given URL is not convertable to a java URL Object / is not a full domain
	 *
	 */
	public void setImageUrl(String url) {
		if(url == null) throw new IllegalArgumentException("image url must not be null");
		try {
			if(url.length()==0) throw new IllegalArgumentException("image url must not be empty");
			new URL(url);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("image url must not be a valid URL");
		}
		this.imageUrl = url;
	}

	/**
	 * Returns the URL pointing to the Descriptive image of the Wordpair
	 * @return the URP pointing to the Descriptive image of the Wordpair
	 */
	public String getImageUrl(){return this.imageUrl;}

	/**
	 * Sets the word that will be used in the Wordpair, and that will be checked against in the validate method
	 * @param word The word that will be used in the Wordpair. It must not be Empty or Null
	 * @throws IllegalArgumentException When the wod is null, or an Empty string, a IllegalArgumentExcepption will be thrown
	 */
	public void setWord(String word) {
		if(word==null) throw new IllegalArgumentException("word must not be null");
		if(word.length()==0) throw new IllegalArgumentException("image url must not be empty");
		this.word = word;
	}

	/**
	 * Validates the word that is given as a parameter in context with the Word, set in the  constructor or setWord method.
	 * @param word The word that will be checkeed
	 * @return if the word is a valid equal to the word contained in the wordpair
	 */
	public boolean validate(String word) {
		return this.word.equals(word);
	}

	/**
	 * Checks if a Wordpair is Equal to the current one. Equality depends on the String used in the word and the image URL not bein the same.
	 * @param o the object that is to be compaired against.
	 * @return If the given wordpair is euqal to this, according to the conditions mentioned above
	 */
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Wordpair)) return false;
		Wordpair wp = (Wordpair) o;
		if(!this.word.equals(wp.word)) return false;
		if(!this.imageUrl.equals(wp.imageUrl)) return false;
		return true;
	}

	/**
	 * Returns a string representation of the Wordpair, with the stringrepresentation being the Word
	 * It is not intended to Compair against the Word dirrectly, please use the Validate method, to acommodate for eventual changes in behaviour of different implementations
	 * @return The word stored in the wordpair
	 */
	public String toString(){
		return word;
	}
}
