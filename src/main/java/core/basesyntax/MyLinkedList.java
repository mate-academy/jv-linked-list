package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        newNode.prev = last;
        if (last != null) {
            last.next = newNode;
        }
        if (first == null) {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value, null, null);
            Node<T> tempPoint = getByIndex(index);
            newNode.prev = tempPoint.prev;
            newNode.next = tempPoint;
            if (tempPoint.prev != null) {
                tempPoint.prev.next = newNode;
            }
            tempPoint.prev = newNode;
            if (newNode.prev == null) {
                first = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        Node<T> tempPoint = getByIndex(index);
        return tempPoint.data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> tempPoint = getByIndex(index);
        T result = tempPoint.data;
        tempPoint.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> tempPoint = getByIndex(index);
        if (tempPoint.next != null) {
            tempPoint.next.prev = tempPoint.prev;
        }
        if (tempPoint.prev != null) {
            tempPoint.prev.next = tempPoint.next;
        }
        if (tempPoint.prev == null) {
            first = tempPoint.next;
        }
        if (tempPoint.next == null) {
            last = tempPoint.prev;
        }
        size--;
        return tempPoint.data;
    }

    @Override
    public boolean remove(T object) {
        boolean result = false;
        Node<T> tempPoint = first;
        for (int i = 0; i < size; i++) {
            if (tempPoint != null && (tempPoint.data == object || (tempPoint.data != null
                    && tempPoint.data.equals(object)))) {
                remove(i);
                result = true;
                break;
            }
            tempPoint = tempPoint.next;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getByIndex(int index) {
        Node<T> tempPoint = first;
        if (index < 0 || index > size - 1 || tempPoint == null) {
            throw new IndexOutOfBoundsException("Invalid index : " + index + " Out bounds of List");
        }
        for (int i = 0; i < index; i++) {
            tempPoint = tempPoint.next;
        }
        return tempPoint;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T data;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }
}
