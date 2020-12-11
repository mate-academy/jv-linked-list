package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> last;
    private Node<T> first;
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> someNode = node(index);
        T oldItem = someNode.item;
        someNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> someNode = first;
        if (object == null) {
            while (someNode != null) {
                if (someNode.item == null) {
                    unlink(someNode);
                    return true;
                }
                someNode = someNode.next;
            }
        } else {
            while (someNode != null) {
                if (object.equals(someNode.item)) {
                    unlink(someNode);
                    return true;
                }
                someNode = someNode.next;
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

    private void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value,null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is incorrect!");
        }
    }

    private void linkBefore(T value, Node<T> current) {
        final Node<T> nodeBefore = current.prev;
        final Node<T> newNode = new Node<>(nodeBefore, value, current);
        current.prev = newNode;
        if (nodeBefore == null) {
            first = newNode;
        } else {
            nodeBefore.next = newNode;
        }
        size++;
    }

    public Node<T> node(int index) {
        Node<T> someNode;
        if (index < (size / 2)) {
            someNode = first;
            for (int i = 0; i < index; i++) {
                someNode = someNode.next;
            }
        } else {
            someNode = last;
            for (int i = size - 1; i > index; i--) {
                someNode = someNode.prev;
            }
        }
        return someNode;
    }

    private T unlink(Node<T> someNode) {
        final T value = someNode.item;
        final Node<T> next = someNode.next;
        final Node<T> prev = someNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            someNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            someNode.next = null;
        }

        someNode.item = null;
        size--;
        return value;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is incorrect!");
        }
    }
}
