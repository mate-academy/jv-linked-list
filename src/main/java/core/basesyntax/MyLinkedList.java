package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        linkedLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkedLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    void linkBefore(T e, Node<T> succ) {
        final Node<T> pred = succ.previous;
        final Node<T> newNode = new Node<>(pred, e, succ);
        succ.previous = newNode;

        if (pred == null) {
            firstNode = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        addAll(size, list);
    }

    public void addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return;
        }

        Node<T> predecessor;
        Node<T> successor;
        if (index == size) {
            successor = null;
            predecessor = lastNode;
        } else {
            successor = node(index);
            predecessor = successor.previous;
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") T e = (T) o;
            Node<T> newNode = new Node<>(predecessor, e, null);
            if (predecessor == null) {
                firstNode = newNode;
            } else {
                predecessor.next = newNode;
            }
            predecessor = newNode;
        }

        if (successor == null) {
            lastNode = predecessor;
        } else {
            predecessor.next = successor;
            successor.previous = predecessor;
        }
        size += numNew;
    }

    @Override
    public T get(int index) {
        checkedElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkedElementIndex(index);
        Node<T> x = node(index);
        T oldValue = x.item;
        x.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkedElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = firstNode; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = firstNode; x != null; x = x.next) {
                if (object.equals(x.item)) {
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

    private T unlink(Node<T> x) {

        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> previous = x.previous;

        if (previous == null) {
            firstNode = next;
        } else {
            previous.next = next;
            x.previous = null;
        }

        if (next == null) {
            lastNode = previous;
        } else {
            next.previous = previous;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    private void checkedElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(index + " out of bounds");
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private Node<T> node(int index) {
        Node<T> x;
        if (index < (size / 2)) {
            x = firstNode;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = lastNode;
            for (int i = size - 1; i > index; i--) {
                x = x.previous;
            }
        }
        return x;
    }

    private void linkedFirst(T e) {
        final Node<T> f = firstNode;
        final Node<T> newNode = new Node<>(null, e, f);
        firstNode = newNode;
        if (f == null) {
            lastNode = newNode;
        } else {
            f.previous = newNode;
        }
        size++;
    }

    private void linkedLast(T e) {
        final Node<T> l = lastNode;
        final Node<T> newNode = new Node<>(l, e, null);
        lastNode = newNode;
        if (l == null) {
            firstNode = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> previous;

        public Node(Node<E> previous, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
}
