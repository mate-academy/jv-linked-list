package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.element == object) {
                unlink(x);
                return true;
            }
        }
        if (object != null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.element)) {
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

    private void linkFirst(T value) {
        Node<T> h = head;
        Node<T> newNode = new Node<>(null, value, h);
        head = newNode;
        if (h == null) {
            tail = newNode;
        } else {
            h.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
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

    private void linkBefore(T value, Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> newNode = new Node<>(prev, value, node);
        node.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> x;
        int i;
        if (size == 0) {
            x = null;
        }
        if (index < (size >> 1)) {
            x = head;
            for (i = 0;i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private T unlink(Node<T> unlinkElement) {
        final T element = unlinkElement.element;
        final Node<T> next = unlinkElement.next;
        final Node<T> prev = unlinkElement.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            unlinkElement.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            unlinkElement.next = null;
        }
        unlinkElement.element = null;
        size--;
        return element;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element doesn't exist");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
