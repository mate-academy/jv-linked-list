package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> prev = getNode(index).prev;
            Node<T> newNode = new Node<>(prev, value, currentNode);
            currentNode.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size >> 1) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T oldItem = currentNode.item;
        currentNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return node.item;
        }
        if (index == size - 1) {
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node.item;
        }
        if (index == 0) {
            node.next.prev = null;
            head = node.next;
            size--;
            return node.item;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == get(i) || get(i).equals(t)) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException: "
                    + index + ", Size: " + size);
        }
    }

    private void addLast(T value) {
        Node<T> oldLast = tail;
        tail = new Node<>(oldLast, value, null);
        if (oldLast == null) {
            head = tail;
        } else {
            oldLast.next = tail;
        }
        size++;
    }
}
