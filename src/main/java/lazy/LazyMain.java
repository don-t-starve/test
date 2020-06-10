package lazy;

import org.springframework.cglib.proxy.Enhancer;

public class LazyMain {

    public static void main(String[] args) {
        ScoreBean scoreBean = (ScoreBean) Enhancer.create(ScoreBean.class, new LazyScoreBeanLoader("1", ScoreBean.class));
        System.out.println(scoreBean.toString());
    }

}
