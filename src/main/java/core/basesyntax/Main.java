package core.basesyntax;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final Cat THE_SAME_SECOND_CAT = new Cat("Barsik", "black");
    private static final Cat THE_SAME_THIRD_CAT = new Cat("Tom", "white");
    private static final Cat THE_SAME_FOURTH_CAT = new Cat("Leopold", "yellow");
    private static final Cat FIRST_CAT = new Cat("Fantic", "grey");
    private static final Cat SECOND_CAT = new Cat("Barsik", "black");
    private static final Cat THIRD_CAT = new Cat("Tom", "white");
    private static final Cat FOURTH_CAT = new Cat("Leopold", "yellow");
    private static final String FIRST_ITEM = "First";
    private static final String SECOND_ITEM = "Second";
    private static final String THIRD_ITEM = "Third";
    private static final String NULL_ITEM = null;
    private static final String NEW_ITEM = "NewFirst";
    private static final String ANOTHER_NEW_ITEM = "NewSecond";
    public static void main(String[] args) {
    LinkedList<String> myLinkedList = new LinkedList<>();
//        MyLinkedListInterface<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(NULL_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.remove(FIRST_ITEM);
        myLinkedList.remove(NULL_ITEM);
        myLinkedList.remove(THIRD_ITEM);
        myLinkedList.remove(FIRST_ITEM);
        System.out.println(myLinkedList);
    }

}
/*
        MyLinkedListInterface<Cat> cats = new MyLinkedList<>();
        cats.add(FIRST_CAT);
        cats.add(SECOND_CAT);
        cats.add(THIRD_CAT);
        cats.add(THIRD_CAT);
        cats.add(null);
        cats.add(FOURTH_CAT);
        cats.add(null);
        cats.remove(THE_SAME_SECOND_CAT);
        cats.remove(THE_SAME_THIRD_CAT);
        cats.remove(THE_SAME_FOURTH_CAT);
        cats.remove(null);
        System.out.println(cats.get(0));
 */