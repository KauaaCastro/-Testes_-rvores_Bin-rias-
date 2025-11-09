package avb;

public class Btree {
    private Node root;
    private long AdictionLevels;

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

    // Nível Médio:
    public double getAddLevel(int totalNode) {
        this.AdictionLevels = 0;

        CalcLevels(this.root, 0);

        if (totalNode == 0) {
            return 0;
        }

        return (double) this.AdictionLevels / totalNode;
    }

    private void CalcLevels(Node node, int currentLevel) {
        if (node == null) {
            return;
        }

        this.AdictionLevels += currentLevel;

        CalcLevels(node.esq, currentLevel + 1);
        CalcLevels(node.dir, currentLevel + 1);
    }
}
