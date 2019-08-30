package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;

    private Node<T> last;

    private int size;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(value, null, null);
            last = first;
        } else {
            Node newLast = new Node(value, last, null);
            last.next = newLast;
            last = newLast;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            linkLast(value);
        } else {
            link(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        Node node = node(index);
        node.item = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public T remove(T t) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.item.equals(t)) {
                return unlink(node);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void link(T value, Node<T> x) {
        Node<T> previous = x.previous;
        Node<T> newNode = new Node(value, previous, x);
        x.previous = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> previous = last;
        Node<T> newNode = new Node(value, previous, null);
        last = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> n) {
        final T value = n.item;
        final Node<T> next = n.next;
        final Node<T> previous = n.previous;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            n.previous = null;
        }
        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            n.next = null;
        }
        n.item = null;
        size--;
        return value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> node(int index) {
        Node element;
        if (index < (size >> 1)) {
            element = first;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = last;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
        }
        return element;
    }

    private static class Node<T> {
        T item;
        Node next;
        Node previous;

        Node(T item, Node previous, Node next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
}
