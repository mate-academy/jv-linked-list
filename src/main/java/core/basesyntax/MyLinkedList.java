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
        } else {
            isIndexValid(index);
            Node<T> currentNode = searchNodeByIndex(index);
            Node<T> node = new Node<T>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
            size++;
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
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = searchNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = searchNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        if (object == null) {
            for (MyLinkedList.Node<T> j = head; j != null; j = j.next, i++) {
                if (j.value == null) {
                    unlink(j);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> j = head; j != null; j = j.next, i++) {
                if (object.equals(j.value)) {
                    unlink(j);
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
        isIndexValid(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public void isIndexValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid");
        }
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

    public void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
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
