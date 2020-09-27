
package calabash;

public class Calabash {

    private String name;
    private int age;
    static int objCount;
    static{
       objCount = 0;
    }
    
    public Calabash() {    //default constructor
        Calabash.objCount++;
        name = "";
        age = 0;
    }

    public Calabash(String inName, int inAge) {    //overloading
        Calabash.objCount++;
        this.name = inName;
        this.age = inAge;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void sayName() {
        System.out.print(this.name + "  ");
    }

    public boolean compare(Calabash b) {
        return this.age < b.getAge();

    }

    public void swap(Calabash b) {
        String tempName = name;
        int tempAge = age;
        this.name = b.getName();
        this.age = b.getAge();
        b.name = tempName;
        b.age = tempAge;
    }

};
