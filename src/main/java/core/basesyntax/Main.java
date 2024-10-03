package core.basesyntax;

public class Main {
    private static final String FIRST_ITEM = "First";
    private static final String SECOND_ITEM = "Second";
    private static final String THIRD_ITEM = "Third";
    private static final String NULL_ITEM = null;
    private static final String NEW_ITEM = "NewFirst";
    private static final String ANOTHER_NEW_ITEM = "NewSecond";
    private static MyLinkedListInterface<String> myLinkedList;

    public static void main(String[] args) {
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        myLinkedList.add(THIRD_ITEM, 2);
    }
}
