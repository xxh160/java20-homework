package java作业9_Testing;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class HuluwaSortingTest {
    private HuluwaSorting hs;
    private Huluwa h;
    private Sorting <Huluwa> s;
    public HuluwaSortingTest(){
        hs=null;
        h=null;
        s=new Sorting<Huluwa>();//s的初始化
    }

    @BeforeAll
    static void beforeAllMsg() {
        System.out.println(">>> Starting HuluwaSortingTest");
    }
    @AfterAll
    static void afterAllMsg() {
        System.out.println(">>> Finished HuluwaSortingTest");
    }
    @BeforeEach
    public void initialize(){
        System.out.println("Running Initialize()");
        s=new Sorting<Huluwa>();//s的初始化
        assertNotNull(s);//断言，判断s初始化成功，现在不是null
    }
    @Test
    public void insert_seven_huluwas(){
        System.out.println("Running Insert_seven_huluwas()");
        for(int i=0;i<7;i++){//老大-老七葫芦娃
            s.add_some(new Huluwa(i + 1));
            assertEquals(new Huluwa(i + 1).name,Integer.toString(i+1)+"brother");
        }
        assertEquals(s.p.size(), 7);
    }
    @Test
    public void insert_random_huluwas(){
        System.out.println("Running Insert_random_huluwas()");
        int num=5;
        for(int i = 0; i<num; i++){
            s.add_some(new Huluwa());
        }
        assertEquals(s.p.size(), num);
        for(Huluwa i:s.p){
            System.out.print(i.name+' '+i.sex+"   ");
        }
        System.out.println();
    }
    @Test(timeout = 2000)
    public void sorting(){
        insert_random_huluwas();
        System.out.println("乱序为：");
        for(Huluwa i:s.p){
            System.out.print(i.name+' '+" ");
        }
        System.out.println();
        System.out.println("正序为：");
        s.p.sort(s);
        for(Huluwa i:s.p){
            System.out.print(i.name+' '+" ");
        }
    }

}