package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Indeks jest poza zakresem listy: " + index);
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> current = head;
            int currentIndex = 0;
            while (current.next != null && currentIndex < index - 1) {
                current = current.next;
                currentIndex++;
            }
            if (current.next == null) {
                Node<T> newNode = new Node<>(current, value, null);
                current.next = newNode;
                tail = newNode;
            } else {
                Node<T> newNode = new Node<>(current, value, current.next);
                current.next.prev = newNode;
                current.next = newNode;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Indeks jest poza zakresem listy: " + index);
        }

        Node<T> current = head;
        int currentIndex = 0;

        while (current != null && currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        if (current != null && currentIndex == index) {
            return current.value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Indeks jest poza zakresem listy: " + index);
        }

        Node<T> current = head;
        int currentIndex = 0;

        while (current != null && currentIndex < index) {
            current = current.next;
            currentIndex++;
        }

        if (current != null && currentIndex == index) {
            T oldValue = current.value;
            current.value = value;
            return oldValue;
        }

        return null;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node<T> nodeToRemove = getNodeAtIndex(index);
        T valueToRemove = nodeToRemove.value;
        removeNode(nodeToRemove);
        return valueToRemove;
    }

    public boolean remove(T object) {
        if (object == null) {
            return removeNull();
        } else {
            return removeNonNull(object);
        }
    }

    private boolean removeNull() {
        Node<T> current = head;

        while (current != null) {
            if (current.value == null) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private boolean removeNonNull(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object.equals(current.value)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public int size() {
        int count = 0;
        Node<T> current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
