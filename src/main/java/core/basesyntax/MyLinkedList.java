package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

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

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) { // сама перша нода
            head = node;
        } else { // інші ноди
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeUnderIndex = node(index);
            Node<T> newNode = new Node<>(nodeUnderIndex.prev, value, nodeUnderIndex);
            nodeUnderIndex.prev = newNode;
            if (newNode.prev == null) {
                head = newNode;
            } else {
                newNode.prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> removeNode = node(index); // нода яку затираємо
        T removeValue = removeNode.item;
        removeNode.item = value;
        return removeValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> removeNode = node(index);
        if (removeNode.prev == null && removeNode.next == null) {
            head = null;
            tail = null;
        } else if (removeNode.next != null && removeNode.prev != null) { // нода в середині
            removeNode.next.prev = removeNode.prev;
            removeNode.prev.next = removeNode.next;
        } else if (removeNode.next == null) { // ця нода є хвостом
            removeNode.prev.next = removeNode.next;
            tail = removeNode.prev;
        } else if (removeNode.prev == null) { // ця нода є головою
            removeNode.next.prev = removeNode.prev;
            head = removeNode.next;
        }
        size--;
        return unlink(removeNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || (object != null && object.equals(currentNode.item))) {
                remove(i);
                return true;
            } else {
                currentNode = currentNode.next;
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

    public T unlink(Node<T> node) {
        node.next = null;
        node.prev = null;
        T removeItem = node.item;
        node.item = null;
        return removeItem;
    }

    private void checkIndex(int index, int lastIndex) {
        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> node(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return currentNode;
            } else {
                currentNode = currentNode.next;
            }
        }
        return null;
    }
}
