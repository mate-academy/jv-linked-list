package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, T inem, Node<T> prev) {
            this.next = next;
            this.item = inem;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, tail);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexException(index);
        Node<T> node = getNodeAtIndex(index);
        Node<T> newNode = new Node<>(node, value, node.prev);
        if (node.prev == null) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexException(index);
        return getNodeAtIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexException(index);
        Node<T> oldNote = getNodeAtIndex(index);
        T oldItem = oldNote.item;
        oldNote.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
        Node<T> currentNode = getNodeAtIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.item == object || object != null && object.equals(node.item)) {
                unlink(node);
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

    public void unlink(Node<T> removed) {
        if (removed == head) {
            head = removed.next;
        } else if (removed == tail) {
            tail = removed.prev;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
        size--;
    }

    private Node<T> getNodeAtIndex(int index) {
        checkIndexException(index);
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}
