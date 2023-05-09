package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final int DEFAULT_SIZE = 0;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.size = DEFAULT_SIZE;
    }

    @Override
    public void add(T value) {
        Node<T> thisNode = new Node<>(tail, value, null);
        if (head == null) {
            head = thisNode;
        } else {
            tail.next = thisNode;
        }
        tail = thisNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexCorrect(index);
        if (index == 0) {
            Node<T> thisNode = new Node<>(null, value, head);
            head.prev = thisNode;
            head = thisNode;
            size++;
            return;
        }

        Node<T> currentNode = getNode(index);
        Node<T> thisNode = new Node<>(currentNode.prev, value, currentNode);
        thisNode.prev.next = thisNode;
        thisNode.next.prev = thisNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (object == i.item || object != null && object.equals(i.item)) {
                unlink(i);
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

    private void checkIndexCorrect(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This value of index: "
                    + index
                    + ", is not correct");
        }
    }

    private Node<T> getNode(int index) {
        checkIndexCorrect(index);
        Node<T> node;
        if (index < (size / 2)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
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
        T element = node.item;
        node.item = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
