package aschmutz;

import java.util.LinkedList;
import java.util.List;

public class WordtrainerBackend {

	private List<Wordpair> wordpairs;

	private int correct = 0;

	private int wrong = 0;
	public WordtrainerBackend(){
		wordpairs = new LinkedList<Wordpair>();
	}
	public Wordpair getWordpair(int id) {
		if(id < 0 || id >=wordpairs.size()) throw new IndexOutOfBoundsException("Wordpair ID must be greater than or equal to 0 and smaller than "+wordpairs.size());
		return wordpairs.get(id);
	}

	public void addWordpair(Wordpair wordpair) {
		if(wordpair == null) throw new IllegalArgumentException("Cannot add a NULL Wordpair to Wordtrainer");
		this.wordpairs.add(wordpair);
	}

	public int getStatistics(StatType type) {
		return switch (type) {
			case correct -> correct;
			case wrong -> wrong;
			case tries -> correct+wrong;
			default -> throw new IllegalArgumentException("Unsupportet StatType given");
		};
	}
	public void setStatistics(int value, StatType type) {
		if(value < 0) value = 0;
		switch (type) {
			case correct -> correct = value;
			case wrong -> wrong = value;
			default -> throw new IllegalArgumentException("Unsupportet StatType given for setting");
		}
	}
	public Wordpair[] getWordpairs(){return wordpairs.toArray(Wordpair[]::new);}
	public Wordpair removeWordpair(int id){
		if(wordpairs.size()==0) throw new IndexOutOfBoundsException("There are no elements currently, nothing to Delete");
		if(id < 0 || id >=wordpairs.size()) throw new IndexOutOfBoundsException("Wordpair ID must be greater than or equal to 0 and smaller than "+wordpairs.size());
		return wordpairs.remove(id);}

}
