package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldNode = tail;
        Node<T> newNode = new Node<>(oldNode,value,null);
        tail = newNode;
        if (oldNode != null) {
            oldNode.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index,size + 1);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null,value,head);
            head = head.prev;
            size++;
        } else {
            Node<T> node = getNodeByIndex(index);
            Node<T> midValue = new Node<>(node.prev,value,node);
            midValue.next.prev = midValue;
            midValue.prev.next = midValue;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T element = node.item;
        node.item = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.item;
        unlink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index,int upperBound) {
        if (index >= upperBound || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " don't exist!");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index,size);
        Node<T> current = head;
        if ((size / 2) > index) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        size--;
    }

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
}
