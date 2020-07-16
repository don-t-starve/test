package Observer;

public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attachObserver(this);
    }

    @Override
    public void update() {
        System.out.println("binary change:" + Integer.toBinaryString(subject.getState()));
    }
}
