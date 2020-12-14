package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> currentNode = getNodeByIndex(index);
        if (size == 0) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, currentNode);
            currentNode.prev = newNode;
            head = newNode;
            size++;
        } else if (size == index) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T currentItem = currentNode.item;
        currentNode.item = value;
        return currentItem;
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
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            size--;
            T value = currentNode.item;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            return value;
        }
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
