package Observer;

public class ObserverMain {
    public static void main(String[] args) {
        Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);
        new HexaObserver(subject);

        subject.setState(15);
        System.out.println("-----------------------");
        subject.setState(10);

    }
}
