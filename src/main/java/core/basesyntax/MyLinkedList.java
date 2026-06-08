package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
            return;
        }
        Node<T> succ = getNode(index);
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<>(pred, value, succ);
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
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldVal = node.value;
        node.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> curr = first;
        if (object == null) {
            while (curr != null) {
                if (curr.value == null) {
                    unlink(curr);
                    return true;
                }
                curr = curr.next;
            }
        } else {
            while (curr != null) {
                if (object.equals(curr.value)) {
                    unlink(curr);
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
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

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index for add: " + index);
        }
    }

}
