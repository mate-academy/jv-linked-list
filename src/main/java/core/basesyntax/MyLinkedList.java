package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }

        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            size++;
            return;
        }

        if (index == size) {
            Node<T> newNode = new Node<>(last, value, null);
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
            size++;
            return;
        }

        Node<T> indexValue = first;
        for (int i = 0; i < index; i++) {
            indexValue = indexValue.next;
        }

        if (indexValue.prev == null) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> prew = indexValue.prev;
            Node<T> newNode = new Node<>(prew, value, indexValue);
            prew.next = newNode;
            indexValue.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List is null");
        }
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        Node<T> current;

        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T removed = current.value;
        current.value = value;
        return removed;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T removed = current.value;

        if (current.prev == null) {
            first = current.next;
            if (first != null) {
                first.prev = null;
            }
        } else if (current.next == null) {
            last = current.prev;
            if (last != null) {
                last.next = null;
            }
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return removed;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> current = findNode(object);
        if (current == null) {
            return false;
        }
        if (current.prev == null) {
            first = current.next;
            if (first != null) {
                first.prev = null;
            }
        }
        if (current.next == null) {
            last = current.prev;
            if (last != null) {
                last.next = null;
            }
        }

        if (current.prev != null && current.next != null) {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;
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

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private Node<T> findNode(T object) {
        Node<T> current = first;

        while (current != null) {
            if (object == null) {
                if (current == null) {
                    return current;
                }
            }
            if (Objects.equals(current.value, object)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
