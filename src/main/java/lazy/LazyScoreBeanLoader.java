package lazy;

import org.springframework.cglib.proxy.LazyLoader;

public class LazyScoreBeanLoader implements LazyLoader {

    private String id;
    private Class<? extends ScoreBean> clazz;

    public LazyScoreBeanLoader(String id, Class<? extends ScoreBean> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public Object loadObject() throws Exception {
        if ("1".equals(id)) {
            return new ScoreBean("1", 1);
        } else {
            return new ScoreBean();
        }
    }
}
