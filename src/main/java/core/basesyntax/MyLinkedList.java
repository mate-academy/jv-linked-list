package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newValue = new Node<>(last, value, null);
        tail = newValue;
        if (last == null) {
            head = newValue;
        } else {
            last.next = newValue;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index  " + index + "is out of bounds of the List");
        }
        if (index == 0) {
            Node<T> first = head;
            Node<T> newValue = new Node<>(null, value, first);
            head = newValue;
            if (first == null) {
                tail = newValue;
            } else {
                first.prev = newValue;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> currentValue = getNode(index);
            Node<T> newValue = new Node<>(currentValue.prev, value, currentValue);
            currentValue.prev.next = newValue;
            currentValue.prev = newValue;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T valueFromList : list) {
            add(valueFromList, size);
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexOutOfBounds(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexOutOfBounds(index);
        T oldValue = get(index);
        Node<T> oldNode = getNode(index);
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexOutOfBounds(index);
        T removedValue = get(index);
        Node<T> removedNode = getNode(index);
        unlinkNode(removedNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                unlinkNode(node);
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
        return size == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> value;
        if (index <= size / 2) {
            value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
        } else {
            value = tail;
            for (int i = size - 1; i > index; i--) {
                value = value.prev;
            }
        }
        return value;
    }

    private void unlinkNode(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = head;
        } else if (node.equals(head)) {
            head = head.next;
            head.prev = null;
        } else if (node.equals(tail)) {
            tail = tail.prev;
            tail.next = null;;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIfIndexOutOfBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index  " + index + "is out of bounds of the List");
        }
    }
}
