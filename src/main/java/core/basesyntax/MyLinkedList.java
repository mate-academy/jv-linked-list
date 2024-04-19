package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds " + index);
        }
        if (index == 0) {
            addFirst(value);
        } else {
            int count = 0;
            Node<T> current = head;
            while (count < index - 1) {
                current = current.next;
                count++;
            }
            Node<T> node = new Node<>(current, value, current.next);
            node.next = current.next;
            current.next = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexBound(index);
        if (index == 0) {
            return head.value;
        } else {
            int count = 0;
            Node<T> current = head;
            while (current != null) {
                current = current.next;
                count++;
                if (count == index) {
                    return current.value;
                }
            }
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexBound(index);
        Node<T> current = getNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNode(object);
        if (current != null) {
            unlink(current);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addFirst(T value) {
        Node<T> node = new Node<>(null,value,getNode(value));
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    private void checkIndexBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndexBound(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNode(T object) { //  A <- null -> B
        Node<T> current = head;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private T unlink(Node<T> node) {
        if (size == 1) {
            head = tail = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
