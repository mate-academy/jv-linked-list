package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

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

    public MyLinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last = new Node<>(last, value, null);
            last.prev.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
        } else {
            if (index == 0) {
                first = new Node(null, value, first);
                first.next.prev = first;
                size++;
            } else {
                Node<T> current = getNode(index);
                Node newNode = new Node(current.prev, value, current);
                newNode.prev.next = newNode;
                newNode.next.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNode(index);
        T old = node.item;
        node.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> element = getNode(index);
        Node<T> next = element.next;
        Node<T> previous = element.prev;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }
        size--;
        return element.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if ((t == null && t == current.item) || (t != null && t.equals(current.item))) {
                remove(i);
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index, size - 1);
        Node<T> result;
        if (index > size / 2) {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        } else {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        }
        return result;
    }
}
