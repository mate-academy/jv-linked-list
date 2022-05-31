package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
        } else if (size == 1) {
            Node<T> newNode = new Node<>(null, value, head);
            head.next = newNode;
            newNode.prev = head;
            tail = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(null, value, tail);
            newNode.prev = tail;
            newNode.prev.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (size == 0) {
            add(value);
        }
        indexCheck(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(head, value, null);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;

        } else if (index < size) {
            Node<T> nodeOnIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeOnIndex, value, nodeOnIndex.prev);
            newNode.prev = nodeOnIndex.prev;
            nodeOnIndex.prev.next = newNode;
            nodeOnIndex.prev = newNode;
            newNode.next = nodeOnIndex;
            size++;
        } else {
            add(value);
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeWillReplaced = getNodeByIndex(index);
        T a = nodeWillReplaced.item;
        nodeWillReplaced.item = value;
        return a;
    }

    @Override
    public T remove(int index) {
        Node<T> newNode = getNodeByIndex(index);
        unlink(newNode);
        return newNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> actual = head;
        for (int i = 0; i <= size - 1; i++) {
            if (actual.item == object || actual.item != null && actual.item.equals(object)) {
                remove(i);
                return true;
            }
            actual = actual.next;
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

    static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> next, E item, Node<E> prev) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }

    public Node<T> getNodeByIndex(int index) {
        indexCheck(index);
        Node<T> actual;
        if (index < (size / 2)) {
            if (index == 0) {
                return head;
            }
            actual = head;
            for (int i = 0; i < index; i++) {
                actual = actual.next;
            }
        } else {
            actual = tail;
            for (int j = size - 1; j > index; j--) {
                actual = actual.prev;
            }
        }
        return actual;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            node.item = null;
            head = null;
            tail = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
            node.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
