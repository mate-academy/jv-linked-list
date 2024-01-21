package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (isEmpty()) {
            checkIndexWhenAdd(index);
            addFirst(value);
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            checkIndexWhenAdd(index);
            Node<T> node = getNodeByIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> element = getNodeByIndex(index);
        T replaced = element.item;
        element.item = value;
        return replaced;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private void checkIndexWhenAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void addFirst(T value) {
        if (size == 0) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        }
        size++;
    }

    private void addLast(T value) {
        if (size == 0) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void unlink(Node<T> x) {
        if (x.prev == null) {
            head = x.next;
        } else {
            x.prev.next = x.next;
        }
        if (x.next == null) {
            tail = x.prev;
        } else {
            x.next.prev = x.prev;
        }
        size--;
    }

    class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }
}
