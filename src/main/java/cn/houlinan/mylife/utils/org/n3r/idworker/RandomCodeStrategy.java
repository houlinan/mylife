package cn.houlinan.mylife.utils.org.n3r.idworker;

public interface RandomCodeStrategy {
    void init();

    int prefix();

    int next();

    void release();
}
