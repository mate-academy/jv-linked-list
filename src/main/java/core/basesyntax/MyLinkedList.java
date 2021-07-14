package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> tail;
    private Node<T> head;

    private static class Node<T> {
        private T element;
        private Node<T> perv;
        private Node<T> next;

        public Node(Node<T> perv, T element, Node<T> next) {
            this.perv = perv;
            this.element = element;
            this.next = next;
        }

    }

    @Override
    public void add(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        final Node<T> last = getNode(index).perv;
        final Node<T> newNode = new Node<>(last, value, getNode(index));
        getNode(index).perv = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> x = getNode(index);
        T oldVal = x.element;
        x.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.element)) {
                    unlink(x);
                    return true;
                }
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

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " out of range array");
        }
        Node<T> x;
        if (index < (size >> 1)) {
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.perv;
            }
        }
        return x;
    }

    private T unlink(Node<T> x) {
        final T element = x.element;
        final Node<T> next = x.next;
        final Node<T> perv = x.perv;

        if (perv == null) {
            head = next;
        } else {
            perv.next = next;
            x.perv = null;
        }

        if (next == null) {
            tail = perv;
        } else {
            next.perv = perv;
            x.next = null;
        }

        x.element = null;
        size--;
        return element;
    }
}
