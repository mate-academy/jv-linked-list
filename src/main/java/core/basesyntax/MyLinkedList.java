package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        size++;
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, value, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        size++;
        checkPositionIndex(index);

        Node<T> newNode = defaultNode(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size - 1) {
            node(size - 2).next = newNode;
            newNode.prev = node(size - 2);
            tail = newNode;
        } else {
            Node<T> prevNode = node(index - 1);
            newNode.next = prevNode.next;
            newNode.prev = prevNode;
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
        }

    }

    @Override
    public void addAll(List<T> list) {
        Node<T> pred;
        Node<T> succ;
        succ = null;
        pred = tail;

        for (T o : list) {
            Node<T> newNode = new Node<>(pred, o, null);
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

        size += list.size();
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> x;
        T oldVal;
        if (index == size) {
            x = tail;
            x.value = value;
            oldVal = x.value;
            return oldVal;
        }
        x = node(index);
        oldVal = x.value;
        x.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
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

    Node<T> node(int index) {
        if (index <= size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private Node<T> defaultNode(T value) {
        return new Node<>(null, value, null);
    }

    T unlink(Node<T> x) {
        final T element = x.value;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.value = null;
        size--;
        return element;
    }
}
