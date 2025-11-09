package arn;

public class Btree {
    private Node root;
    private static final boolean red = true;
    private static final boolean black = false;

    public Btree() {
        this.root = null;
    }

    public void Insert(int value) {
        // Nascimento do nó vermelho
        Node newNode = new Node(value);

        // Nó father atual
        Node currentFather = null;

        // Nó Atual
        Node current = this.root;

        // ex: arvore 50 -> 30 (com inserção de 40)
        // loop 1: 50!=null?
        // loop 2: 30!=null (pois o caso de exemplo se aplica ao segundo if, virando a
        // direita)
        while (current != null) {
            // father atual = atual nó (50)
            currentFather = current;

            // Se o valor do novo nó (40) < valor do nó atual (50): virar a esquerda
            if (newNode.value < current.value) {
                current = current.esq; // Se houver algo a esquerda o loop continua, se não, o 40 será pendurado aq e o
                                       // nó atual é = null

                // Se o valor do novo nó (40) > valor do nó atual (50): virar a direita
            } else if (newNode.value > current.value) {
                current = current.dir; // Se houver algo a direita, o loop continua, se não, 0 40 será pendurado aq e o
                                       // nó atual é = null
            } else {
                return;
            }
        }

        // Aqui armazenamos que o nó 30 é o father do nó 40
        newNode.father = currentFather;

        // caso 1: Se o father atual for nulo, a arvore está vazia, portanto preenche.
        // caso 2: Se o father atual é maior que o valor atual, pendurar o valor à
        // direita
        // caso 3: Se o father atual é menor que o valor atual, pendurar o valor à
        // esquerda
        if (currentFather == null) {
            this.root = newNode;
        } else if (newNode.value < currentFather.value) {
            currentFather.esq = newNode;
        } else {
            currentFather.dir = newNode;
        }

        // Aqui tornamos a raiz SEMPRE PRETA!!
        fixInsert(newNode);
        this.root.color = black;
    }

    private void RotationEsq(Node x) {
        // Aqui dizemos que y é o filho da direita de x
        Node y = x.dir;

        // Aqui fazemos a alteração, y sobe e x vira seu novo filho da direita
        x.dir = y.esq;

        // Se a arvore já existe, dizemos q seu novo father é x
        if (y.esq != null) {
            y.esq.father = x;
        }

        // Conectamos y ao "avo"
        y.father = x.father;

        // Caso 1: Se x não era a raiz, não há um avo e y se torna a nova raiz.
        // Caso 2: Se x era um filho da esquerda, o y agora é o novo filho da esquerda
        // Caso 3: Se x era um filho da direita, o y agora é o novo filho da direita
        if (x.father == null) {
            this.root = y;
        } else if (x == x.father.esq) {
            x.father.esq = y;
        } else {
            x.father.dir = y;
        }

        // Aqui passamos x para o filho da esquerda de y
        y.esq = x;

        // E por fim, contamos ao x quem é seu "father"
        x.father = y;
    }

    // O mesmo processo acima ocorre aqui, porém para a direita
    private void RotationDir(Node y) {
        Node x = y.esq;

        y.esq = x.dir;

        if (x.dir != null) {
            x.dir.father = y;
        }

        x.father = y.father;

        if (y.father == null) {
            this.root = x;
        } else if (y == y.father.dir) {
            y.father.dir = x;
        } else {
            y.father.esq = x;
        }

        x.dir = y;
        y.father = x;
    }

    // Verificação de cores:
    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == red;
    }

    private void setColor(Node node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    // Correção do desbalanceio da arvore:

    private void fixInsert(Node current) {
        Node uncle;

        while (current != root && isRed(current.father)) {
            if (current.father == current.father.father.esq) {
                uncle = current.father.father.dir;

                if (isRed(uncle)) {
                    // Solução: Apenas recolorir
                    setColor(current.father, black); // father fica black
                    setColor(uncle, black);
                    setColor(current.father.father, red); // Avô fica VERMELHO

                    // Move o problema para cima
                    current = current.father.father;
                } else {
                    // Sub-caso "Zigue-zague" (Esquerda-Direita)
                    if (current == current.father.dir) {
                        current = current.father; // Move para o father
                        RotationEsq(current); // Transforma em "linha reta"
                    }

                    // Sub-caso "Linha Reta" (Esquerda-Esquerda)
                    // Solução: Recolore e rotaciona o avô
                    setColor(current.father, black);
                    setColor(current.father.father, red);
                    RotationDir(current.father.father);
                }
            } else {
                uncle = current.father.father.esq; // O uncle é o irmão da esquerda

                if (isRed(uncle)) {
                    setColor(current.father, black);
                    setColor(uncle, black);
                    setColor(current.father.father, red);
                    current = current.father.father;
                } else {
                    // Sub-caso "Zigue-zague" (Direita-Esquerda)
                    if (current == current.father.esq) {
                        current = current.father;
                        RotationDir(current); // Transforma em "linha reta"
                    }

                    // Sub-caso "Linha Reta" (Direita-Direita)
                    setColor(current.father, black);
                    setColor(current.father.father, red);
                    RotationEsq(current.father.father);
                }
            }
        } // Fim do loop 'while'
        this.root.color = black;
    }
}
