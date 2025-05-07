package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
            throw new IndexOutOfBoundsException("Index Out Of Bound");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, searchByIndex(index));
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
        checkIndex(index);
        return searchByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> searchedNode = searchByIndex(index);
        T oldVal = searchedNode.value;
        searchedNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.value == object) {
                unlink(x);
                return true;
            }
        }
        if (object != null) {
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

    private void linkFirst(T value) {
        final Node<T> h = head;
        final Node<T> newNode = new Node<>(null, value, h);
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

    private void linkBefore(T t, Node<T> beOnTheRight) {
        final Node<T> prevActual = beOnTheRight.prev;
        final Node<T> actual = new Node<>(prevActual, t, beOnTheRight);
        beOnTheRight.prev = actual;
        if (prevActual == null) {
            head = actual;
        } else {
            prevActual.next = actual;
        }
        size++;
    }

    private Node<T> searchByIndex(int index) {
        Node<T> search;
        if (size == 0) {
            search = null;
        }
        if (index < (size >> 1)) {
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

    private T unlink(Node<T> unlinkElement) {
        final T unlinkValue = unlinkElement.value;
        final Node<T> nextFromUnlink = unlinkElement.next;
        final Node<T> prevFromUnlink = unlinkElement.prev;

        if (prevFromUnlink == null) {
            head = nextFromUnlink;
        } else {
            prevFromUnlink.next = nextFromUnlink;
            unlinkElement.prev = null;
        }

        if (nextFromUnlink == null) {
            tail = prevFromUnlink;
        } else {
            nextFromUnlink.prev = prevFromUnlink;
            unlinkElement.next = null;
        }

        unlinkElement.value = null;
        size--;
        return unlinkValue;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
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
