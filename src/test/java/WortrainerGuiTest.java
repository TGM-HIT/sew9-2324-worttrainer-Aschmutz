import aschmutz.Wordpair;
import aschmutz.WordtrainerBackend;
import aschmutz.WordtrainerGui;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class WortrainerGuiTest {
    @DisplayName("Tests the GUI/Controller system -> WordtrainerGui")
    @Test
    public void testChangeWord(){
        WordtrainerGui wg = new WordtrainerGui(new WordtrainerBackend());
        assertThrows(IllegalArgumentException.class, ()->wg.changeWordtainerBackend(null,true),"New \"NULL\" WordtrainerBackend was accepted");
        assertFalse(wg.showWordpromt(),"WordtrainerGui did not correctly identify an empty Wordtrainer");
        WordtrainerBackend wb = new WordtrainerBackend();
        wb.addWordpair(new Wordpair("TGM","https://upload.wikimedia.org/wikipedia/commons/b/ba/TGM_Logo.png"));
        wb.addWordpair(new Wordpair("untis","https://neilo.webuntis.com/assets/images/logo.png"));
        wg.changeWordtainerBackend(wb,true);
        assertTrue(wg.showWordpromt(),"Wordtrainer did not correctly identify a filled Wordtrainer");

    }
}
