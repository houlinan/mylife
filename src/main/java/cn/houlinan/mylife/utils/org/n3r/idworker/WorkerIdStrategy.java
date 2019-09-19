package cn.houlinan.mylife.utils.org.n3r.idworker;

public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}
