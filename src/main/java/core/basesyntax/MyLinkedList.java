package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(head, value, null);
            head.next = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
        } else if (isEmpty() || index == size) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            tail = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            tail = current.next;
            tail.prev = null;
        } else if (index == size - 1) {
            current = head;
            head = current.prev;
            head.next = null;
        } else {
            current = getNode(index);
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        T oldItem = current.item;
        size--;
        return oldItem;
    }

    @Override
    public boolean remove(T object) {
        int index = getNode(object);
        if (index >= 0) {
            remove(index);
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
        return head == null && tail == null;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = tail;
            int i = 0;
            while (current.next != null) {
                if (i == index) {
                    break;
                }
                current = current.next;
                i++;
            }
        } else {
            current = head;
            int i = size - 1;
            while (current.prev != null) {
                if (i == index) {
                    break;
                }
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private int getNode(T value) {
        Node<T> current = tail;
        int i = 0;
        do {
            if (current.item == value || current.item != null && current.item.equals(value)) {
                return i;
            }
            current = current.next;
            i++;
        } while (current.next != null);
        return -1;
    }
}
