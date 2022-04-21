package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
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
        if (first == null) {
            first = new Node<>(null, value, null);
        } else if (last == null) {
            last = new Node<>(first, value, null);
            first.next = last;
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToBounds(index, size);
        Node<T> node = getNodeByIndex(index);
        if (node == null) {
            add(value);
            return;
        }
        node.prev = new Node<>(node.prev, value, node);
        if (node.prev.prev == null) {
            first = node.prev;
        } else {
            node.prev.prev.next = node.prev;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndexToBounds(index, size - 1);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = get(index);
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexToBounds(index, size - 1);
        Node<T> node = getNodeByIndex(index);
        if (node.prev == null && node.next == null) {
            first = null;
            size--;
            return node.value;
        }
        if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
            size--;
            return node.value;
        }
        if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
            size--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexByValue(object);
        if (index >= 0) {
            remove(index);
            return true;
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

    private void checkIndexToBounds(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = first;
        int i = 0;
        while (i != index) {
            node = node.next;
            i++;
        }
        return node;
    }

    private int getIndexByValue(T value) {
        if (first != null) {
            Node<T> node = first;
            int i = 0;
            do {
                if (node.value == value || node.value != null && node.value.equals(value)) {
                    return i;
                }
                node = node.next;
                i++;
            } while (node.next != null);
        }
        return -1;
    }
}
