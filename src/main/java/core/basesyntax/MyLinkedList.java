package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(value);
        } else if (isIndexValid(index)) {
            Node<T> currentNode = searchNodeByIndex(index);
            Node<T> node = new Node<T>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
            size++;
        } else {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (isIndexValid(index)) {
            return searchNodeByIndex(index).value;
        } else {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    @Override
    public T set(T value, int index) {
        add(value, index);
        T oldValue = get(index + 1);
        remove(index + 1);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (isIndexValid(index)) {
            Node<T> currentNode = searchNodeByIndex(index);
            if (index == 0) {
                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    currentNode.next.prev = null;
                    head = currentNode.next;
                    currentNode.next = null;
                }
                size--;
            } else if (index == size - 1) {
                tail = currentNode.prev;
                currentNode.prev.next = null;
                currentNode.prev = null;
                size--;
            } else {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentNode.prev = null;
                currentNode.next = null;
                size--;
            }
            return currentNode.value;
        } else {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        if (object == null) {
            for (MyLinkedList.Node<T> x = head; x != null; x = x.next, i++) {
                if (x.value == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> x = head; x != null; x = x.next, i++) {
                if (object.equals(x.value)) {
                    remove(i);
                    return true;
                }
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

    public Node<T> searchNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    public void addFirst(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            head = node;
            tail = node;
        } else {
            node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        }
        size++;
    }

    static class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
