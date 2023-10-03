package aschmutz;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handels Saving and Loading of WordtrainerBackend.
 */
public interface WordtrainerSaveManager {

    /**
     * Loads a {@link WordtrainerBackend} from a File at a given Path and Returns it
     * @param path the Path from which the WordtrainerBackend may be loaded
     * @return The WordtrainerBackend, that is loaded. This loaded WordtrainerBackend is equal to the saved WordtrainerBackend
     */
    public WordtrainerBackend load(String path) throws FileNotFoundException, IOException;

    /**
     * Saves a {@link WordtrainerBackend} into a file, at a given path
     * @param path The path at which the File containing the WordtrainerBackend will be stored
     * @param wordtrainerBackend the WordtrainerBackend Object that is to be stored.
     */
    public void save(String path, WordtrainerBackend wordtrainerBackend) throws IOException;
}
