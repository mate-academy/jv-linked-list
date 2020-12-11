package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;

    private Node<T> last;
    private int size;
    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (first == null) {
            first = node;
            last = first;
        } else {
            last.next = node;
            last = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if ((isEmpty() && index == 0) || index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> add = new Node<>(null, value, first);
            first.prev = add;
            first = add;
            size++;
        } else {
            validateIndex(index);
            Node<T> node = getNode(index);
            Node<T> add = new Node<>(node.prev, value, node);
            node.prev.next = add;
            node.prev = add;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T removed = node.item;
        node.item = value;
        return removed;
    }

    @Override
    public T remove(int index) {
        Node<T> remove = getNode(index);
        removeNode(remove);
        return remove.item;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return true;
        } else if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (null == node.item) {
                    removeNode(node);
                    return true;
                }
            }
            return false;
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    removeNode(node);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    public void removeFirst() {
        if (size == 1) {
            T item = first.item;
            first = null;
            last = null;
            return;
        }
        Node<T> node = first;
        first = first.next;
        first.prev = null;
    }

    public void removeLast() {
        Node<T> node = last;
        last = last.prev;
        last.next = null;
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
            removeFirst();
            size--;
        } else if (node == last) {
            removeLast();
            size--;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
