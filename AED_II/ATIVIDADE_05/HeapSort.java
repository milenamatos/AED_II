import java.util.Scanner;

public class HeapSort {
    public void maxHeapify(int[] list, int i, int len) {
        int l = (2*i)+1;
        int r = (2*i)+2;
        int max;

        if(l < len && list[l] > list[i]) {
            max = l;
        }
        else {
            max = i;
        }

        if(r < len && list[r] > list[max]) {
            max = r;
        }

        if(max != i){
            swap(list, i, max);
            maxHeapify(list, max, len);
        }
    }

    public void buildMaxHeap(int[] list){
        int n = list.length;
        for(int i = n/2 - 1; i >= 0; i--){
            maxHeapify(list, i, n);
        }
    }
    
    public int heapExtractMax(int[] list, int len){
        int max = list[0];
        list[0] = list[len];
        maxHeapify(list, 0, len);
        return max;
    }

    public void heapSort(int[] list) {
        buildMaxHeap(list);
        print(list);
        int n = list.length-1;
        int[] ordered = new int[list.length];
        while(n >= 0) {
            ordered[n] = heapExtractMax(list, n);
            n--;
        }
        print(ordered);
    }

    public void swap(int[] list, int i, int j) {        
        int aux = list[i];  
        list[i] = list[j];
        list[j] = aux;
    }

    public void print(int[] list){
        for(int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] list = new int[n];

        for(int i = 0; i < n; i++){
            list[i] = scan.nextInt();
        }

        scan.close();
        HeapSort hp = new HeapSort();
        hp.heapSort(list);
    }
}