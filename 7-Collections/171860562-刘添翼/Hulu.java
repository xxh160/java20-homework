// 作业：集合框架应用
// 请基于Java Collection Framework改写作业2：
// 假设葫芦娃的家族不断壮大，葫芦娃的名字/性别随机生成。
// 使用集合框架的容器类来存放葫芦娃们，并能够对他们按照名字的字典序进行排序（正序，反序，乱序等），然后打印。
// 将葫芦娃们按照性别排成两个队伍，同样按照名字的字典序排序、打印。
// 建议使用泛型防止队伍中混入蝎子精等异类，使用Iterable和Iterator接口，Comparable和Comparator接口完成排序，可以提供多种不同的实现。
// 完成后按照作业提交要求和流程，完成作业提交。

// comparable Hulu class, each Hulu has a index, differernt Hulu have differernt fingerprint(hashcode)
public class Hulu {

    public enum Gender {
        MALE, FEMAIL;
    }

    private String name;
    private Gender gender;

    public Hulu(final String nameIn, Gender genderIn) {
        name = nameIn;
        gender = genderIn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hulu other = (Hulu) obj;
        if (gender != other.gender)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Hulu [name=" + name + ", gender=" + gender + "]";
    }

    public static void main(final String[] args) {
        Hulu hulu = new Hulu("erf3t43f43g", Hulu.Gender.FEMAIL);
        System.out.println(hulu);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
