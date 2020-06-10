package lazy;

public class ScoreBean {
    private String scoreReason;
    private int score;

    public ScoreBean() {
    }

    public ScoreBean(String scoreReason, int score) {
        this.scoreReason = scoreReason;
        this.score = score;
    }

    public String getScoreReason() {
        return scoreReason;
    }

    public void setScoreReason(String scoreReason) {
        this.scoreReason = scoreReason;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ScoreBean{" +
                "scoreReason='" + scoreReason + '\'' +
                ", score=" + score +
                '}';
    }
}
