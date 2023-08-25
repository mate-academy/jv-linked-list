package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> tailNew = new Node<>(null, value, null);
        if (isEmpty()) {
            tail = tailNew;
            head = tail;
        } else if (size >= 1) {
            tailNew.prev = tail;
            tail.next = tailNew;
            tail = tailNew;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        ifIndexOutOfBounds(index, size);
        if (size <= 1 || index == size) {
            add(value);
            return;
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> current = getNodeByIndex(index);
            Node<T> nodeNew = new Node<>(current.prev, value, current);
            nodeNew.prev.next = nodeNew;
            current.prev = nodeNew;
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
        for (T entry : list) {
            add(entry);
        }
    }

    @Override
    public T get(int index) {
        ifIndexOutOfBounds(index, size - 1);
        if (index == 0) {
            return head.value;
        }
        Node<T> current = getNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        ifIndexOutOfBounds(index, size - 1);
        T valueToReturn = head.value;
        if (index == 0) {
            head.value = value;
            return valueToReturn;
        }
        Node<T> current = getNodeByIndex(index);
        valueToReturn = current.value;
        current.value = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        ifIndexOutOfBounds(index, size - 1);
        Node<T> current = getNodeByIndex(index);
        T valueToReturn = current.value;
        unlink(current);
        size--;
        return valueToReturn;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || (current.value != null
                    && current.value.equals(object))) {
                unlink(current);
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
        return size == 0;
    }

    public void ifIndexOutOfBounds(int index, int upperBound) {
        if (index < 0 || index > upperBound) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }
    }

    private void unlink(Node<T> current) {
        if (current.equals(head) && head == tail) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = head.next;
            head.prev = null;
        } else if (current.equals(tail)) {
            tail = tail.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }
}
