package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            linkLast(t);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> node = getNode(index);
        T set = node.value;
        node.value = value;
        return set;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> nodeToRemove = getNode(index);
        removeNode(index);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        int count = 0;
        while (node != null) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                removeNode(count);
                size--;
                return true;
            }
            count++;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index < size >> 1) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void linkFirst(T value) {
        Node<T> node = first;
        Node<T> current = new Node<>(value, null, null);
        first = current;
        if (node == null) {
            last = current;
        } else {
            node.prev = current;
        }
    }

    private void linkLast(T value) {
        Node<T> node = last;
        Node<T> current = new Node<>(value, node, null);
        last = current;
        if (node == null) {
            first = current;
        } else {
            node.next = current;
        }
    }

    private void linkBefore(T value, Node<T> indexNode) {
        Node<T> node = indexNode.prev;
        Node<T> current = new Node<>(value, node, indexNode);
        indexNode.prev = current;
        if (node == null) {
            first = current;
        } else {
            node.next = current;
        }
    }

    private void removeNode(int index) {
        Node<T> current = getNode(index);
        Node<T> prev = current.prev;
        Node<T> next = current.next;

        if (prev == null && next == null) {
            first = null;
            last = null;
            return;
        }
        if (prev == null) {
            first = next;
            next.prev = null;
            current.next = null;
        }
        if (prev != null && next == null) {
            last = prev;
            prev.next = null;
            current.prev = null;
        }
        if (prev != null && next != null) {
            prev.next = current.next;
            next.prev = current.prev;
            current.next = null;
            current.prev = null;
        }

    }
}
