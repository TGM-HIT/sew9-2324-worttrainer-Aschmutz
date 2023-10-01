import aschmutz.Wordpair;
import aschmutz.WordtrainerBackend;
import aschmutz.WordtrainerGui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WortrainerGuiTest {
    @Test
    public void testChangeWord(){
        WordtrainerGui wg = new WordtrainerGui(new WordtrainerBackend());
        assertThrows(IllegalArgumentException.class, ()->wg.changeWordtainerBackend(null),"New \"NULL\" WordtrainerBackend was accepted");
        assertFalse(wg.showWordpromt(),"WordtrainerGui did not correctly identify an empty Wordtrainer");
        WordtrainerBackend wb = new WordtrainerBackend();
        wb.addWordpair(new Wordpair("google","https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));
        wg.changeWordtainerBackend(wb);
        assertTrue(wg.showWordpromt(),"Wordtrainer did not correctly identify a filled Wordtrainer");

    }
}
