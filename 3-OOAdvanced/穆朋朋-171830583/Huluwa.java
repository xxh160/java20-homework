import java.util.ArrayList;
import java.util.Collections;


public class Huluwa extends Hulu {
    static int numHuluwa;
    static{
        numHuluwa=0;
    }

    String name;
    Huluwa() {
        super();
        numHuluwa++;
    }
    Huluwa(int color,String name) {
        super(color);
        numHuluwa++;
        this.name=name;
    }

    void sayName() {
        System.out.print(name);
    }
    public static void main(String[] args) {
        ArrayList<Huluwa> huluwaBros=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            switch(i) {
                case 0: huluwaBros.add(new Huluwa(i,"老大"));break;
                case 1: huluwaBros.add(new Huluwa(i,"老二"));break;
                case 2: huluwaBros.add(new Huluwa(i,"老三"));break;
                case 3: huluwaBros.add(new Huluwa(i,"老四"));break;
                case 4: huluwaBros.add(new Huluwa(i,"老五"));break;
                case 5: huluwaBros.add(new Huluwa(i,"老六"));break;
                case 6: huluwaBros.add(new Huluwa(i,"老七"));break;
                default: huluwaBros.add(new Huluwa(i,"老大"));break;
            }
        }
        for (Huluwa huluwa : huluwaBros) {
            huluwa.sayName();
        }
    }
}