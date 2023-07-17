package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        Node<T> firstNode = null;
        Node<T> lastNode = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(lastNode, value, null);
        if (firstNode == null) {
            firstNode = node;
            lastNode = node;
            size++;
            return;
        }
        if (firstNode.next == null) {
            firstNode.next = node;
        }
        lastNode.next = node;
        lastNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexInBounds(index);
        Node<T> currentNode = firstNode;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                Node<T> node = new Node<T>(currentNode.prev, value, currentNode);
                if (index == 0) {
                    firstNode = node;
                } else {
                    currentNode.prev.next = node;
                }
                currentNode.prev = node;
                size++;
                return;
            }
            currentNode = currentNode.next;
            i++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexInBounds(index);
        Node<T> currentNode = firstNode;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
            i++;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInBounds(index);
        Node<T> currentNode = firstNode;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                T oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue;
            }
            currentNode = currentNode.next;
            i++;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndexInBounds(index);
        if (index == 0) {
            if (firstNode.next != null) {
                firstNode.next.prev = null;
            }
            T oldValue = firstNode.value;
            firstNode = firstNode.next;
            size--;
            return oldValue;
        }
        if (index == size - 1) {
            lastNode.prev.next = null;
            T oldValue = lastNode.value;
            lastNode = lastNode.prev;
            size--;
            return oldValue;
        }
        Node<T> currentNode = firstNode.next;
        int i = 1;
        do {
            if (i == index) {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                T oldValue = currentNode.value;
                currentNode.value = currentNode.next.value;
                size--;
                return oldValue;
            }
            currentNode = currentNode.next;
            i++;
        } while (currentNode != lastNode);
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        int i = 0;
        while (currentNode != null) {
            if ((currentNode.value != null && currentNode.value.equals(object))
                    || currentNode.value == object) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
            i++;
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

    private void checkIndexInBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds " + size);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
