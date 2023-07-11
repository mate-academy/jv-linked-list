package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLastNode(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            addLastNode(value);
            return;
        }
        addNodeBefore(getByIndex(index), value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLastNode(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (equals(node.item, object)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private Node<T> getByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            Node<T> temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
            return temp;
        }
    }

    private T unlink(Node<T> node) {
        if (node == first) {
            first = node.next;
        }
        if (node == last) {
            last = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
        return node.item;
    }

    private void addLastNode(T value) {
        Node<T> newNode = new Node(last, value, null);
        last = newNode;
        if (newNode.prev == null) {
            first = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        size++;
    }

    private void addNodeBefore(Node node, T value) {
        Node<T> newNode = new Node(node.prev, value, node);
        if (node == first) {
            first = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
