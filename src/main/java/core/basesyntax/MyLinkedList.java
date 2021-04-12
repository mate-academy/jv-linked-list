package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    public MyLinkedList(List<T> list) {
        this();
        addAll(list);
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            addLast(value);
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T object : (T[]) list.toArray()) {
            addLast(object);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNode(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlinkNode(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                unlinkNode(node);
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
        return size == 0;
    }

    private void checkIndex(int index, int maxAvailableIndex) {
        if (index > maxAvailableIndex || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of list range");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index, size - 1);
        Node<T> nodeByIndex;
        if (index < (size / 2)) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private void addLast(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private T unlinkNode(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        T oldValue = node.value;
        node.value = null;
        size--;
        return oldValue;
    }
}
