package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
        } else {
            if (tail == null) {
                tail = new Node<>(head, value, null);
                head.next = tail;
            } else {
                tail.next = new Node<>(tail, value, null);
                tail = tail.next;
            }
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        Node<T> foundNode = getNode(index);
        if (foundNode != null) {
            Node<T> newNode;
            if (foundNode.prev != null) {
                newNode = new Node<>(foundNode.prev, value, foundNode);
                foundNode.prev.next = newNode;
            } else {
                newNode = new Node<>(null, value, foundNode);
            }
            if (head == foundNode) {
                head = newNode;
            }
            size++;
        } else {
            add(value);
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
        Node<T> node = getNode(index);
        return node != null ? node.item : null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        size--;
        T value = node.item;
        node = null;
        return value;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds!");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node = head;
        if (size != 0) {
            for (int i = 0; i < index; i++) {
                node = node != null ? node.next : null;
            }
        }
        return node;
    }

    private void checkIndexAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds!");
        }
    }


    private int getIndex(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == value
                    || node != null && node.item != null && node.item.equals(value)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous != null) {
            previous.next = next;
        } else {
            head = next;

        }
        if (next != null) {
            next.prev = previous;
        } else {
            tail = next;
        }
    }
}
