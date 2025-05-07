package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<T>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            checkIndex(index);
            Node<T> node = searchNodeByIndex(index);
            Node<T> newNode = new Node<T>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = searchNodeByIndex(index);
        T oldNode = node.value;
        node.value = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        for (node = head; node != null; node = node.next) {
            if (node.value == object || object != null && object.equals(node.value)) {
                unlink(node);
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

    private void checkIndex(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException("Invalid index");
    }

    private Node<T> searchNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
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
        Node<T> nodePrev = node.prev;
        Node<T> nodeNext = node.next;
        if (nodePrev == null) {
            head = nodeNext;
        } else {
            nodePrev.next = nodeNext;
        }
        if (nodeNext == null) {
            tail = nodePrev;
        } else {
            nodeNext.prev = nodePrev;
        }
        size--;
        return node.value;
    }
}
