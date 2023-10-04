package aschmutz;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        WordtrainerBackend wordtrainer;
        WordtrainerSaveManager wsm =  new WordtrainerJSONSaveManager();
        try{
            wordtrainer = new WordtrainerBackend(new WordtrainerJSONSaveManager(),"defaultWordtrainer.json");
        } catch (IOException e) {
            wordtrainer = new WordtrainerBackend(wsm);
            wordtrainer.addWordpair(new Wordpair("raspberries","https://picsum.photos/id/102/4320/3240"));
            wordtrainer.addWordpair(new Wordpair("flower","https://picsum.photos/id/152/3888/2592"));
            wordtrainer.addWordpair(new Wordpair("guitar","https://picsum.photos/id/145/4288/2848"));
            wordtrainer.addWordpair(new Wordpair("railway","https://picsum.photos/id/155/3264/2176"));
            wordtrainer.addWordpair(new Wordpair("clock","https://picsum.photos/id/175/2896/1944"));
            wordtrainer.addWordpair(new Wordpair("bus","https://picsum.photos/id/183/2316/1544"));
            wordtrainer.addWordpair(new Wordpair("boat","https://picsum.photos/id/211/1920/1280"));
            wordtrainer.addWordpair(new Wordpair("camera","https://fastly.picsum.photos/id/250/367/267.jpg?hmac=ZWlsJL5aOoUwCnpeDr0F9mUhW7Y91K-bUWQ-zpK3ipw"));
            wordtrainer.addWordpair(new Wordpair("dog","https://fastly.picsum.photos/id/237/536/354.jpg?hmac=i0yVXW1ORpyCZpQ-CknuyV-jbtU7_x9EBQVhvT5aRr0"));
            wordtrainer.addWordpair(new Wordpair("seal","https://fastly.picsum.photos/id/1084/536/354.jpg?grayscale&hmac=Ux7nzg19e1q35mlUVZjhCLxqkR30cC-CarVg-nlIf60"));
            wordtrainer.addWordpair(new Wordpair("lighthouse","https://fastly.picsum.photos/id/870/536/354.jpg?blur=2&grayscale&hmac=A5T7lnprlMMlQ18KQcVMi3b7Bwa1Qq5YJFp8LSudZ84"));
            //Images from picsum.photos/images
        }

        WordtrainerGui wg = new WordtrainerGui(wordtrainer);
        wg.showWordpromt();
        try {
            wordtrainer.save("defaultWordtrainer.json");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Could not save Wordrainer","ERROR WHILE SAVING",JOptionPane.ERROR_MESSAGE);
        }
    }
}