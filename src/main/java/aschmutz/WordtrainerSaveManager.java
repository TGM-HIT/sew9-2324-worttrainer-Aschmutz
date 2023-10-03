package aschmutz;

public interface WordtrainerSaveManager {
    public WordtrainerBackend load(String path);
    public void save(String path, WordtrainerBackend wordtrainerBackend);
}
