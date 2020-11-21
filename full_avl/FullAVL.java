import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullAVL{
    public class Node {
        public Node esq;
        public Node dir;
        public int val;
        public Node(int val) {
            esq = null;
            dir = null;
            this.val = val;
        }
    }

    public Node raiz;

    public int altura(Node no) {
        if (no == null)
            return 0;
        
        return 1 + Math.max(altura(no.esq), altura(no.dir));
    }

    public int fator(Node no) {
        return altura(no.esq) - altura(no.dir);
    }

    public Node balancear(Node raiz){
        Node novaArv = raiz;
        int FA = fator(raiz);

        if(FA > 1) {
            int FB = fator(raiz.esq);
            if(FB > 0) {
                //System.out.println("LL");
                novaArv = rotacionarLL(novaArv);
            }
            else if (FB < 0){
                //System.out.println("LR");
                novaArv = rotacionarLR(novaArv);
            }
        }
        else if(FA < -1) {
            int FB = fator(raiz.dir);
            if(FB < 0) {
                //System.out.println("RR");
                novaArv = rotacionarRR(novaArv);
            }
            else if(FB > 0){
                //System.out.println("RL");
                novaArv = rotacionarRL(novaArv);
            }
        }

        return novaArv;
    }

    public Node rotacionarLL(Node no){
        Node PA = no;
        Node PB = PA.esq;

        PA.esq = PB.dir;
        PB.dir = PA;
        return PB;
    }

    public Node rotacionarRR(Node no) {
        Node PA = no;
        Node PB = PA.dir;

        PA.dir = PB.esq;
        PB.esq = PA;
        return PB;
    }

    public Node rotacionarLR(Node no) {
        Node PA = no;
        Node PB = PA.esq;
        Node PC = PB.dir;

        PB.dir = PC.esq;
        PC.esq = PB;
        PA.esq = PC.dir;
        PC.dir = PA;
        return PC;
    }

    public Node rotacionarRL(Node no) {
        Node PA = no;
        Node PB = PA.dir;
        Node PC = PB.esq;

        PB.esq = PC.dir;
        PC.dir = PB;
        PA.dir = PC.esq;
        PC.esq = PA;
        return PC;
    }

    public void imprime(Node no) {
        if (no != null) {
            System.out.print("(C" + no.val);
            imprime(no.esq);
            imprime(no.dir);
            System.out.print(")");
        } else {
            System.out.print("()");
        }
    }


    public void insere(Node no){
        if (raiz == null) {
            raiz = new Node(no.val);
        } else {
            raiz = insereNo(raiz, no);
        }
    }
    
    private Node insereNo(Node noAtual, Node no) {
        if (no.val < noAtual.val) {
            if (noAtual.esq == null)
                noAtual.esq = (no);
            else {
                noAtual.esq = insereNo(noAtual.esq, no);
                noAtual = balancear(noAtual);                
            }
        }
        else if (no.val > noAtual.val) {
            if (noAtual.dir == null) 
                noAtual.dir = (no);            
            else {
                noAtual.dir = insereNo(noAtual.dir, no);
                noAtual = balancear(noAtual);
            }
        } 
        return noAtual;
    }

    public Node busca(Node noAtual, int val){
        if (noAtual == null)
            return null;
        if (noAtual.val == val)
            return noAtual;
        if (val < noAtual.val)
            return busca(noAtual.esq, val);
        return busca(noAtual.dir, val);
    }

    public Node remove(Node No, int val) {
        if (No == null)
            return No;
        if (val < No.val) {
            No.esq = (remove(No.esq, val));
            return balancear(No);
        } else if (val > No.val) {
            No.dir = (remove(No.dir, val));
            return balancear(No);
        } else {
            if (No.esq == null) {
                return No.dir;
            } else if (No.dir == null)
                return No.esq;
            No.val = (min(No.dir));
            No.dir = (remove(No.dir, No.val));
            return balancear(No);
        }
    }

    public int min(Node root) {
        int minimum = root.val;
        while (root.esq != null) {
            minimum = root.esq.val;
            root = root.esq;
        }
        return minimum;
    }

    public void carregar(int[] list, int search) {
        for(int i = 0; i < list.length; i++){
            this.insere(new Node(list[i]));
        }
        Node no = this.busca(this.raiz, search);
        if (no == null) {
            this.insere(new Node(search));
        } else {
            this.raiz = this.remove(this.raiz, no.val);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] list = new int[count];
        for(int i = 0; i < count; i++){
            list[i] = scanner.nextInt();
        }
        int search = scanner.nextInt();
        scanner.close();
        
        FullAVL arv = new FullAVL();
        arv.carregar(list, search);
        arv.imprime(arv.raiz);
    }
}