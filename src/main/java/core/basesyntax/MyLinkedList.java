package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> first = new Node<>(null, value, null);
            head = first;
            tail = first;
        } else {
            Node<T> last = tail;
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0 || index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> left = current.prev;
            Node<T> newNode = new Node<>(left, value, current);
            if (left == null) {
                head = newNode;
            } else {
                current.prev = newNode;
                left.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        return getNode(index).items;
    }

    @Override
    public T set(T value, int index) {
        indexChecker(index);
        T oldValue = getNode(index).items;
        getNode(index).items = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexChecker(index);
        Node<T> oldNode = getNode(index);
        Node<T> prevNode = oldNode.prev;
        Node<T> nextNode = oldNode.next;
        final T returnElement = oldNode.items;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return returnElement;
    }

    @Override
    public boolean remove(T object) {
        int counter = 0;
        Node<T> header = head;
        for (int i = 0; i < size; i++) {
            if (header.items == null || header.items.equals(object)) {
                remove(i);
                return true;
            }
            header = head.next;
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

    private void indexChecker(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> first = head;
            for (int i = 0; i < index; i++) {
                first = first.next;
            }
            return first;
        } else {
            Node<T> last = tail;
            for (int i = size - 1; i > index; i--) {
                last = last.prev;
            }
            return last;
        }
    }

    private static class Node<T> {
        private T items;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T items, Node<T> next) {
            this.prev = prev;
            this.items = items;
            this.next = next;
        }
    }
}
