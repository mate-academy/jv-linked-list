package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(tail, element, null);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T element, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size: " + size);
        }
        if (index == size) {
            add(element);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, element, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
            size++;
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> previousNode = nextNode.prev;
        Node<T> newNode = new Node<>(previousNode, element, nextNode);
        nextNode.prev = newNode;
        if (previousNode != null) {
            previousNode.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T element, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = element;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        final T removedValue = node.data;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T element) {
        Node<T> current = head;
        while (current != null) {
            if (element == current.data
                    || (element != null
                    && element.equals(current.data))) {

                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
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
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size: " + size);
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
