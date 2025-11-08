package avb;

public class Btree {
    private Node root;

    public Btree() {
        this.root = null;
    }

    public void Insert(int value) {
        ManualInsert(this.root, value);
    }

    private Node ManualInsert(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.esq = ManualInsert(current.esq, value);

        } else if (value > current.value) {
            current.dir = ManualInsert(current.dir, value);

        }

        return current;
    }
}
