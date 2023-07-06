package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
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
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds!!!");
        }
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = node(index);
        T oldVal = node.value;
        node.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index < 0) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds!!!");
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds!!!");
        }
    }

    private Node<T> node(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void linkLast(T e) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T e, Node<T> such) {
        Node<T> pred = such.prev;
        Node<T> newNode = new Node<>(pred, e, such);
        such.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
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
        node.value = null;
        size--;
        return element;
    }

    private int indexOf(T element) {
        int index = 0;
        if (element == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.value == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (element.equals(node.value)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
}
