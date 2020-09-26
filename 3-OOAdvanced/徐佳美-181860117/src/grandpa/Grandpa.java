

package grandpa;
import calabash.*;
// import java.util.Random;
import sortset.*;

public class Grandpa implements Sortset {

    public Calabash[] createCalabash() {
        Calabash[] grandsons = new Calabash[] {
            new Calabash("大娃", 7),
            new Calabash("二娃", 6), 
            new Calabash("三娃", 5),
            new Calabash("四娃", 4), 
            new Calabash("五娃", 3), 
            new Calabash("六娃", 2),
            new Calabash("七娃", 1), 
        };
        return grandsons;
    }

 


    public void report(Calabash[] grandsons) {
        for (Calabash huluwa : grandsons) {
            huluwa.sayName();
        }
        System.out.println();
    }

    public void newSort(Calabash[] grandsons) {
        int len = grandsons.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (grandsons[i].getAge() < grandsons[j].getAge()) {
                    swap(grandsons, i, j);
                }
            }
        }
    }

};