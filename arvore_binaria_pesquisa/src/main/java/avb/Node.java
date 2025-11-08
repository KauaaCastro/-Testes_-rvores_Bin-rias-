package avb;

public class Node {
    int value;
    Node esq;
    Node dir;

    public Node(int value) {
        this.value = value;
        this.dir = null;
        this.esq = null;
    }
}
