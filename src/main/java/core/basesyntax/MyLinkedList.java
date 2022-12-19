package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addHead(value);
        } else {
            addTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (index == 0) {
                addHead(value);
            } else if (index == size) {
                addTail(value);
            } else {
                addMidNode(getNodeIndex(index), value);
            }
            size++;
        } else {
            throw new IndexOutOfBoundsException("This index does not exist");
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
        if (indexExists(index)) {
            Node<T> testNode = getNodeIndex(index);
            return testNode.value;
        }
        throw new IndexOutOfBoundsException("Couldn't find requested item by the index");
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < size) {
            Node<T> testNode = getNodeIndex(index);
            T oldValue = testNode.value;
            testNode.value = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException("This index does not exist");
    }

    @Override
    public T remove(int index) {
        if (indexExists(index)) {
            Node<T> testNode = getNodeIndex(index);
            removeNode(testNode);
            return testNode.value;
        }
        throw new IndexOutOfBoundsException("This index does not exist");
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

    private boolean indexExists(int index) {
        return index >= 0 && index < size;
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
            head = new Node<>(value, null, tail);
            tail = head;
            return;
        }
        Node<T> testNode = new Node<>(value, null, head);
        head.prev = testNode;
        head = testNode;
    }

    private void addTail(T value) {
        Node<T> testNode = new Node<>(value, tail, null);
        tail.next = testNode;
        tail = testNode;
    }

    private void addMidNode(Node<T> prevNode, T value) {
        Node<T> testNode = new Node<>(value, prevNode.prev, prevNode);
        prevNode.prev.next = testNode;
        prevNode.prev = testNode;
    }

    private Node<T> getNodeIndex(int index) {
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
