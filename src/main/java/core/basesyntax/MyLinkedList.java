package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index < 0");
        }
        if (size == 0 || index == size) {
            addTail(value);
        } else if (index == 0) {
            addHead(value);
        } else {
            Node<T> newNode = new Node<>(value,
                    getNodePerIndex(index).prev, getNodePerIndex(index));
            getNodePerIndex(index).prev.next = newNode;
            getNodePerIndex(index).prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listValue: list) {
            addTail(listValue);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Exception");
        }
        return getNodePerIndex(index).value;
    }

    private Node<T> getNodePerIndex(int index) {
        Node<T> resylt;
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Not valide index");
        }
        if (index < size / 2) {
            resylt = first;
            for (int i = 0; i < index; i++) {
                resylt = resylt.next;
            }
        } else {
            resylt = last;
            for (int i = size - 1; i > index; i--) {
                resylt = resylt.prev;
            }
        }
        return resylt;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not valide index");
        }
        T oldValue = getNodePerIndex(index).value;
        getNodePerIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Not valide index");
        } else {
            T oldValue = getNodePerIndex(index).value;
            unlink(getNodePerIndex(index));
            return oldValue;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> result = first;
        while (result != null) {
            if (Objects.equals(result.value, object)) {
                unlink(result);
                return true;
            }
            result = result.next;
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

    private void addHead(T value) {
        Node<T> newNode = new Node<>(value, null, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addTail(T value) {
        if (first == null && last == null) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(value, last, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
        } else if (Objects.equals(node, first)) {
            node.next.prev = null;
            first = node.next;
        } else if ((Objects.equals(node, last))) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
