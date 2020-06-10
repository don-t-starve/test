package audition;

public class Stat {

    private String date;
    private int registerCount;
    private int activeCount;

    public Stat() {
    }

    public Stat(String date, int registerCount, int activeCount) {
        this.date = date;
        this.registerCount = registerCount;
        this.activeCount = activeCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(int registerCount) {
        this.registerCount = registerCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }
}
