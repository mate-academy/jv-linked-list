package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, findNode(index));
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
        checkElementIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> foundNode = findNode(index);
        T prevVal = foundNode.value;
        foundNode.value = value;
        return prevVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNode(index));
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

    private void addFirst(T value) {
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

    private void addLast(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void addBefore(T value, Node<T> node) {
        Node<T> act = node.prev;
        Node<T> actual = new Node<>(act, value, node);
        node.prev = actual;
        if (act == null) {
            head = actual;
        } else {
            act.next = actual;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> search;
        if (index < (size / 2)) {
            search = head;
            for (int i = 0; i < index; i++) {
                search = search.next;
            }
        } else {
            search = tail;
            for (int i = size - 1; i > index; i--) {
                search = search.prev;
            }
        }
        return search;
    }

    private T unlink(Node<T> element) {
        final T unlinkValue = element.value;
        Node<T> next = element.next;
        Node<T> prev = element.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            element.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            element.next = null;
        }

        element.value = null;
        size--;
        return unlinkValue;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Non-existent position");
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
