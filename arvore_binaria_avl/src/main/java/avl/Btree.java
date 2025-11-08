package avl;

public class Btree {
    private Node root;

    public Btree() {
        this.root = null;
    }

    // Serve para retornar a altura do nó que está sendo pedido
    private int getHigh(Node high) {
        if (high == null) {
            return 0;
        }

        return high.altura;
    }

    // Calcula a altura do novo nó
    private void newHigh(Node high) {
        if (high != null) {
            high.altura = 1 + Math.max(getHigh(high.dir), getHigh(high.esq));
        }
    }

    // O código insert continua igual, fazemos apenas uma alteração para
    // atualizar a altura da àrvore.
    public void Insert(int value) {
        this.root = RecursiveInsert(this.root, value);
    }

    private Node RecursiveInsert(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.esq = RecursiveInsert(current.esq, value);

        } else if (value > current.value) {
            current.dir = RecursiveInsert(current.dir, value);

        } else {
            return current;

        }

        newHigh(current);
        int balance = Verify(current);

        // ... (depois de 'int balance = Verify(current);') ...

        // Caso 1: Esquerda-Esquerda (Perfeito!)
        if (balance < -1 && value < current.esq.value) {
            return RotationDir(current);
        }

        // Caso 2: Direita-Direita (O que estava faltando)
        // Se pesou à direita E o novo nó foi inserido à direita.
        if (balance > 1 && value > current.dir.value) { // <-- CONDIÇÃO CORRIGIDA
            return RotationEsq(current); // <-- CORREÇÃO SIMPLES
        }

        // Caso 3: Esquerda-Direita (Perfeito!)
        if (balance < -1 && value > current.esq.value) {
            current.esq = RotationEsq(current.esq);
            return RotationDir(current);
        }

        // Caso 4: Direita-Esquerda (O caso 2 original, agora corrigido)
        // Se pesou à direita E o novo nó foi inserido à esquerda.
        if (balance > 1 && value < current.dir.value) { // <-- CONDIÇÃO IGUAL AO SEU CASO 2
            // CORREÇÃO: Rotação dupla
            current.dir = RotationDir(current.dir);
            return RotationEsq(current); // <-- ERRO ESTAVA AQUI (era RotationDir)
        }

        return current;
    }

    // Verificador de desbalanceio
    private int Verify(Node no) {
        if (no == null) {
            return 0;
        }

        return getHigh(no.dir) - getHigh(no.esq);
    }

    // Rotação direita
    private Node RotationDir(Node fatherUnbalanced) {
        // O filho da esquerda passa a se tornar o novo pai
        Node newFather = fatherUnbalanced.esq;

        // A arvore da direita é movida
        Node midTree = newFather.dir;

        // O antigo pai (fatherUnbalanced) passa a ser filho da direita do novo pai
        // (newFather)
        newFather.dir = fatherUnbalanced;

        // A arvore que havia sido "guardada" agora é devolvida parae se torna filha da
        // esquerda
        fatherUnbalanced.esq = midTree;

        // Atualiza as alturas, atualizando o nó que havia descido e dps o que subiu
        newHigh(fatherUnbalanced);
        newHigh(newFather);

        return newFather;
    }

    // Rotação para a esquerda, funciona da mesma maneira do primeiro, no entanto,
    // aqui invertemos os lados e posições
    private Node RotationEsq(Node fatherUnbalanced) {
        Node newFather = fatherUnbalanced.dir;
        Node midTree = newFather.esq;

        newFather.esq = fatherUnbalanced;
        fatherUnbalanced.dir = midTree;

        newHigh(fatherUnbalanced);
        newHigh(newFather);

        return newFather;
    }
}
