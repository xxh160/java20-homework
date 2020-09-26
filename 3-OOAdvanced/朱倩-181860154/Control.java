package homework3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class HuLuWa {
  private String name;
  private int rank;

  HuLuWa(String name, int rank) {
    this.name = name;
    this.rank = rank;
  }

  public boolean youngerThan(HuLuWa h) {
    return this.rank > h.rank;
  }

  public void exchangeWith(HuLuWa h) {
    String tempname = h.name;
    h.name = this.name;
    this.name = tempname;
    int temprank = h.rank;
    h.rank = this.rank;
    this.rank = temprank;
  }

  public int getRank() {
    return rank;
  }

  public void tellName() {
    System.out.print(name + " ");
  }
}

class Grandpa {

  public boolean needExchange(HuLuWa h1, HuLuWa h2) {
    return h1.getRank() > h2.getRank();
  }

  public void exchange(List<HuLuWa> queue, int pos1, int pos2) {
    HuLuWa temp = queue.get(pos1);
    queue.set(pos1, queue.get(pos2));
    queue.set(pos2, temp);
  }
}

interface Sort {
  void orchestration(Grandpa grandpa, List<HuLuWa> queue);
  void choreography(List<HuLuWa> queue);
}

class BubbleSort implements Sort {

  public void orchestration(Grandpa grandpa, List<HuLuWa> queue) {
    for (int i = 1; i < queue.size(); i++) {
      for (int j = queue.size() - 1; j >= i; j--) {
        if (grandpa.needExchange(queue.get(j - 1), queue.get(j))) {
          grandpa.exchange(queue, j - 1, j);
        }
      }
    }
  }

  public void choreography(List<HuLuWa> queue) {
    
    for (int i = 1; i < queue.size(); i++) {
      for (int j = queue.size() - 1; j >= i; j--) {
        if (queue.get(j - 1).youngerThan(queue.get(j))) {
          queue.get(j - 1).exchangeWith(queue.get(j));
        }
      }
    }
    
  }
}

public class Control {

  private static void initQueue(List<HuLuWa> queue) {
    queue.clear();
    for (int i = 1; i <= 7; i++) {
      String name = "";
      switch (i) {
        case 1:
          name = "老大";
          break;
        case 2:
          name = "老二";
          break;
        case 3:
          name = "老三";
          break;
        case 4:
          name = "老四";
          break;
        case 5:
          name = "老五";
          break;
        case 6:
          name = "老六";
          break;
        case 7:
          name = "老七";
          break;
      }
      queue.add(new HuLuWa(name, i));
    }
    Random r = new Random();
    Collections.shuffle(queue, r);
  }

  private static void printQueue(List<HuLuWa> queue) {
    for (HuLuWa huluwa : queue) {
      huluwa.tellName();
    }
    System.out.println();
  }

  private static void sort(Sort sort, Grandpa grandpa, List<HuLuWa> queue) {
    System.out.println("----------orchestration----------");
    initQueue(queue);
    System.out.println("Original queue:");
    printQueue(queue);
    sort.orchestration(grandpa, queue);
    System.out.println("After sorting:");
    printQueue(queue);
    System.out.println();
    System.out.println("----------choreography----------");
    initQueue(queue);
    System.out.println("Original queue:");
    printQueue(queue);
    sort.choreography(queue);
    System.out.println("After sorting:");
    printQueue(queue);
  }

  public static void main(String[] args) {
    List<HuLuWa> queue = new ArrayList<>();
    Grandpa grandpa = new Grandpa();
    BubbleSort bubblesort = new BubbleSort();
    sort(bubblesort,grandpa,queue);
  }
}
