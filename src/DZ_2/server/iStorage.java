package DZ_2.server;

public interface iStorage<T> {
    void saveLog(T text);
    T loadLog();
}