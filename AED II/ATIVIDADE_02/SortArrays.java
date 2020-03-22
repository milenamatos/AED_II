import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SortArrays {

    public ArrayList<Integer> list;

    public SortArrays(){
        list = new ArrayList<Integer>();
    }

    public void sort (){
        for(int i = 0; i < list.size() - 1; i++){
            for(int j = 0; j < list.size() - 1 - i; j++){
               list.iterator();
                if(list.get(j) > list.get(j+1)){
                    int temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
    }

    public void print(){        
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
    }

    public static void main(String[] args) {        

        
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();      
        int i;  
        SortArrays obj = new SortArrays();

        for(i = 0; i < n; i++){
            obj.list.add(in.nextInt());
        }
        
        n = in.nextInt();
        for(i = 0; i < n; i++){
            obj.list.add(in.nextInt());
        }
        in.close();
        
        obj.sort();
        obj.print();
    }

}
