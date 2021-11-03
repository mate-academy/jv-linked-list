package core.basesyntax;

public class Main {
    private static final String FIRST_ITEM = "First";
    private static final String SECOND_ITEM = "Second";
    private static final String THIRD_ITEM = "Third";

    public static void main(String[] args) {
        MyLinkedListInterface<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        myLinkedList.add(THIRD_ITEM, 2);

        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);

        System.out.println(FIRST_ITEM + actualFirst);
        System.out.println(SECOND_ITEM + actualSecond);
        System.out.println(THIRD_ITEM + actualThird);

        System.out.println(myLinkedList.size());
    }
}
