package arn;

public class Node {

    private static final boolean red = true;
    private static final boolean black = false;

    int value;
    Node esq;
    Node dir;
    boolean color;
    Node father;

    public Node(int value) {
        this.value = value;
        this.color = red;
        this.esq = null;
        this.dir = null;
        this.father = null;
    }

    public boolean isRed() {
        return this.color == red;
    }
}