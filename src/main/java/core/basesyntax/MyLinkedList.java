package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> node = new Node<>(null, value, null);

        if (index == 0) {
            node.next = head;
            if (head != null) {
                head.prev = node;
            }
            head = node;
            if (tail == null) {
                tail = node;
            }
        } else if (index == size()) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node<T> current = getNodeAtIndex(index);
            node.next = current;
            node.prev = current.prev;
            current.prev.next = node;
            current.prev = node;

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
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> current = getNodeAtIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    private void baseRemove(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
    }
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> current = getNodeAtIndex(index);
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null) {
                if (current.value == null) {
                    baseRemove(current);
                    return true;
                }
            } else if (object.equals(current.value)) {
                // If the object to remove is not null, use equals() to compare
                baseRemove(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        Node<T> current = head;
        int count=0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
