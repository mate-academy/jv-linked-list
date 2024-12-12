package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev,T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail,value,null);
        if (tail == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null,value, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        if (head == null & index == 0) {
            head = newNode;
            tail = newNode;
        }
        if (index == 0 & head != null) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        if (index == size) {
            newNode.prev = tail;
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        }
        if (index > 0 & index < size) {
            Node<T> current = head;
            for (int i = 0;i < index;i++) {
                current = current.next;
            }
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size();i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index;i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        T oldValue = current.value;
        for (int i = 0;i < index;i++) {
            current = current.next;
        }
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        for (int i = 0;i < index;i++) {
            current = current.next;
        }
        T oldValue = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0;i < size;i++) {
            current = current.next;
            if (object.equals(current.value)) {
                current.value = null;
                current.prev.next = current.next;
                current.next.prev = current.prev;
                return true;
            }
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
}
