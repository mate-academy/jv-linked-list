package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> currentNode;
    private Node<T> node;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, tail);
        } else if (size == 1) {
            tail = new Node<>(head, value, null);
            tail.prev = head;
            head.next = tail;
        } else {
            currentNode = new Node<>(tail, value, null);
            tail.next = currentNode;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index is not valid!");
        }
        if (index == size) {
            add(value);
            return;
        }
        currentNode = getNode(index);
        node = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev = node;
        if (node.prev != null) {
            node.prev.next = node;
        } else {
            head = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        currentNode = getNode(index);
        T returnValue = currentNode.item;
        currentNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        currentNode = getNode(index);
        if (size == 1) {
            size = 0;
            return currentNode.item;
        }
        if (currentNode == head) {
            head = currentNode.next;
            head.prev = null;
        } else if (currentNode == tail) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1) {
            size = 0;
            return true;
        }
        currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object || (object != null && object.equals(currentNode.item))) {
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

    private Node<T> getNode(int index) {
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = 1; i < size - index; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is not valid!");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T item;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
