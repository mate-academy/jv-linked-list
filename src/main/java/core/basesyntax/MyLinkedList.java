package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (last == null) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value,null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            if (index == 0) {
                first.prev = new Node<>(null, value, first);
                first = first.prev;
            } else {
                Node<T> node = getNodeByIndex(index);
                Node<T> newNode = new Node<>(node.prev, value, getNodeByIndex(index));
                node.prev.next = newNode;
                node.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t: list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T item = node.item;
        unlink(node);
        return item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == null && currentNode.item == null
                    || currentNode.item != null && currentNode.item.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            first = node.next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
    }
}
