import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ARN{
    public class Node {
        public Node esq;
        public Node dir;
        public Node pai;
        public int val;
        public String cor;
        public Node(int val, String cor) {
            esq = null;
            dir = null;
            pai = null;
            this.val = val;
            this.cor = cor;
        }
    }

    public Node raiz;

    public int altura(Node no) {
        if (no == null)
            return 0;
        
        int sum = (no.cor == "negro") ? 1 : 0;
    
        int He = altura(no.esq);        
        int Hd = altura(no.dir);        

        return (He > Hd) ? He + sum: Hd + sum;
    }

    public void rotacionarLL(Node no){
        Node PA = no;
        Node PB = PA.esq;

        PA.esq = PB.dir;
        if (PA.esq != null) PA.esq.pai = PA;

        PB.pai = PA.pai;
        if(PA.pai == null) {
            raiz = PB;
        } else if (PA == PA.pai.dir) {
            PA.pai.dir = PB;
        } else {
            PA.pai.esq = PB;
        }
        
        PB.dir = PA;
        PA.pai = PB;
    }

    public void rotacionarRR(Node no) {
        Node PA = no;
        Node PB = PA.dir;
        
        PA.dir = PB.esq;
        if (PB.esq != null) PB.esq.pai = PB;
        
        PB.pai = PA.pai;
        if(PA.pai == null) {
            raiz = PB;
        } else if (PA == PA.pai.esq) {
            PA.pai.esq = PB;
        } else {
            PA.pai.dir = PB;
        }

        PB.esq = PA;
        PA.pai = PB;
    }

    public void imprime(Node no) {
        if (no != null) {
            System.out.print("(");
            System.out.print(no.cor == "negro" ? "N": "R");
            System.out.print(no.val);
            imprime(no.esq);
            imprime(no.dir);
            System.out.print(")");
        } else {
            System.out.print("()");
        }
    }

    public void insere(Node no){
        if (raiz == null) {
            raiz = new Node(no.val, "negro");
        } else {
            insereNo(no);
        }
    }
    
    private void insereNo(Node no) {
        Node noAtual = null;
        Node X = raiz;
        while(X != null) {
            noAtual = X;
            if (no.val < noAtual.val) {
                X = noAtual.esq;                
            }
            else if (no.val > noAtual.val) {
                X = noAtual.dir;                
            } 
        }
        
        no.pai = noAtual;
        if (noAtual == null) {
            raiz = no;
        } else if (no.val < noAtual.val) {
            noAtual.esq = no;
        } else {                     
            noAtual.dir = no;
        }

        if(no.pai == null) {
            no.cor = "negro";
            return;
        }

        if(no.pai.pai == null) {
            return;
        }

        rebalancear(no);
    }

    public void rebalancear(Node noAtual){
        if (noAtual.val == raiz.val) {
            noAtual.cor = "negro";
            return;
        }

		Node u;
		while (noAtual.pai.cor == "rubro") {
			if (noAtual.pai == noAtual.pai.pai.dir) {
				u = noAtual.pai.pai.esq; // tio
				if (u!= null && u.cor == "rubro") {
					u.cor = "negro";
					noAtual.pai.cor = "negro";
					noAtual.pai.pai.cor = "rubro";
					noAtual = noAtual.pai.pai;
				} else {
					if (noAtual == noAtual.pai.esq) {
						//noAtual = noAtual.pai;
						rotacionarLL(noAtual.pai);
					}

					noAtual.pai.cor = "negro";
					noAtual.pai.pai.cor = "rubro";
					rotacionarRR(noAtual.pai.pai);
				}
			} else {
				u = noAtual.pai.pai.dir; // tio

				if (u != null && u.cor == "rubro") {
					u.cor = "negro";
					noAtual.pai.cor = "negro";
					noAtual.pai.pai.cor = "rubro";
					noAtual = noAtual.pai.pai;	
				} else {
					if (noAtual == noAtual.pai.dir) {
						//noAtual = noAtual.pai;
						rotacionarRR(noAtual.pai);
					}
					noAtual.pai.cor = "negro";
					noAtual.pai.pai.cor = "rubro";
					rotacionarLL(noAtual.pai.pai);
				}
			}
		}
	}

    public void remove(Node noAtual, int val) {
        Node x, y;
        Node z = null;
        while(noAtual != null) {
            if(noAtual.val == val) {
                z = noAtual;
            }

            if(noAtual.val <= val) {
                noAtual = noAtual.dir;
            } else {
                noAtual = noAtual.esq;
            }
        }

        if (z == null)
            return;

        y = z;
        String cor = y.cor;
        if (z.esq == null) {
			x = z.dir;
			transplant(z, z.dir);
		} else if (z.dir == null) {
			x = z.esq;
			transplant(z, z.esq);
		} else {
			y = min(z.dir);
			cor = y.cor;
			x = y.dir;
			if (y.pai == z) {
				x.pai = y;
			} else {
				transplant(y, y.dir);
				y.dir = z.dir;
				y.dir.pai = y;
			}

			transplant(z, y);
			y.esq = z.esq;
			y.esq.pai = y;
			y.cor = z.cor;
		}
		if (cor == "negro"){
			rebalancearRemocao(x);
		}
    }

    private void transplant(Node u, Node v){        
        if (u.pai == null) {
            raiz = v;
		} else if (u == u.pai.esq){
            u.pai.esq = v;
		} else {
            u.pai.dir = v;
        }
        
        if(v != null) {
            v.pai = u.pai;
        }
    }
    
    private void rebalancearRemocao(Node x) {
        Node s;
		while (x != raiz && x.cor == "negro") {
			if (x == x.pai.esq) {
				s = x.pai.dir;
				if (s.cor == "rubro") {
					// case 1
					s.cor = "negro";
					x.pai.cor = "rubro";
					rotacionarRR(x.pai);
					s = x.pai.dir;
				}

				if (s.esq.cor == "negro" && s.dir.cor == "negro") {
					// case 2
					s.cor = "rubro";
					x = x.pai;
				} else {
					if (s.dir.cor == "negro") {
						// case 3
						s.esq.cor = "negro";
						s.cor = "rubro";
						rotacionarLL(s);
						s = x.pai.dir;
					} 

					// case 4
					s.cor = x.pai.cor;
					x.pai.cor = "negro";
					s.dir.cor = "negro";
					rotacionarRR(x.pai);
					x = raiz;
				}
			} else {
				s = x.pai.esq;
				if (s.cor == "rubro") {
					// case 1
					s.cor = "negro";
					x.pai.cor = "rubro";
					rotacionarLL(x.pai);
					s = x.pai.esq;
				}

				if (s.dir.cor == "negro" && s.dir.cor == "negro") {
					// case 2
					s.cor = "rubro";
					x = x.pai;
				} else {
					if (s.esq.cor == "negro") {
						// case 3
						s.dir.cor = "negro";
						s.cor = "rubro";
						rotacionarRR(s);
						s = x.pai.esq;
					} 

					// case 4
					s.cor = x.pai.cor;
					x.pai.cor = "negro";
					s.esq.cor = "negro";
					rotacionarLL(x.pai);
					x = raiz;
				}
			} 
		}
		if (x != null) x.cor = "negro";
    }

    public Node min(Node raiz) {
        int minimum = raiz.val;
        while (raiz.esq != null) {
            minimum = raiz.esq.val;
            raiz = raiz.esq;
        }
        return raiz;
    }

    public void carregar(int[] list, int remove) {
        for(int i = 0; i < list.length; i++){
            this.insere(new Node(list[i], "rubro"));
        }

        System.out.println(altura(raiz));
        imprime(raiz);
        System.out.println();

        remove(raiz, remove); 
        System.out.println(altura(raiz));
        imprime(raiz);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] list = new int[count];
        for(int i = 0; i < count; i++){
            list[i] = scanner.nextInt();
        }
        int remove = scanner.nextInt();
        scanner.close();
        
        ARN arv = new ARN();
        arv.carregar(list, remove);
    }
}