package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> oldNode = findNodeByIndex(index);
            Node<T> prev = oldNode.prev;
            Node<T> newNode = new Node<T>(value, oldNode, prev);
            oldNode.prev = newNode;
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T replacedValue = node.item;
        node.item = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeByIndex(index);
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == findNodeByIndex(i).item || t != null && t.equals(findNodeByIndex(i).item)) {
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

    private void exceptionCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        exceptionCheck(index);
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T value, Node<T> next, Node<T> prev) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
