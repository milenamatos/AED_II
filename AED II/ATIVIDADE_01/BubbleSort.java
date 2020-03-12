import java.util.Scanner;

public class BubbleSort {

    public boolean isFulano = true;

    public String sort (int[] list){
        for(int i = 0; i < list.length; i++){
            for(int j = 0; j < list.length - 1; j++){
                if(list[j] > list[j+1]){
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;

                    if(isFulano) isFulano = false;
                    else isFulano = true;
                }
            }
        }

        if(isFulano) return "Fulano";
        return "Ciclano";
    }

    public static void main(String[] args) {        
        System.out.println("Informe N: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] list = new int[n];
        for(int i = 0; i < n; i++){
            list[i] = in.nextInt();
        }

        BubbleSort obj = new BubbleSort();
        System.out.println("Vencedor: " + obj.sort(list));
    }

}