package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        firstNode = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        }else {
            newNode.prev = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node <T> newNode = new Node<>(null, value, null);
        if (index == size)  {
            add(value);
        }
        if (index == 0) {
            newNode.next = firstNode;
            if (firstNode != null) {
                firstNode.prev = newNode;
            }
            firstNode = newNode;
        }
        if (index > 0 && index < size) {
            Node <T> oldNode = firstNode;
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node <T> result = firstNode; // - треьба метод зробити де шукає за індексом, цикл фор
        for (int i = 0; i <= index; i++) {
            result = result.next;
        }
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node <T> result = firstNode; // - треьба метод зробити де шукає за індексом, цикл фор
        for (int i = 0; i <= index; i++) {
            result = result.next;
        }
        T oldVal = result.value;
        result.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node <T> result = firstNode; // - треьба метод зробити де шукає за індексом, цикл фор
        for (int i = 0; i <= index; i++) {
            result = result.next;
        }
        result.prev.next = result.next;
        result.next.prev = result.prev;
        return (T) result;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    private static class Node <T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || size <= index) {
            throw new RuntimeException("Index out of range: " + index);
        }
    }
}
