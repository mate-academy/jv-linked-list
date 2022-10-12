package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @Override
    public void add(T value) {
        linkLast(value);
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
    public void addAll(List<T> list) {
        checkPositionIndex(size);

        Object[] obj = list.toArray();
        int length = obj.length;

        if (length == 0) {
            return;
        }

        Node<T> prev;
        prev = tail;

        for (Object object : obj) {
            T value = (T) object;
            Node<T> newNode = new Node<>(prev, value, null);
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            prev = newNode;
        }

        tail = prev;
        size += length;
    }

    @Override
    public T get(int index) {
        checkElementByIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementByIndex(index);
        Node<T> node = node(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementByIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.element == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (object.equals(node.element)) {
                    unlink(node);
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

    private void checkElementByIndex(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private void checkPositionIndex(int index) {
        if (index >= 0 && index <= size) {
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private void linkBefore(T value, Node<T> before) {
        final Node<T> pre = before.prev;
        final Node<T> newNode = new Node<>(pre, value, before);
        before.prev = newNode;
        if (pre == null) {
            head = newNode;
        } else {
            pre.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> value) {
        final T element = value.element;
        final Node<T> next = value.next;
        final Node<T> prev = value.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            value.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }

        value.element = null;
        size--;
        return element;
    }

    private Node<T> node(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}


