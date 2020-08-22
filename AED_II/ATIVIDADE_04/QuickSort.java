import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickSort {
    public List<Person> listPeople = new ArrayList<Person>();
    public boolean stable = true;
    public class Person {
        public String Name;
        public int Age;
        public int index;
        public Person(String name, int age, int i){
            this.Name = name;
            this.Age = age;
            this.index = i;
        }
    }

    public int partition(List<Person> list, int p, int r) {
        // get medium

        int media = (p + r) / 2;
        Person a = list.get(p);
        Person b = list.get(media);
        Person c = list.get(r);

        int index = 0;
        if (a.Age < b.Age) {
            if (b.Age < c.Age)
                index = media;
            else {
                if (a.Age < c.Age)
                    index = r;
                else
                    index = p;
            }
        } else {
            if (c.Age < b.Age)
                index = media;
            else {
                if (c.Age < a.Age)
                    index = r;
                else
                    index = p;
            }
        }

        swap(list, index, r);

        // quick sort
        Person x = list.get(r);
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (list.get(j).Age <= x.Age) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, r);
        return (i + 1);
    }

    public void swap(List<Person> list, int i, int j) {
        
        Person a = list.get(i);
        Person b = list.get(j);

        for(Person p: list){
            if(p.Age == a.Age && p.index != j || p.Age == b.Age && p.index != i)
                stable = false;
        }

        list.set(i, b);
        list.set(j, a);
    }

    public void quickSort(List<Person> list, int p, int r) {
        if (p < r) {
            int q = partition(list, p, r);
            quickSort(list, p, q - 1);
            quickSort(list, q + 1, r);
        }
    }

    public void fill(int quantity, Scanner scanner){
        for(int i = 0; i < quantity; i++){
            String name = scanner.next();
            int age = scanner.nextInt();    
            listPeople.add(new Person(name, age, i));
        }
    }

    public void printList(int starter, int limit){
        System.out.println(stable ? "yes" : "no");

        int max = starter + limit;
        for(int i = starter; i < max; i++){
            System.out.println(listPeople.get(i).Name + " " + listPeople.get(i).Age);
        }        
    }

    public static void main(String[] args) {
        QuickSort qs = new QuickSort();
        Scanner scanner = new Scanner(System.in);

        int quantity = scanner.nextInt();
        qs.fill(quantity, scanner);

        int starter = scanner.nextInt();
        int limit = scanner.nextInt();
        scanner.close();

        qs.quickSort(qs.listPeople, 0, quantity - 1);
        qs.printList(starter-1, limit);
    }
}