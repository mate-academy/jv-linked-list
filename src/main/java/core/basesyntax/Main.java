package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        System.out.println("Is empty: " + list.isEmpty());

        list.add(0);
        list.add(6);
        list.add(4);
        System.out.println("After add: " + listToString(list));

        list.set(3, 1);
        System.out.println("After set: " + listToString(list));

        list.add(1, 1);
        list.add(2, 2);
        System.out.println("After add by index: " + listToString(list));

        java.util.List<Integer> other = java.util.List.of(5, 6, 7);
        list.addAll(other);
        System.out.println("After add all: " + listToString(list));

        System.out.println("Element at index 2: " + list.get(2));

        list.remove(7);
        System.out.println("After remove by index: " + listToString(list));

        list.remove(Integer.valueOf(6));
        System.out.println("After remove by object: " + listToString(list));

        System.out.println("Size: " + list.size());

        System.out.println("Is empty now: " + list.isEmpty());
    }

    private static <T> String listToString(MyLinkedList<T> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
