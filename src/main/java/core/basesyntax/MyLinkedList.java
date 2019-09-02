package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        T item;
        Node next;
        Node prev;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(value, null, null);
            last = first;
            size = 1;
        } else {
            Node newLast = new Node(value, last, null);
            last.next = newLast;
            last = newLast;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        if (index == size) {
            add(value);
        } else {
            addValue(value);
        }
    }

    private void addValue(T value) {
        Node<T> firstNode = first;
        Node<T> newNode = new Node<>(value, null, firstNode);
        first = newNode;
        if (firstNode == null) {
            last = newNode;
        } else {
            firstNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getElem(index).item;
    }

    public Node<T> getElem(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeIndex = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                nodeIndex = nodeIndex.next;
            }
        } else {
            nodeIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeIndex = nodeIndex.prev;
            }
        }
        return nodeIndex;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        getElem(index).item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removeElem = getElem(index);
        Node<T> next = removeElem.next;
        Node<T> previous = removeElem.prev;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }
        T removedData = removeElem.item;
        removeElem.item = null;
        size--;
        return removedData;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getElem(i).item.equals(t)) {
                T deletedItem = getElem(i).item;
                remove(i);
                return deletedItem;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
