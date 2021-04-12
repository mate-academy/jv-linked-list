package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, first);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            checkRange(index);
            Node<T> current = getNodeByIndex(index);
            newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            newNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkRange(index);
        Node<T> itemNode = getNodeByIndex(index);
        return itemNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> newNode;
        Node<T> deletedNode;
        if (index == 0) {
            newNode = first;
            deletedNode = newNode;
            first = first.next;
            size--;
            return deletedNode.item;
        }
        if (index == size - 1) {
            newNode = last;
            deletedNode = newNode;
            last = last.prev;
        } else {
            newNode = getNodeByIndex(index);
            deletedNode = new Node<>(newNode.prev, newNode.item, newNode.next);
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        size--;
        return deletedNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((get(i) == object || (get(i) != null && get(i).equals(object)))) {
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

    private Node<T> getNodeByIndex(int index) {
        checkRange(index);
        Node<T> itemByIndex;
        itemByIndex = first;
        for (int i = 0; i < index; i++) {
            itemByIndex = itemByIndex.next;
        }
        return itemByIndex;
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index"
                    + index + " out of bounds exception");
        }
    }
}
