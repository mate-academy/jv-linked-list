package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private final Node falseHead;
    private int size;

    public MyLinkedList() {
        falseHead = new Node(null, null, null);
        falseHead.next = falseHead.prev = falseHead;
    }

    @Override
    public void add(T value) {
        insert(falseHead.prev, falseHead, value);
    }

    @Override
    public void add(T value, int index) {
        Node node = getNode(index);
        insert(node.prev, node, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T val : list) {
            add(val);
        }
    }

    @Override
    public T get(int index) {
        checkIndexBounds(index + 1, "Cannot get node with index equals size."
                + " Current size: " + index
                + ", list size: " + size);
        return getNode(index).val;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index + 1, "Cannot set node with index equals size."
                + " Current size: " + index
                + ", list size: " + size);
        Node node = getNode(index);
        T val = node.val;
        node.val = value;
        return val;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index + 1, "Cannot remove node with index equals size."
                + " Current size: " + index
                + ", list size: " + size);
        Node node = getNode(index);
        unlink(node);
        return node.val;
    }

    @Override
    public boolean remove(T object) {
        Node runner = falseHead.next;
        for (int i = 0; i != size; i++) {
            if (runner.val == object || object != null && object.equals(runner.val)) {
                unlink(runner);
                return true;
            }
            runner = runner.next;
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

    private Node getNode(int index) {
        checkIndexBounds(index, "Cannot get node with index: " + index
                + ", list size: " + size);
        if (size - index > size >> 1) {
            Node runnerFromHead = falseHead.next;
            for (int i = 0; i != index && i != size; i++) {
                runnerFromHead = runnerFromHead.next;
            }
            return runnerFromHead;
        } else {
            int indexFromEnd = size - index - 1;
            Node runnerFromTail = falseHead.prev;
            for (int i = 0; i != indexFromEnd && i != size; i++) {
                runnerFromTail = runnerFromTail.prev;
            }
            return runnerFromTail;
        }
    }

    private void insert(Node prev, Node next, T val) {
        prev.next = next.prev = new Node(val, next, prev);
        size++;
    }

    private void unlink(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    private void checkIndexBounds(int index, String errorMessage) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(errorMessage);
        }
    }

    private class Node {
        private T val;
        private Node next;
        private Node prev;

        public Node(T val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }
}
