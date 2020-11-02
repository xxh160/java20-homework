import java.util.*;

public class Main {
    public static void main(String []args) {
        LinkedList<Huluwa> huluwaList = new LinkedList<Huluwa>();
        // String name = '';
        // boolean gender = 0;
        // for(int i=0;i<50;i++) {
        //     // name = ...
        //     // gender = ...
        //     huluwaList.add(new Huluwa(name,gender));
        Huluwa a = new Huluwa("9wa",true);
        huluwaList.add(a);
        huluwaList.add(new Huluwa("1wa",false));
        huluwaList.add(new Huluwa("2wa",true));
        huluwaList.add(new Huluwa("8wa",true));
        huluwaList.add(new Huluwa("3wa",false));
        huluwaList.add(new Huluwa("7wa",true));
        huluwaList.add(new Huluwa("6wa",false));
        huluwaList.add(new Huluwa("4wa",false));
        huluwaList.add(new Huluwa("5wa",true));
        
        for(Huluwa it: huluwaList) {
            it.count_off();
            System.out.println(' ');
        }
        System.out.println();
        HuluwaAscendingComparator hac = new HuluwaAscendingComparator();
        Collections.sort(huluwaList,hac);
        for(Huluwa it: huluwaList) {
            it.count_off();
            System.out.println(' ');
        }
        System.out.println();
        
        HuluwaDescendingComparator hdc = new HuluwaDescendingComparator();
        Collections.sort(huluwaList,hdc);
        for(Huluwa it: huluwaList) {
            it.count_off();
            System.out.println(' ');
        }
        
    }
}
