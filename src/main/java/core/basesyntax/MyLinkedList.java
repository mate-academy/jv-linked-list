package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, value, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            final Node<T> l = tail;
            final Node<T> newNode = new Node<>(l, value, null);
            tail = newNode;
            if (l == null) {
                head = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index; i++) {
                previous = previous.next;
            }
            final Node<T> pred = previous.prev;
            final Node<T> newNode = new Node<>(pred, value, previous);
            previous.prev = newNode;
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] a = list.toArray();
        final int numNew = a.length;
        Node<T> pred;
        Node<T> succ;
        succ = null;
        pred = tail;
        for (Object o : a) {
            T e = (T) o;
            Node<T> newNode = new Node<>(pred, e, null);
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null) {
            tail = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }
        size += numNew;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = node(index);
        T finVal = current.value;
        current.value = value;
        return finVal;
    }

    public Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            final T temp = head.value;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp;
        } else if (index == size - 1) {
            if (size == 0) {
                return null;
            } else if (size == 1) {
                T temp = head.value;
                head = tail = null;
                size = 0;
                return temp;
            } else {
                Node<T> current = head;
                for (int i = 0; i < size - 2; i++) {
                    current = current.next;
                }
                final T temp = tail.value;
                tail = current;
                tail.next = null;
                size--;
                return temp;
            }
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index; i++) {
                previous = previous.next;
            }
            Node<T> current = previous.next;
            previous.next = current;
            current.prev = previous.prev;
            previous.prev.next = current;
            T temp = previous.value;
            size--;
            return temp;
        }
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
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

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    public void unlink(Node<T> current) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current.prev == null) {
            head = current.next;
            head.prev = null;
        } else if (current.next == null) {
            tail = current.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    public void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
