package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    public MyLinkedList() {
        size = 0;
    }


    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;

        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public T getFirst() {
        return head == null ? null : head.data;
    }

    public T getLast() {
        return tail == null ? null : tail.data;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> prevNode = currentNode.prev;
        Node<T> newNode = new Node<T>(prevNode, value, currentNode);
        prevNode.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).data;
    }


    @Override
    public T set(T value, int index) {
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.data;
        unlink(current);
        return oldValue;
    }


    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            boolean match = object == null ? current.data == null : object.equals(current.data);
            if (match) {
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

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = null;
        if (index < size / 2) {
            current = head;
            for (int currentIndex = 0; currentIndex < index; currentIndex++) {
                current = current.next;

            }
        } else {
            current = tail;
            for (int currentIndex = size - 1; currentIndex > index; currentIndex--) {
                current = current.prev;
            }
        }
        return current;
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

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
