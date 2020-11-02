package cn.edu.nju.java2020.homework7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

enum gender{
    MALE, FEMALE
}

public class Hulu extends CartonCharacter implements Sort<Hulu>, Comparable<Hulu> {

    public Hulu(String name, gender gender) {
        super(name, gender);
    }

    @Override
    public void sort(ArrayList<Hulu> huluBrothers, int option) {
        if(option == 0){
            shuffle(huluBrothers);
        }
        else {
            //实现了Comparator接口
            huluBrothers.sort(new Comparator<Hulu>() {
                @Override
                public int compare(Hulu o1, Hulu o2) {
                    return o1.name.compareTo(o2.name);
                }
            });
            if (option == 2) {
                Collections.reverse(huluBrothers);
            }
        }
        ArrayList<Hulu> female = new ArrayList<>(), male = new ArrayList<>();
        //按性别排成两队
        for(int i = 0; i < huluBrothers.size(); ++i){
            if (huluBrothers.get(i).gender == gender.FEMALE){
                female.add(huluBrothers.get(i));
            }
            else{
                    male.add(huluBrothers.get(i));
            }
        }
        System.out.print("女生队伍：");
        for(int i = 0; i < female.size(); ++i) {
            System.out.print(female.get(i).name);
            System.out.print(" ");
        }
        System.out.println();
        System.out.print("男生队伍：");
        for(int i = 0; i < male.size(); ++i) {
            System.out.print(male.get(i).name);
            System.out.print(" ");
        }
        System.out.println();
        return;
    }

    @Override
    public int compareTo(Hulu o) {
        return name.compareTo(o.name);
    }


}
