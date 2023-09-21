package aschmutz;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wordpair {

	private String imageUrl;

	private String word;
	public Wordpair(String word, String url){
		setWord(word);
		setImageUrl(url);
	}
	public void setImageUrl(String url) {
		if(url == null) throw new RuntimeException("image url must not be null");
			try {
				new URL(url);
			} catch (MalformedURLException e) {
				throw new RuntimeException("image url must not be a valid URL");
			}
	}

	public void setWord(String word) {
		if(word==null) throw new RuntimeException("word must not be null");
		this.word = word;
	}

	public boolean validate(String word) {
		return this.word.equals(word);
	}

}
