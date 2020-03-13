import java.util.Scanner;

public class BubbleSort {

    //comeca falso para que na primeira vez fique true, a variavel controla a ultima pessoa que fez a troca
    public boolean isFulano = false;
    public int[] list;

    public void sort (){
        for(int i = 0; i < list.length - 1; i++){
            for(int j = 0; j < list.length - 1 - i; j++){
                if(list[j] > list[j+1]){
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;

                    if(isFulano) isFulano = false;
                    else isFulano = true;
                }
            }
        }
    }

    public static void main(String[] args) {        
        System.out.println("Informe N: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] list = new int[n];
        for(int i = 0; i < n; i++){
            list[i] = in.nextInt();
        }
        in.close();

        BubbleSort obj = new BubbleSort();
        obj.list = list;
        obj.sort();
        String vencedor = (obj.isFulano) ? "Fulano" : "Ciclano";
        System.out.println("Vencedor: " + vencedor);
     
        //print da lista final 
        // for(int i = 0; i < n; i++){
        //     System.out.print(list[i] + " ");
        // }
    }

}