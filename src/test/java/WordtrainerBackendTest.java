import aschmutz.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//TODO Add Displaynames (@DisplayName(xyz))"
public class WordtrainerBackendTest {

    @Test
   public void testWordpairHandeling(){
        WordtrainerBackend wtb = new WordtrainerBackend();
        Wordpair wp1 = new Wordpair("word","https://www.tgm.ac.at");
        Wordpair wp2 = new Wordpair("word2","https://www.google.com");
        Wordpair wp3 = new Wordpair("word3","https://elearning.tgm.ac.at");
        wtb.addWordpair(wp1);
        wtb.addWordpair(wp2);
        wtb.addWordpair(wp3);
        assertArrayEquals( new Wordpair[]{wp1, wp2,wp3},wtb.getWordpairs(),"Wordpair List is not as excpected");
        assertEquals(wp3,wtb.getWordpair(2),"Cannot access last Wordpair in list");
        assertEquals(wp2,wtb.getWordpair(1),"Cannot access middle Wordpair in list");
        assertEquals(wp1,wtb.getWordpair(0),"Cannot access first Wordpair in list");
        wtb.removeWordpair(2);
        assertThrows(IndexOutOfBoundsException.class,()->{wtb.getWordpair(2);});
        wtb.removeWordpair(0);
        assertEquals(wp2,wtb.getWordpair(0),"Order of wordpairs did not correctly shift, on deletion of predecessor");
        assertThrows(IndexOutOfBoundsException.class,() -> wtb.removeWordpair(3), "Can remove non-existend element");
        assertThrows(IllegalArgumentException.class,()->wtb.addWordpair(null),"Adding a null wordpair is possible");
    }
    @Test
     public void testStatistics(){
         WordtrainerBackend wtb = new WordtrainerBackend();
         wtb.setStatistics(-10,StatType.wrong);
         assertEquals(0,wtb.getStatistics(StatType.wrong),"Negative values are not Zeroed");
         wtb.setStatistics(5, StatType.correct);
         wtb.setStatistics(15,StatType.wrong);
         assertThrows(IllegalArgumentException.class,()->wtb.setStatistics(10, StatType.tries), "Can set unsettable Type");
         assertEquals(5,wtb.getStatistics(StatType.correct),"Amount for Statistic:Correct is wrong");
         assertEquals(15,wtb.getStatistics(StatType.wrong),"Amount for Statistic:Wrong is wrong");
         assertEquals(20,wtb.getStatistics(StatType.tries),"Amount for Statistic:Tries is wrong");
    }
    @Test
    public void testSaving(){
        WordtrainerBackend wtb = new WordtrainerBackend();
        Wordpair wp1 = new Wordpair("word","https://www.tgm.ac.at");
        Wordpair wp2 = new Wordpair("word2","https://www.google.com");
        Wordpair wp3 = new Wordpair("word3","https://elearning.tgm.ac.at");
        wtb.addWordpair(wp1);
        wtb.addWordpair(wp2);
        wtb.addWordpair(wp3);
        WordtrainerJSONSaveManager wjsm = new WordtrainerJSONSaveManager();
        wtb.setSaveManager(wjsm);
        wtb.save("testSave.json");
        WordtrainerBackend wtbLoaded = new WordtrainerBackend(wjsm,"testSave.json");
        assertEquals(wtb,wtbLoaded,"Loaded WordtrainerBackend differentiates from Saved WordtrainerBackend");
        WordtrainerBackend wtbLoadedWithMethod = new WordtrainerBackend(wjsm);
        wtbLoadedWithMethod.load("testSave.json");
        assertEquals(wtb,wtbLoadedWithMethod,"Loading With \"load\" method does not correctly create a Equal Object");
    }
}
