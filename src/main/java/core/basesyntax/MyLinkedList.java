package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> tempNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = tempNode;
        } else {
            addLast(tempNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> tempNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = tempNode;
        } else if (index == 0) {
            tempNode.next = head;
            head.prev = tempNode;
            head = tempNode;
        } else {
            Node<T> currentNode = findNodeByIndex(index - 1);
            tempNode.next = currentNode.next;
            tempNode.prev = currentNode;
            currentNode.next = tempNode;
            if (tempNode.next != null) {
                tempNode.next.prev = tempNode;
            }
            if (index == size) {
                tail = tempNode;
            }
        }
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
        checkIndex(index, size);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T removedValue;
        if (index == 0) {
            removedValue = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            removedValue = currentNode.value;
            currentNode.prev.next = currentNode.next;
            if (currentNode.next != null) {
                currentNode.next.prev = currentNode.prev;
            }
            if (index == size - 1) {
                tail = currentNode.prev;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? current.value == null : object.equals(current.value)) {
                remove(i);
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

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index >= size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {

    }

    private void addLast(Node<T> node) {
        Node<T> currentNode = tail;
        currentNode.next = node;
        node.prev = currentNode;
        tail = node;
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
