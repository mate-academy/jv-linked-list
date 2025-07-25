package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount = 0;

    private static class Node<T> {

        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {

        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void add(T value, int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid: " + index);
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
            modCount++;
            return;
        }

        if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
            if (head == null) {
                head = newNode;
            }
            size++;
            modCount++;
            return;
        }

        Node<T> current = node(index);
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(prev, value, current);
        prev.next = newNode;
        current.prev = newNode;
        size++;
        modCount++;

    }

    @Override
    public void addAll(List<T> list) {

        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid: " + index);
        }
        Node<T> newNode = node(index);
        T oldVal = newNode.value;
        newNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }

        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {

        if (object == null) {
            for (Node<T> newNode = head; newNode != null; newNode = newNode.next) {
                if (newNode.value == null) {
                    unlink(newNode);
                    return true;
                }
            }
        } else {
            for (Node<T> newNode = head; newNode != null; newNode = newNode.next) {
                if (object.equals(newNode.value)) {
                    unlink(newNode);
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

    private Node<T> node(int index) {

        Node<T> current;
        if (index < (size >> 1)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {

        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        modCount++;
        return element;
    }
}
