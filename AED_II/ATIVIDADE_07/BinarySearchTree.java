import java.util.Scanner;

public class BinarySearchTree {

    public static class No {
        public No esq;
        public No dir;
        public int val;
        public No(int val) {
            esq = null;
            dir = null;
            this.val = val;
        }
    }
    
    private No raiz;

    public int altura(No no) {
        if (no == null)
            return 0;
        else {
            int lDepth = altura(no.esq);
            int rDepth = altura(no.dir);

            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }
    }

    public void insere(No no){
        if (raiz == null) {
            raiz = new No(no.val);
        } else {
            insereNo(raiz, no);
        }
    }
    
    private void insereNo(No noAtual, No no) {
        if (no.val < noAtual.val) {
            if (noAtual.esq == null) {
                noAtual.esq = (no);
            }
            else
                insereNo(noAtual.esq, no);
        }
        if (no.val > noAtual.val) {
            if (noAtual.dir == null)
                noAtual.dir = (no);
            else
                insereNo(noAtual.dir, no);
        }
    }

    public No busca(No noAtual, int val){
        if (noAtual == null)
            return null;
        if (noAtual.val == val)
            return noAtual;
        if (val < noAtual.val)
            return busca(noAtual.esq, val);
        return busca(noAtual.dir, val);
    }

    public No remove(No No, int val) {
        if (No == null)
            return No;
        if (val < No.val) {
            No.esq = (remove(No.esq, val));
        } else if (val > No.val) {
            No.dir = (remove(No.dir, val));
        } else {
            if (No.esq == null) {
                return No.dir;
            } else if (No.dir == null)
                return No.esq;
            No.val = (min(raiz.dir));
            No.dir = (remove(raiz.dir, raiz.val));
        }
        return No;
    }

    public int min(No root) {
        int minimum = root.val;
        while (root.esq != null) {
            minimum = root.esq.val;
            root = root.esq;
        }
        return minimum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree ABB = new BinarySearchTree();

        int input, quantidade = 0, alturaInicial;
        //reading 
        while ((input = scanner.nextInt()) >= 0) {
            quantidade++;
            ABB.insere(new No(input));
        }
        alturaInicial = ABB.altura(ABB.raiz);
        
        //searching
        int quantidadeFinal = quantidade, alturaFinal;
        input = scanner.nextInt();
        boolean existe = ABB.busca(ABB.raiz, input) != null;
        if (!existe) {
            ABB.insere(new No(input));
            quantidadeFinal++;
        } else {
            ABB.remove(ABB.raiz, input);
            quantidadeFinal--;
        }

        alturaFinal = (quantidadeFinal == 0 ) ? 0 : ABB.altura(ABB.raiz);     
        
        scanner.close();
        System.out.println(quantidade + " " + alturaInicial);
        System.out.println(quantidadeFinal + " " + alturaFinal);
    }
}