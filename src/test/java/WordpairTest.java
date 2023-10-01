import aschmutz.Wordpair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordpairTest {
    @Test
    public void testUrl(){
        Wordpair wp = new Wordpair("word","https://www.google.com");
        assertThrows(RuntimeException.class,()->{wp.setImageUrl("");},"EMPTY URL IS INVALIDLY ACCEPTED");
        assertThrows(RuntimeException.class,()->{wp.setImageUrl(null);},"NUll IS INVALIDLY ACCEPTED");
        assertThrows(RuntimeException.class,()->{wp.setImageUrl("google(dot)com");},"INVALID URL IS INVALIDLY ACCEPTED");
    }
    @Test
    public void testValidate(){
        Wordpair wp = new Wordpair("word","https://www.google.com");
        assertTrue(wp.validate("word"));
        assertFalse(wp.validate("notWord"));
    }
    @Test
    public void testWord(){
        Wordpair wp = new Wordpair("word","https://www.google.com");
        assertThrows(RuntimeException.class,()->{wp.setWord("");},"EMPTY WORD IS INVALIDLY ACCEPTED");
        assertThrows(RuntimeException.class,()->{wp.setWord(null);},"NUll IS INVALIDLY ACCEPTED");
    }
    @Test
    public void testEquals(){
        Wordpair wp1 = new Wordpair("word","https://www.google.com");
        Wordpair wp2 = new Wordpair("word","https://www.google.com");
        Wordpair wp3 = new Wordpair("word1","https://www.google.com");
        Wordpair wp4 = new Wordpair("word","https://www.youtube.com");
        assertEquals(wp1, wp2, "Equal worpairs are not Equal");
        assertNotEquals(wp2, wp3, "unequal words are not detected");
        assertNotEquals(wp2, wp4, "unequal URLs are not detected");
        assertThrows(IllegalArgumentException.class,()->wp1.equals("word:https://www.google.com"),"Tries to Compare to Objects of different types");
    }
}
