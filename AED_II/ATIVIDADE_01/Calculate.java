import java.util.Scanner;

public class Calculate {

    public int calculate (int n){
        if(n == 0)
            return 1;
        else 
            return 2 * calculate(n-1);
    }

    public static void main(String[] args) {        
        System.out.println("Informe N: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Calculate obj = new Calculate();
        System.out.println("Resultado de 2^N: " + obj.calculate(n));
    }

}