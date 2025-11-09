package avl;

public class Node {
    int value;
    Node esq;
    Node dir;
    int altura;

    public Node(int value) {
        this.value = value;
        this.dir = null;
        this.esq = null;
        this.altura = 1;
    }
}
