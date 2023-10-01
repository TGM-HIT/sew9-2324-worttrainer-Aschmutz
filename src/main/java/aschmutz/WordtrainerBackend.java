package aschmutz;

public class WordtrainerBackend {

	private String Wordpairs;

	private int correct = 0;

	private int wrong = 0;

	public Wordpair getWordpair() {
		return null;
	}

	public void addWordpair(int wordpair) {

	}

	public int getStatistics(statType type) {
		return 0;
	}
	public void setStatistics(int value, statType type) {

	}
	public Wordpair[] getWordpairs(){return null;}
	public Wordpair removeWordpair(int id){return null;}

	public static WordtrainerBackend loadWordtrainerBackend(String path){
		return null;
	}
}
