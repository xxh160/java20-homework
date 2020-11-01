import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HuluGenderNameSortTest {
    public static class RandomEnum<E extends Enum<E>> {
        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }

    public static void main(String[] args) {
        RandomEnum<Hulu.Gender> randomEnum = new RandomEnum<Hulu.Gender>(Hulu.Gender.class);

        List<Hulu> list = new LinkedList<Hulu>();
        for (int i = 0; i < 10; ++i) {
            Hulu hulu = new Hulu(UUID.randomUUID().toString(), randomEnum.random());
            list.add(hulu);
        }


    }
}