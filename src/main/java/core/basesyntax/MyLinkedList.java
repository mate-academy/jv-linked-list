package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            Node<T> target = getNode(index);
            linkBefore(value, target);
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::linkLast);
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> target = getNode(index);
        T oldValue = target.value;
        target.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        unLink(target);
        return target.value;
    }

    @Override
    public boolean remove(T t) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.value == t || node.value != null && node.value.equals(t)) {
                unLink(node);
                return true;
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
        Node<T> oldLast = last;
        last = new Node<>(oldLast, value, null);
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> previous = target.prev;
        Node<T> newNode = new Node<>(previous, value, target);
        target.prev = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index < size >> 1) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void unLink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
            node.next = null;
        }
        size--;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }
}
