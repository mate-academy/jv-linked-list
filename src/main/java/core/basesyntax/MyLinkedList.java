package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value,null, null);
            tail = head;
        } else {
            tail.next = new Node<>(value, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size != index) {
            checkIndex(index);
        }
        if (index == size) {
            add(value);
        } else {
            relinkWithNewNode(value, index);
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
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T value) {
        int index = getIndex(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(E element, Node<E> prev, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds for the list size:"
                    + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = null;
        if (size != 0) {
            if (index <= size / 2) {
                node = runLoopFromHead(index);
            } else {
                node = runLoopFromTail(index);
            }
        }
        return node;
    }

    private void relinkWithNewNode(T value, int index) {
        Node<T> foundNode = getNodeByIndex(index);
        Node<T> prev = foundNode.prev;
        Node<T> newNode = new Node<>(value, prev, foundNode);
        foundNode.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
    }

    private Node<T> runLoopFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> runLoopFromTail(int index) {
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private int getIndex(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == value
                    || node.item != null && node.item.equals(value)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
        }
        node.prev = null;
        node.next = null;
        size--;
    }
}
