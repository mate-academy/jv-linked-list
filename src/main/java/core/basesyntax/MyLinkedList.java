package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;

        }
    }

    @Override
    public void add(T value) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, value, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdding(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> nodeOnIndex = findNodeOnIndex(index);
            final Node<T> beforeNewNode = nodeOnIndex.prev;
            final Node<T> newNode = new Node<>(beforeNewNode,value, nodeOnIndex);
            nodeOnIndex.prev = newNode;
            if (beforeNewNode == null) {
                head = newNode;
            } else {
                beforeNewNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForSettingAndRemoving(index);
        return findNodeOnIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForSettingAndRemoving(index + 1);
        Node<T> x = findNodeOnIndex(index);
        T oldValue = x.element;
        x.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForSettingAndRemoving(index);
        return unlink(findNodeOnIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (object == x.element || (object != null && object.equals(x.element))) {
                unlink(x);
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

    private void checkIndexForAdding(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index doesn't exist");
        }
    }

    private void checkIndexForSettingAndRemoving(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist");
        }
    }

    private Node<T> findNodeOnIndex(int index) {
        Node<T> nodeAfterIndex = head;
        for (int i = 0; i < index; i++) {
            nodeAfterIndex = nodeAfterIndex.next;
        }
        return nodeAfterIndex;
    }

    private T unlink(Node<T> x) {
        final T object = x.element;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.element = null;
        size--;
        return object;
    }
}
