package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    private void setHead(T value) {
        Node<T> oldHead = head;
        Node<T> newNode = new Node<>(null, value, oldHead);
        head = newNode;
        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }
        size++;
    }

    private T removeHead() {
        final T oldValue = head.value;
        if (size > 1) {
            head = head.next;
            head.prev = null;
            size--;
        } else {
            head.next = null;
            head.value = null;
            size = 0;
        }
        return oldValue;
    }

    private T removeTail() {
        final T oldValue = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;
        return oldValue;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            setHead(value);
            return;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
        Node<T> currentNode = head;
        if (currentNode == null) {
            return null;
        }
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        final T oldValue = currentNode.value;
        currentNode.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeHead();
        }
        if (index == (size - 1)) {
            return removeTail();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> removedNode = currentNode;
        currentNode.prev.next = removedNode.next;
        currentNode.next.prev = removedNode.prev;
        size--;

        return removedNode.value;
    }

    @Override
    public boolean remove(T value) {
        int index = 0;
        if (value == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    remove(index);
                    return true;
                }
                index++;
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (value.equals(x.value)) {
                    remove(index);
                    return true;
                }
                index++;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
