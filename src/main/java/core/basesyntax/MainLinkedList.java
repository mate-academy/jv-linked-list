package core.basesyntax;

public class MainLinkedList {
    public static void main(String[] args) {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("BMW");
        myLinkedList.add("AMG");
        myLinkedList.add("VW");
        System.out.println(myLinkedList.size());
//        System.out.println(myLinkedList.get(2));
        System.out.println(myLinkedList.get(1));
        System.out.println(myLinkedList.get(0));
        myLinkedList.remove("AMG");
        System.out.println("------------------------>");
        System.out.println(myLinkedList.get(1));
        System.out.println(myLinkedList.get(0));
        System.out.println(myLinkedList.size());


    }
}
