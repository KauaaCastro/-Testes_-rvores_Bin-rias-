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

        // Navegação recursiva para a esquerda e para direita
        if (value < current.value) {
            current.esq = RecursiveInsert(current.esq, value);

        } else if (value > current.value) {
            current.dir = RecursiveInsert(current.dir, value);

        } else {
            return current;

        }

        // Atualiza a altura do nó atual
        newHigh(current);

        // Calcula o alarme e verifica o fator de balanceamento
        int balance = Verify(current);

        // Caso: Esquerda-Esquerda, caso o peso maior seja na esquerda e o valor foi
        // inserido na subArvore da ESQUERDA do filho da ESQUERDA
        if (balance < -1 && value < current.esq.value) {
            return RotationDir(current);
        }

        // Caso: Direita-Direita, caso o peso maior seja na direita e o valor foi
        // inserido na subArvore da DIREITA do filho da DIREITA
        if (balance > 1 && value > current.dir.value) {
            return RotationEsq(current);
        }

        // Caso: Esquerda-Direita, o nó está pesado à esquerda e o valor foi inserido na
        // subArvore da DIREITA do filho da ESQUERDA
        if (balance < -1 && value > current.esq.value) {
            current.esq = RotationEsq(current.esq);
            return RotationDir(current);
        }

        // Caso: Direita-Esquerda, o nó está pesado a direita e o valor inserido na
        // subArvore da ESQUERDA do filho da DIREITA
        if (balance > 1 && value < current.dir.value) {
            current.dir = RotationDir(current.dir);
            return RotationEsq(current);
        }

        // Retorno caso não seja necessário nenhum conserto
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
