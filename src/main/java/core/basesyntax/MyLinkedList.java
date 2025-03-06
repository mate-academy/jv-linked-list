package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tale;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tale = newNode;
        } else {
            tale.next = newNode;
            tale = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tale = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tale.next = newNode;
            tale = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T removeValue;
        if (index == 0) {
            removeValue = head.value;
            head = head.next;
            if (head == null) {
                tale = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removeValue = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tale = prev;
            }
        }
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        if (object == null ? head.value == null : object.equals(head.value)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (object == null ? current.next.value == null : object.equals(current.next.value)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
