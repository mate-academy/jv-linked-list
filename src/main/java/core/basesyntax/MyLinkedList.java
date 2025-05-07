package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private void linkLast(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> succ) {
        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node<T> noda;
            noda = first;
            for (int i = 0; i < index; i++) {
                noda = noda.next;
            }
            return noda;
        } else {
            Node<T> noda = last;
            for (int i = size - 1; i > index; i--) {
                noda = noda.prev;
            }
            return noda;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
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
        T value = node.value;
        node.value = null;
        size--;
        return value;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value,null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementOfList: list) {
            add(elementOfList);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> x = first;
        while (x != null) {
            if (x.value == object || (x.value != null && x.value.equals(object))) {
                unlink(x);
                return true;
            }
            x = x.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }
}
