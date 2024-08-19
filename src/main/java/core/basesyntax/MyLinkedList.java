package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexToInsert(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexOfPresentElement(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOfPresentElement(index);
        Node<T> oldNode = findNode(index);
        T oldValue = oldNode.item;
        oldNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOfPresentElement(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (object.equals(node.item)) {
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

    private Node<T> findNode(int index) {
        if (index < size / 2) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void linkFirst(T element) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, element, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkLast(T element) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, element, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> node) {
        final Node<T> current = node.prev;
        final Node<T> newNode = new Node<>(current, element, node);
        node.prev = newNode;
        if (current == null) {
            head = newNode;
        } else {
            current.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }

    private void checkIndexToInsert(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexOfPresentElement(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
