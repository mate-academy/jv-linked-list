package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.previous = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
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
        if (index >= 0 && index <= size) {
            if (index == size) {
                add(value);
            } else {
                linkBefore(value, getNode(index));
            }
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new NullPointerException(" ");
        } else {
            for (T value : list) {
                add(value);
            }
        }
    }

    @Override
    public T get(int index) {
        if (indexIsValid(index)) {
            return getNode(index).item;
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public T set(T value, int index) {
        if (indexIsValid(index)) {
            Node<T> node = getNode(index);
            T old = node.item;
            node.item = value;
            return old;
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public T remove(int index) {
        if (indexIsValid(index)) {
            return unlink(getNode(index));
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = removeHelper(object);
        if (node != null) {
            unlink(node);
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

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
            return node;
        }
    }

    private void linkBefore(T e, Node<T> node) {
        final Node<T> previous = node.previous;
        final Node<T> newNode = new Node<>(previous, e, node);
        node.previous = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private boolean indexIsValid(int index) {
        return 0 <= index && index < size;
    }

    private T unlink(Node<T> object) {
        final T element = object.item;
        final Node<T> next = object.next;
        final Node<T> prev = object.previous;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            object.previous = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            object.next = null;
        }
        object.item = null;
        size--;
        return element;
    }

    private Node<T> removeHelper(T objects) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.item == objects) {
                return node;
            }
            if (objects != null && objects.equals(node.item)) {
                return node;
            }
        }
        return null;
    }
}
