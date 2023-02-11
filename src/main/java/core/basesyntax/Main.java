package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> two = new MyLinkedList<>();
        String FIRST_ITEM = "First";
        String SECOND_ITEM = "Second";
        String THIRD_ITEM = "Third";
        String NULL_ITEM = null;
        String NEW_ITEM = "NewFirst";
        String ANOTHER_NEW_ITEM = "NewSecond";

        two.add(FIRST_ITEM);
        two.add(SECOND_ITEM);

        two.remove(FIRST_ITEM);

    }
}
