package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node oldTail = tail;
        tail = new Node(value, null, tail);
        if (oldTail == null) {
            head = tail;
        } else {
            oldTail.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node node = getNode(index);
        Node newNode = new Node(value, node, node.prev);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        node.prev = newNode;
        size++;
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
                + ", list size: " + size
        );
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
        if (head == null) {
            return false;
        }
        Node runner = head;
        while (runner != null) {
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
        checkIndexBounds(index, "Cannot get node with index: " + index + ", list size: " + size);
        if (size - index > size >> 1) {
            Node runnerFromHead = head;
            for (int i = 0; i != index; i++) {
                runnerFromHead = runnerFromHead.next;
            }
            return runnerFromHead;
        } else {
            int indexFromEnd = size - index - 1;
            Node runnerFromTail = tail;
            for (int i = 0; i != indexFromEnd; i++) {
                runnerFromTail = runnerFromTail.prev;
            }
            return runnerFromTail;
        }
    }

    private void unlink(Node node) {
        if ((node.next != null) & (node.prev != null)) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        } else if ((node.prev == null) & (node.next != null)) {
            head = node.next;
        } else if (node.prev != null) {
            tail = node.prev;
        }
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
