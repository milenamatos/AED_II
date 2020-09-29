import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AVL {
    public class Node {
        public Node esq;
        public Node dir;
        public String val;

        public Node(String val) {
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

    public AVL balancear(Node raiz){
        Node novaArv = raiz;
        int FA = fator(raiz);

        if(FA > 1) {
            int FB = fator(raiz.esq);
            if(FB > 0) {
                System.out.println("LL");
                novaArv = rotacionarLL(novaArv);
            }
            else if (FB < 0){
                System.out.println("LR");
                novaArv = rotacionarLR(novaArv);
            }
        }
        else if(FA < -1) {
            int FB = fator(raiz.dir);
            if(FB < 0) {
                System.out.println("RR");
                novaArv = rotacionarRR(novaArv);
            }
            else if(FB > 0){
                System.out.println("RL");
                novaArv = rotacionarRL(novaArv);
            }
        }
        AVL nova = new AVL();
        nova.raiz = novaArv;
        return nova;
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
            System.out.print("(" + no.val);
            imprime(no.esq);
            imprime(no.dir);
            System.out.print(")");
        } else {
            System.out.print("()");
        }
    }

    public int retornaIndex(String arv, int i, int j) { 
        if (i > j)
            return -1;
    
        List<Character> s = new ArrayList<Character>();
        for (int k = i; i <= j; k++){ 
            if (arv.charAt(k) == '(')  
                s.add(arv.charAt(k));
    
            else if (arv.charAt(k) == ')'){  
                if (s.get(0) == '('){
                    s.remove(0); 
    
                    if (s.size() == 0)
                        return k;
                }
            }
        } 
        return -1;
    }
  
    public Node carregarArvore(String arv, int i, int j){      
        if (i > j) 
            return null;
     
        int k = i;
        while(k < arv.length()){
            if(arv.charAt(k) == ('(') || arv.charAt(k) == (')')){
                break;
            } else k++;
        }
        String key = arv.substring(i, k);
        int len = key.length();
        if(len == 0)
            return null;

        Node no = new Node(key);
        int index = -1;
    
        if (i + len <= j && arv.charAt(i + len) == '(')  
            index = retornaIndex(arv, i + len, j);  
    
        if (index != -1){
            no.esq = carregarArvore(arv, i + len + 1, index - 1);   
            no.dir = carregarArvore(arv, index + 2, j); 
        }
        return no;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String arvore = scanner.nextLine();
        scanner.close();
        
        AVL arv = new AVL();
        arv.raiz = arv.carregarArvore(arvore, 1, arvore.length() - 1);
        System.out.println(arv.altura(arv.raiz)-1);
        arv.imprime(arv.raiz);
        System.out.println();

        AVL balanceada = arv.balancear(arv.raiz);
        System.out.println(balanceada.altura(balanceada.raiz)-1);
        balanceada.imprime(balanceada.raiz);
    }
}