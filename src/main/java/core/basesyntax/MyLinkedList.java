package core.basesyntax;

import core.basesyntax.util.ObjectUtil;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prevNode, T value, Node<T> nextNode) {
            prev = prevNode;
            item = value;
            next = nextNode;
        }
    }

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (size == 0) {
            head = current;
        } else {
            current.prev = tail;
            tail.next = current;
        }
        tail = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        canAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNodeAtIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public T get(int index) {
        indexValidator(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        if (index == 0) {
            final T item = head.item;
            head.prev = null;
            head = head.next;
            if (size == 1) {
                tail = null;
            }
            size--;
            return item;
        }
        Node<T> current = getNodeAtIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        if (head.item == object) {
            head = head.next;
            if (head != null) {
                head.prev = null;
                tail = null;
            }
            size--;
            return true;
        }
        Node<T> current = head;
        while (!ObjectUtil.equals(current.item, object)) {
            if (current.next == null) {
                return false;
            }
            current = current.next;
        }
        unlink(current);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void canAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add element with invalid index");
        }
    }

    private void indexValidator(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index value: " + index);
        }
    }
}
