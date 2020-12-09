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
        preLast.next = last;
        size++;
        return true;
    }
    //+
    @Override
    public void add(T value, int index) {
        if((isEmpty() && index == 0) || index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> add = new Node<>(null, value, first);
            first.prev = add;
            first = add;
            size++;
        } else {
            validateIndex(index);
            Node<T> nodeIndex = getNode(index);
            Node<T> prevNodeIndex = nodeIndex.prev;
            Node<T> add = new Node<>(prevNodeIndex, value, nodeIndex);
            prevNodeIndex.next = add;
            nodeIndex.prev = add;
            size++;
        }
    }
    //+
    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
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
        if (isEmpty()) {
            return false;
        }
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
        if (index >= size || index < 0 || isEmpty()) {
            throw new IndexOutOfBoundsException();
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
        while (iteration-- != index) {
            node = node.prev;
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        if (first == node) {
            first = first.next;
            first.prev = null;
        } else if (node == last) {
            last = last.prev;
            last.next = null;
        } else {

        }
    }
}
