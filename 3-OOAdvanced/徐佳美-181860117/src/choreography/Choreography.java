
package choreography;


import calabash.*;
// import sortset.*;
import grandpa.*;

public class Choreography {
    public static void main(String[] args) {
        Grandpa grandpa = new Grandpa();
        Calabash grandsons[] = grandpa.createCalabash();
        System.out.println("Choreography");
        grandpa.randLine(grandsons);
        System.out.println("排序前  ");
        grandpa.report(grandsons);
        newSort(grandsons);
        System.out.println("排序后  ");
        grandpa.report(grandsons);
    }

    static void newSort(Calabash grandsons[]) {
        int len = grandsons.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (grandsons[i].compare(grandsons[j])) {
                    grandsons[i].swap(grandsons[j]);
                }
            }
        }
    }


};