package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }
    //+
    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
            return true;
        }
        Node<T> preLast = last;
        last = new Node<>(preLast, value, null);
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
    }
    //+
    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }
    //+
    @Override
    public T get(int index) {
        return getNode(index).item;
    }
    //+
    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T removed = node.item;
        node.item = value;
        return removed;
    }
    //+
    @Override
    public T remove(int index) {
        Node<T> remove = getNode(index);
        removeNode(remove);
        size--;
        return remove.item;
    }
    //+
    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (!node.item.equals(object)) {
            node = node.next;
        }
        if (node.item.equals(object)) {
            removeNode(node);
            size--;
            return true;
        }
        return false;
    }
    //+
    @Override
    public int size() {
        return size;
    }
    //+
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void validateIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Given index "
                    + index + " isn't within list size " + size);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Given index is less than 0");
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        Node<T> node;
        if (size - index > size >> 1) {
            int iteration = 0;
            node = first;
            while (iteration++ != index) {
                node = node.next;
            }
            return node;
        }
        int iteration = size - 1;
        node = last;
        while (iteration++ != index) {
            node = node.prev;
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        node.prev.next = node.next.prev;
        node.next.prev = node.prev.next;
    }
}
