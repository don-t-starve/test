package lock;

public interface Lock {
    ThreadLocal<String> locks = new ThreadLocal<>();
    boolean tryLock(String lockKey);
    void unLock();
    void lock(String lockKey);
}
