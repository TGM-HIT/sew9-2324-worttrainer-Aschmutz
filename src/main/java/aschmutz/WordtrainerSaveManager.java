package aschmutz;

/**
 * TODO
 */
public interface WordtrainerSaveManager {

    /**
     * TODO
     * @param path
     * @return
     */
    public WordtrainerBackend load(String path);

    /**
     * TODO
     * @param path
     * @param wordtrainerBackend
     */
    public void save(String path, WordtrainerBackend wordtrainerBackend);
}
