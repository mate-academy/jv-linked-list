package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        final Node<T> oldTail = this.tail;
        final Node<T> newNode = new Node<>(oldTail, value, null);
        this.tail = newNode;
        if (oldTail == null) {
            this.head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            final Node<T> oldHead = this.head;
            final Node<T> newNode = new Node<>(null, value, oldHead);
            oldHead.prev = newNode;
            this.head = newNode;
            size++;
            return;
        }

        Node<T> nodeAtIndex = getNode(index);
        Node<T> prevNode = nodeAtIndex.prev;
        final Node<T> newNode = new Node<>(prevNode, value, nodeAtIndex);
        prevNode.next = newNode;
        nodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> current = head; current != null; current = current.next) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = head; current != null; current = current.next) {
                if (object.equals(current.item)) {
                    unlink(current);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            this.head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            this.tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
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
