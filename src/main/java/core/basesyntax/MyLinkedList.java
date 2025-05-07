package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of size, Size: " + size);
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
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
        checkElementIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> oldNode = getNodeByIndex(index);
        T oldVal = oldNode.item;
        oldNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> removedElement = getNodeByIndex(index);
        unlink(removedElement);
        return removedElement.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedElement = head;
        while (removedElement != null) {
            if (removedElement.item == object
                    || object != null && object.equals(removedElement.item)) {
                unlink(removedElement);
                return true;
            }
            removedElement = removedElement.next;
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

    private void linkLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> tail) {
        Node<T> prev = tail.prev;
        Node<T> newNode = new Node<>(prev, value, tail);
        tail.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkElementIndex(index);
        if (index < (size >> 1)) {
            Node<T> headNode = head;
            for (int i = 0; i < index; i++) {
                headNode = headNode.next;
            }
            return headNode;
        } else {
            Node<T> value = tail;
            for (int i = size - 1; i > index; i--) {
                value = value.prev;
            }
            return value;
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is outside the array, "
                    + "size" + " is: " + size);
        }
    }
}
