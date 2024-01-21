package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            tail = newNode;
        } else {
            newNode = new Node<>(head, value, null);
            head.next = newNode;
        }
        head = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
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
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNode(object);
        if (current == null) {
            return false;
        }
        unlink(current);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            tail = node.next;
            tail.prev = null;
        } else if (node.next == null) {
            node = head;
            head = node.prev;
            head.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.next = null;
        node.prev = null;
        size--;
        return node.item;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index + " for size " + size);
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

    private Node<T> getNode(T value) {
        Node<T> current = tail;
        do {
            if (current.item == value || current.item != null && current.item.equals(value)) {
                return current;
            }
            current = current.next;

        } while (current.next != null);
        return null;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
