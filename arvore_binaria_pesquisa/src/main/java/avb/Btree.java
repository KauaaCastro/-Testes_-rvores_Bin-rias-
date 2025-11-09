package avb;

import java.util.LinkedList;
import java.util.Queue;

public class Btree {
    private Node root;

    public Btree() {
        this.root = null;
    }

    public void Insert(int value) {
        Node newNode = new Node(value);

        if (this.root == null) {
            this.root = newNode;
            return;
        }

        Node current = this.root;
        Node parent = null;

        while (true) {
            parent = current;

            if (value < current.value) {
                current = current.esq;
                if (current == null) {
                    parent.esq = newNode;
                    return;
                }
            } else if (value > current.value) {
                current = current.dir;
                if (current == null) {
                    parent.dir = newNode;
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static class NoComNivel {
        Node no;
        int nivel;

        NoComNivel(Node no, int nivel) {
            this.no = no;
            this.nivel = nivel;
        }
    }

    public double getAddLevel(int totalNode) {
        if (totalNode == 0 || this.root == null) {
            return 0;
        }

        long AdictionLevels = 0;

        Queue<NoComNivel> fila = new LinkedList<>();

        fila.add(new NoComNivel(this.root, 0));

        while (!fila.isEmpty()) {
            NoComNivel atual = fila.poll();

            AdictionLevels += atual.nivel;

            if (atual.no.esq != null) {
                fila.add(new NoComNivel(atual.no.esq, atual.nivel + 1));
            }
            if (atual.no.dir != null) {
                fila.add(new NoComNivel(atual.no.dir, atual.nivel + 1));
            }
        }

        return (double) AdictionLevels / totalNode;
    }
}