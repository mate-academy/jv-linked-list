package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = this.tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index, size + 1);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null && index == 0) {
            head = this.tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
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
        for (T lists: list) {
            add(lists);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index, size);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index, size);
        T removeElement;
        if (index == 0) {
            removeElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else if (index == size - 1) {
            removeElement = tail.value;
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> current = getNodeByIndex(index);
            removeElement = current.value;
            current.next.prev = current.prev;
            current.prev.next = current.next;
        }
        size--;
        return removeElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (i == 0 && size == 1 && (object != null && object.equals(current.value))) {
                head = null;
                size--;
                return true;
            } else if (i == 0 && (object != null && object.equals(current.value))) {
                head = current.next;
                head.prev = null;
                size--;
                return true;
            } else if (i == size - 1 && (object != null && object.equals(current.value))) {
                tail = current.prev;
                tail.next = null;
                size--;
                return true;
            } else if (object == null && current.value == null) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            } else if (object != null && object.equals(current.value)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
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

    private void indexCheck(int index, int size) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Exception");
        }
    }
}
