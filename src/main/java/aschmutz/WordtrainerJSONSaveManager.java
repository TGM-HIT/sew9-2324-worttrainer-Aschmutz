package aschmutz;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Saves and Loads {@link WordtrainerBackend WordtrainerBackends} using JSON Files.
 */
public class WordtrainerJSONSaveManager implements WordtrainerSaveManager{
    /**
     * This serves as a helper class to be able to easily Map a {@link Wordpair}
     * to a JSON file without modifying the original class
     */
    private static class savableWordpair implements Serializable{
        public String word;
        public String imageUrl;
    }

    /**
     * This serves as a helper class to be able to easily Map a {@link WordtrainerBackend}
     * to a JSON file without modifying the original class
     */
    private static class savableWordtrainerBackend implements Serializable {
        @Serial
        private static final long serialVersionUID = 312354L;
        public savableWordpair[] wordpairs;
        public int correct;
        public int wrong;


    }
    /**
     * Loads a {@link WordtrainerBackend} from a JSON file and Returns it
     * @param path The path where the file will be read from
     * @return The constructed WordtrainerBackend Object
     * @throws IOException If the file can not be read.
     */
    @Override
    public WordtrainerBackend load(String path) throws IOException{
        ObjectMapper om = new ObjectMapper();

        Scanner scanner = new Scanner(new File(path));
        String readJSON = scanner.next();
        System.out.println(readJSON);
        savableWordtrainerBackend swtb = om.readValue(readJSON, savableWordtrainerBackend.class);
        WordtrainerBackend wtb = new WordtrainerBackend();
        wtb.setStatistics(swtb.correct,StatType.correct);
        wtb.setStatistics(swtb.wrong,StatType.wrong);
        for(savableWordpair swp : swtb.wordpairs){
            wtb.addWordpair(new Wordpair(swp.word,swp.imageUrl));
        }
        return wtb;
    }

    /**
     * Saves a {@link WordtrainerBackend} into a JSON file
     * @param path The Path where the file will be stored
     * @param wordtrainerBackend The WordtrainerBackend what will be stored
     * @throws IOException If there are Problems when saving the file to disk.
     */
    @Override
    public void save(String path, WordtrainerBackend wordtrainerBackend) throws IOException {
        ObjectMapper om = new ObjectMapper();
        savableWordtrainerBackend swtb = new savableWordtrainerBackend();
        swtb.correct = wordtrainerBackend.getStatistics(StatType.correct);
        swtb.wrong = wordtrainerBackend.getStatistics(StatType.wrong);

        Wordpair[] wordpairs = wordtrainerBackend.getWordpairs();
        swtb.wordpairs = new savableWordpair[wordpairs.length];
        for(int i = 0; i<wordpairs.length;i++){
            swtb.wordpairs[i] = new savableWordpair();
            swtb.wordpairs[i].imageUrl = wordpairs[i].getImageUrl();
            swtb.wordpairs[i].word = wordpairs[i].toString();
        }

        om.writeValue(new File(path),swtb);

    }
}
