package core.basesyntax;

public class LinkedList<K, V> {

    private Element first;

    private int size;

    public LinkedList() {
        first = null;
    }

    public void insert(K key, V value) {
        Element element = new Element(key, value);
        element.next = first;
        first = element;
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public V findValue(K key) {
        Element<K, V> current = first;
        if (current == null) {
            return null;
        }
        while (current.key != key) {
            if (current.next == null) {
                return null;
            } else {
                current = current.next;
            }
        }
        return current.value;
    }

    public Element remove(K key) {
        if (first == null) {
            return null;
        }
        Element<K, V> current = first;
        Element<K, V> previous = first;
        while (current.key != key) {
            if (current.next == null)
                return null;
            else {
                previous = current;
                current = current.next;
            }
        }
        if (current == first)
            first = first.next;
        else
            previous.next = current.next;
        return current;
    }

    public void displayList() {
        System.out.print("List (first-->last): ");
        Element current = first;
        while (current != null) {
            current.showElement();
            current = current.next;
        }
        System.out.println("");
    }

    public void clear() {
        first = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public class Element<K, V> {
        private K key;
        private V value;
        private Element next;

        Element(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void showElement() {
            System.out.print("{" + key + ", " + value + "} ");
        }
    }

}
