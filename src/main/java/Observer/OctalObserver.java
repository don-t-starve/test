package Observer;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.attachObserver(this);
    }

    @Override
    public void update() {
        System.out.println("octal change:" + Integer.toOctalString(subject.getState()));
    }
}
