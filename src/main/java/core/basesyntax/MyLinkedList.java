package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node node;
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    @Override
    public void add(T value) {
        node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
            size++;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
            size++;
        }

    }

    @Override
    public void add(T value, int index) {
        node = new Node(value);
        if (index == size) {
            add(value);
        } else {
            Node<T> current = findNodeByIndex(index);
            if (current == head) {
                current.prev = node;
                node.next = current;
                node.prev = null;
                head = node;
                size++;
            } else {
                current.prev.next = node;
                current.prev = node;
                node.prev = current.prev;
                node.next = current;
                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            node = new Node<>(null, list.get(i), null);
            tail.next = node;
            node.prev = tail;
            tail = node;
            size++;
        }

    }

    @Override
    public T get(int index) {
        Node<T> current = findNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        check(index);
        node = new Node(value);
        Node<T> current = findNodeByIndex(index);
        if (index == 0) {
            current.next.prev = node;
            node.prev = null;
            node.next = current.next;
            head = node;
        } else {
            node.prev = current.prev;
            node.next = current.next;
            current.next.prev = node;
            current.prev.next = node;
        }
        T oldItem = current.item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        check(index);
        Node<T> current = findNodeByIndex(index);
        T oldItem = current.item;
        if (index == 0) {
            current.next.prev = null;
            head = current.next;
        } else if (index == size - 1) {
            current.prev.next = null;
            current.prev = tail;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return oldItem;
    }

    @Override
    public boolean remove(T object) {
        boolean found = false;
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                if (current == head) {
                    head = current.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = current.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                found = true;
                size--;
                break;
            }
            current = current.next;
        }
        return found;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void check(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        check(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private static class Node<T> {
        private T item;
        private Node prev;
        private Node next;

        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public Node(T item) {
            this.item = item;
        }
    }
}


