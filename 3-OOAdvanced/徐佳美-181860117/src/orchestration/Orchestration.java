package orchestration;


import calabash.*;
import grandpa.*;

public class Orchestration{
    public static void main(String[] args) {
        Grandpa grandpa = new Grandpa();
        Calabash grandsons[] = grandpa.createCalabash();
        System.out.println("Orchestration");
        grandpa.randLine(grandsons);
        System.out.println("排序前  ");
        grandpa.report(grandsons);
        grandpa.newSort(grandsons);
        System.out.println("排序后  ");
        grandpa.report(grandsons);

    }
};