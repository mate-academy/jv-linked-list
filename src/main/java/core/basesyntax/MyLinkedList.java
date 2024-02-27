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
        Node<T> tempNode = new Node<>(null, value, null);
        if (isEmpty()) {
            addNodeToHead(tempNode);
        } else {
            addNodeToTail(tempNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index,true);
        Node<T> tempNode = new Node<>(null, value, null);
        if (index == 0) {
            addNodeToHead(tempNode);
        } else {
            Node<T> currentNode = findNodeByIndex(index - 1);
            tempNode.next = currentNode.next;
            tempNode.prev = currentNode;
            currentNode.next = tempNode;
        }
        if (tempNode.next != null) {
            tempNode.next.prev = tempNode;
        }
        if (index == size) {
            addNodeToTail(tempNode);
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
        checkIndex(index,false);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index,false);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index,false);
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
            unlink(currentNode);
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? current.value == null : object.equals(current.value)) {
                unlink(current);
                size--;
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

    private void checkIndex(int index, boolean allowEqualSize) {
        if (index < 0 || (allowEqualSize ? index > size : index >= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        boolean isRightSide = index >= size / 2;
        Node<T> current = isRightSide ? tail : head;
        int iterations = isRightSide ? size - index - 1 : index;
        for (int i = 0; i < iterations; i++) {
            current = isRightSide ? current.prev : current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
    }

    private void addNodeToTail(Node<T> newNode) {
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    private void addNodeToHead(Node<T> newNode) {
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
    }

    static class Node<T> {
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
