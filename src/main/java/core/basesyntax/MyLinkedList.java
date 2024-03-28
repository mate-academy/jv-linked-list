package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> node = new Node<>(last, value, null);
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        Node<T> findedNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(findedNode.prev, value, findedNode);
        if (findedNode.prev == null) {
            first = newNode;
        } else {
            findedNode.prev. next = newNode;
        }
        findedNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> findedNode = getNodeByIndex(index);
        T oldNode = findedNode.item;
        findedNode.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        Node<T> findedNode = getNodeByIndex(index);
        unlink(findedNode);
        return findedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null) {
            if ((node.item == object) || (object != null && object.equals(node.item))) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + "| Size: " + size);
        }
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node != null) {
            final Node<T> next = node.next;
            final Node<T> prev = node.prev;

            if (prev == null) {
                first = next;
            }
            if (next == null) {
                last = prev;
            }
            if (prev != null) {
                prev.next = next;
                node.prev = null;
            }
            if (next != null) {
                next.prev = prev;
                node.next = null;
            }
            size--;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev,T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
