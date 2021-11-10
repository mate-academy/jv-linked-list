package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> last;
    private Node<T> first;
    private int size = 0;

    private Node<T> node(int index) {
        if (index < size + 1) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public void add(T value) {
        Node<T> l = last;
        Node<T> node = new Node<>(l, value, null);
        last = node;
        if (l == null) {
            first = node;
        } else {
            l.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index,size + 1);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    private void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T e, Node<T> succ) {
        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        addAllWithIndex(size, list);
    }

    public void addAllWithIndex(int index, List<T> list) {
        Objects.checkIndex(index, size + 1);
        Object[] a = list.toArray();
        final int numNew = a.length;
        Node<T> pred;
        Node<T> succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            T e = (T) o;
            Node<T> newNode = new Node<>(pred, e, null);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index,size);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index,size);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index,size);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;

                }
            }
        }
        return false;
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        Node<T> next = x.next;
        Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (first == null) {
            return true;
        } else {
            return false;
        }
    }
}
