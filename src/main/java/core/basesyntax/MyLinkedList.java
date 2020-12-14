package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (first == null) {
            first = last = node;
        } else {
            last = last.next = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        validateIndex(index);
        Node<T> prevNode = getNode(index - 1);
        Node<T> newNode = new Node<>(prevNode, value, prevNode.next);
        prevNode.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
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
        for (Node<T> node = first; node != null; node = node.next) {
            if (object == node.item || object != null && object.equals(node.item)) {
                removeNode(node);
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

    public void removeFirst() {
        if (size == 1) {
            first = last = null;
            return;
        }
        first = first.next;
        first.prev = null;
    }

    public void removeLast() {
        last = last.prev;
        last.next = null;
    }

    public void addFirst(T value) {
        Node<T> node = new Node<>(null, value, first);
        first.prev = node;
        first = node;
        size++;
    }

    private void removeNode(Node<T> node) {
        if (first == node) {
            removeFirst();
        } else if (node == last) {
            removeLast();
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
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
