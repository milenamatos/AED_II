import java.util.Scanner;

public class HashTable {
    int tamanho;
    int quantidade;
    Hash[] hash;

    class Node {
        int chave;
        Node prox;
    }

    class Hash {
        Node primeiro = null;
        Node ultimo = null;
    }

    public void inicia(Scanner s) {
        this.tamanho = s.nextInt();
        this.quantidade = s.nextInt();
        this.hash = new Hash[this.tamanho];

        int i;
        for (i = 0; i < tamanho; i++) {
            this.hash[i] = new Hash();
            this.hash[i].primeiro = null;
            this.hash[i].ultimo = null;
        }

        for (i = 0; i < quantidade; i++) {
            insere(s.nextInt());
        }

        int element = s.nextInt();
        boolean existe = pesquisa(element);

        if (!existe) {
            insere(element);
        } else {
            remove(element);
        }
        
        imprime();
    }
    
    public boolean pesquisa(int chave) {
        int index = getModulo(chave, tamanho);
        Node aux = hash[index].primeiro;
        
        while (aux != null) {
            if (aux.chave == chave)
            return true;
            else
                aux = aux.prox;
            }
        return false;
    }

    public Node criaNo(int chave) {
        Node item = new Node();
        item.prox = null;
        item.chave = chave;
        return item;
    }

    public void insere(int valor) {
        int index = getModulo(valor, tamanho);
        if (hash[index].primeiro == null) {
            hash[index].primeiro = criaNo(valor);
            hash[index].ultimo = hash[index].primeiro;
        } else {
            hash[index].ultimo.prox = criaNo(valor);
            hash[index].ultimo = hash[index].ultimo.prox;
        }
    }    
    
    public void remove(int chave) {
        int index = getModulo(chave, tamanho);
        int i = 0;
        
        Node aux = hash[index].primeiro;
        Node item;
        
        if (aux.chave == chave) {
            hash[index].primeiro = hash[index].primeiro.prox;
            item = aux;
        } else {
            while (i == 0) {
                if (aux.prox.chave == chave) {
                    item = aux.prox;

                    if (item == hash[index].ultimo)
                    hash[index].ultimo = aux;

                    aux.prox = item.prox;
                    i++;
                } else
                aux = aux.prox;
            }
        }
    }

    public int getModulo(int chave, int m) {
        int modulo = chave % m;
        return modulo;
    }

    public void imprime() {
        Node aux;
        
        int i;
        for (i = 0; i < this.tamanho; i++) {
            System.out.print("[" + i + "]");
            aux = this.hash[i].primeiro;

            while (aux != null) {
                System.out.print(" " + aux.chave);
                aux = aux.prox;
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        HashTable m = new HashTable();
        Scanner s = new Scanner(System.in);
        
        m.inicia(s);
        s.close();
    }
}