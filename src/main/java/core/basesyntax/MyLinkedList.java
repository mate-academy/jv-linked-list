package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final int SIZE_OF_EMPTY_LIST = 0;
    public static final int STARTING_INDEX_OF_LIST = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (size == SIZE_OF_EMPTY_LIST) {
            head = node;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
            node.prev = temp;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == STARTING_INDEX_OF_LIST) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> node = new Node<>(value);
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            node.next = temp;
            node.prev = temp.prev;
            temp.prev.next = node;
            temp.prev = node;
            size++;
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
        checkIndex(index);
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        if (temp == null) {
            return null;
        }
        return temp.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        T oldValue = temp.value;
        temp.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> temp = head;
        if (index == size) {
            tail = tail.prev;
            tail.next = null;
        }
        checkIndex(index);
        if (index == STARTING_INDEX_OF_LIST) {
            head = head.next;
            size--;
            return temp.value;
        }
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        unlink(temp);
        return temp.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if ((temp.value == null && object == null)
                    || (temp.value != null && temp.value.equals(object))) {
                if (i == STARTING_INDEX_OF_LIST) {
                    head = head.next;
                    size--;
                    return true;
                }
                unlink(temp);
                return true;
            } else {
                temp = temp.next;
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

    private void addFirst(T value) {
        if (size == STARTING_INDEX_OF_LIST) {
            add(value);
        } else {
            Node<T> node = new Node<>(value);
            node.next = head;
            head.prev = node;
            head = node;
            size++;
        }
    }

    private void checkIndex(int index) {
        if (index < STARTING_INDEX_OF_LIST || index >= size) {
            throw new IndexOutOfBoundsException("index " + index
                    + " was out of bounds LinkedList size: " + size);
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }
}
