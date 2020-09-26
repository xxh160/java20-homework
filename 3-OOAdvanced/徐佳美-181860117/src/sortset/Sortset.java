package sortset;
import java.util.Random;
import calabash.*;

public interface Sortset{
    public default  void randLine(Calabash[] grandsons) {
        Random rand = new Random();
        int len = grandsons.length;
        for (int i = len; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(grandsons, randInd, i - 1);
        }
    }

    default void swap(Calabash[] grandsons, int a, int b) {
        Calabash temp = grandsons[a];
        grandsons[a] = grandsons[b];
        grandsons[b] = temp;
    }

};