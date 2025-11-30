package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    transient int size = 0;
    transient Node<T> first;
    transient Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> currentLast = last;
        final Node<T> newNode = new Node<>(currentLast, value, null);
        last = newNode;
        if (currentLast == null)
            first = newNode;
        else
            currentLast.next = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size)
            linkLast(value);
        else
            linkBefore(value, getNode(index));
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
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
        return size == 0;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }


    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    void linkLast(T value) {
        final Node<T> currentLast = last;
        final Node<T> newNode = new Node<>(currentLast, value, null);
        last = newNode;
        if (currentLast == null)
            first = newNode;
        else
            currentLast.next = newNode;
        size++;
    }

    void linkBefore(T value, Node<T> nextIndNode) {
        final Node<T> pred = nextIndNode.prev;
        final Node<T> newNode = new Node<>(pred, value, nextIndNode);
        nextIndNode.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }
    
    Node<T> getNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++)
                node = node.next;
        } else {
            node = last;
            for (int i = size - 1; i > index; i--)
                node = node.prev;
        }
        return node;
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
}
