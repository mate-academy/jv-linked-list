package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX_ERROR_MESSAGE = "Invalid index";

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirstNode(value);
        } else {
            addLastNode(value);
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        indexValidationAddMethod(index);
        if (isEmpty()) {
            addFirstNode(value);
        } else if (index == size) {
            addLastNode(value);
        } else {
            link(getNode(index), value);
        }
        ++size;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        T pastValue = getNode(index).value;
        getNode(index).value = value;
        return pastValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        Node<T> node = getNode(index);
        unlink(node);
        --size;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            T value = get(i);
            if (object != null && object.equals(value) || object == value) {
                remove(i);
                return true;
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
        return tail == null;
    }

    private Node<T> findNodeFromHead(int index) {
        int currentIndex = 0;
        Node<T> node = head;

        while (currentIndex++ != index) {
            node = node.next;
        }
        return node;
    }

    private Node<T> findNodeFromTail(int index) {
        int currentIndex = size - 1;
        Node<T> node = tail;

        while (currentIndex-- != index) {
            node = node.prev;
        }
        return node;
    }

    private Node<T> getNode(int index) {
        return (index > size / 2) ? findNodeFromTail(index) : findNodeFromHead(index);
    }

    private void link(Node<T> destNode, T value) {
        Node<T> newNode = new Node<>(destNode.prev, value, destNode);
        if (head == destNode) {
            head = newNode;
        } else {
            destNode.prev.next = newNode;
        }
        destNode.prev = newNode;
    }

    private void unlink(Node<T> node) {
        if (head == node) {
            head = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        } else if (tail == node) {
            tail = node.prev;
            node.prev.next = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void addFirstNode(T value) {
        head = new Node<>(null, value, null);
        tail = head;
    }

    private void addLastNode(T value) {
        tail = new Node<>(tail, value, null);
        tail.prev.next = tail;
        if (head.next == null) {
            head.next = tail;
        }
    }

    private void indexValidationAddMethod(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_ERROR_MESSAGE);
        }
    }

    private void indexValidation(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_ERROR_MESSAGE);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
