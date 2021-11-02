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

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (isPositionIndex(index)) {
            if (index == size) {
                linkLast(value);
            } else {
                linkBefore(value, node(index));
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
            for (T t : list) {
                add(t);
            }
        }

    }

    @Override
    public T get(int index) {
        if (isElementIndex(index)) {
            return node(index).item;
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public T set(T value, int index) {
        if (isElementIndex(index)) {
            Node<T> node = node(index);
            T old = node.item;
            node.item = value;
            return old;
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public T remove(int index) {
        if (isElementIndex(index)) {
            return unlink(node(index));
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public boolean remove(T object) {
        if (object != null) {
            for (Node<T> x = firstNode; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = firstNode; x != null; x = x.next) {
                if (x.item == null) {
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
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = firstNode;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = lastNode;
            for (int i = size - 1; i > index; i--) {
                x = x.previous;
            }
            return x;
        }
    }

    private void linkLast(T e) {
        final Node<T> last = lastNode;
        final Node<T> newNode = new Node<>(last, e, null);
        lastNode = newNode;
        if (last == null) {
            firstNode = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T e, Node<T> succ) {
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

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.previous;
        if (prev == null) {
            firstNode = next;
        } else {
            prev.next = next;
            x.previous = null;
        }
        if (next == null) {
            lastNode = prev;
        } else {
            next.previous = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }
}
