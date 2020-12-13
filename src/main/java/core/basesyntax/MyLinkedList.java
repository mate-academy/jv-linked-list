package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return true;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            checkingIndex(index);
        }
        Node<T> currentNode = head;
        for (int i = 0; i <= size; i++) {
            if (index == size || index == 0 && size == 0) {
                add(value);
                return;
            }
            if (i == index) {
                Node<T> newNode = new Node<>(currentNode.prev, currentNode.item, currentNode.next);
                currentNode.item = value;
                currentNode.prev = newNode.prev;
                currentNode.next = newNode;
                newNode.prev = currentNode;
                size++;
                return;
            }
            currentNode = currentNode.next;
        }

    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkingIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= size; i++) {
            if (i == index) {
                return currentNode.item;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T currentItem = currentNode.item;
                currentNode.item = value;
                return currentItem;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index);
        if (index == 0) {
            T oldValue = head.item;
            if (size == 1) {
                head = null;
                tail = null;
                size = 0;
                return oldValue;
            }
            head.next.prev = null;
            head = head.next;
            size--;
            return oldValue;
        }
        if (index == size - 1) {
            tail.prev.next = null;
            T oldValue = tail.item;
            tail = tail.prev;
            size--;
            return oldValue;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                size--;
                T value = currentNode.item;
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                return value;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object || (currentNode.item != null
                    && currentNode.item.equals(object))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkingIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
