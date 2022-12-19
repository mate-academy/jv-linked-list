package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            addHead(value);
        } else {
            addTail(value);
        }
        //size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addHead(value);
        } else if (index == size) {
            addTail(value);
        } else {
            checkIndex(index);
            addBefore(getNodeByIndex(index), value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
    }

    @Override
    public T get(int index) {
        Node<T> testNode = getNodeByIndex(index);
        return testNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> testNode = getNodeByIndex(index);
        T oldValue = testNode.value;
        testNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> testNode = getNodeByIndex(index);
        removeNode(testNode);
        return testNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> testNode = head;
        while (testNode != null) {
            if (testNode.value == object || (object != null && object.equals(testNode.value))) {
                removeNode(testNode);
                return true;
            }
            testNode = testNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index does not exist: " + index);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private T removeNode(Node<T> testNode) {
        T testValue = null;
        if (testNode == head) {
            if (size == 1) {
                testValue = head.value;
                head = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        } else if (testNode == tail) {
            testValue = tail.value;
            tail = tail.prev;
            tail.next = null;
        } else {
            testValue = testNode.value;
            testNode.prev.next = testNode.next;
            testNode.next.prev = testNode.prev;
        }
        size--;
        return testValue;
    }

    private void addHead(T value) {
        if (size == 0) {
            head = new Node<>(null, value, tail);
            tail = head;
            size++;
            return;
        }
        Node<T> testNode = new Node<>(null, value, head);
        head.prev = testNode;
        head = testNode;
        size++;
    }

    private void addTail(T value) {
        Node<T> testNode = new Node<>(tail, value, null);
        tail.next = testNode;
        tail = testNode;
        size++;
    }

    private void addBefore(Node<T> target, T value) {
        Node<T> testNode = new Node<>(target, value, target);
        target.prev.next = testNode;
        target.prev = testNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> testNode;
        if (index <= size / 2) {
            testNode = head;
            for (int i = 0; i < index; i++) {
                testNode = testNode.next;
            }
        } else {
            testNode = tail;
            for (int i = size - 1; i > index; i--) {
                testNode = testNode.prev;
            }
        }
        return testNode;
    }
}
