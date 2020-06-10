package audition;

public class Node {
    Node next;
    int value;

    public Node() {
    }

    public Node(Node next, int value) {
        this.next = next;
        this.value = value;
    }

    public static void main(String[] args) {
        Node node = new Node();
        Node temp = node;
        int i = 0;
        while (i < 5) {
            node.value = i;
            node.next = new Node();
            i++;
            node = node.next;
        }
        while (temp.next != null) {
            System.out.println("Node value:" + temp.value);
            temp = temp.next;
        }
    }
}
