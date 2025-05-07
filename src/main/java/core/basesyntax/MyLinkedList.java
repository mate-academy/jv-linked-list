package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkTail(value);
        } else {
            validateIndex(index);
            insertNode(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            linkTail(item);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> oldNode = findNode(index);
        T oldVal = oldNode.item;
        oldNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);;
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.item || object != null && object.equals(node.item)) {
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
        return size() == 0;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index" + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }

    private void linkHead(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, head);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    void linkTail(T value) {
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

    void insertNode(T value, Node<T> current) {
        Node<T> pred = current.prev;
        Node<T> newNode = new Node<>(pred, value, current);
        current.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> deletedElement) {
        final T element = deletedElement.item;
        Node<T> next = deletedElement.next;
        Node<T> prev = deletedElement.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            deletedElement.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            deletedElement.next = null;
        }
        deletedElement.item = null;
        size--;
        return element;
    }

}
