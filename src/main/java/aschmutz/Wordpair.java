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
		if(url == null) throw new IllegalArgumentException("image url must not be null");
		try {
			if(url.length()==0) throw new IllegalArgumentException("image url must not be empty");
			new URL(url);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("image url must not be a valid URL");
		}
		this.imageUrl = url;
	}
	public String getImageUrl(){return this.imageUrl;}

	public void setWord(String word) {
		if(word==null) throw new IllegalArgumentException("word must not be null");
		if(word.length()==0) throw new IllegalArgumentException("image url must not be empty");
		this.word = word;
	}

	public boolean validate(String word) {
		return this.word.equals(word);
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof Wordpair)) throw new IllegalArgumentException("Object must be comparable to Wordpair");
		Wordpair wp = (Wordpair) o;
		if(!this.word.equals(word)) return false;
		if(!this.imageUrl.equals(wp.imageUrl)) return false;
		return true;
	}
	public String toString(){
		return word;
	}
}
