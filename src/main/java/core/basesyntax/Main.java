package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40, 1);  // Вставляємо 40 на індекс 1

        System.out.println(list.get(0)); // 10
        System.out.println(list.get(1)); // 40
        System.out.println(list.get(2)); // 20

        list.remove(1); // Видаляємо 40

        System.out.println(list.get(1)); // 20
        System.out.println("Size: " + list.size()); // 3

        list.set(99, 1);
        System.out.println(list.get(1)); // 99
    }
}
