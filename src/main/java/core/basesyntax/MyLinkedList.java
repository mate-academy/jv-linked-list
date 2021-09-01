package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        Node<T> insertNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = insertNode;
            size++;
            return;
        }
        tail.next = insertNode;
        tail = insertNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("This index is out of bounds");
        }
        if (isEmpty() || index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> insertNode = new Node<>(nextNode.previous, value, nextNode);
        if (index == 0) {
            head = insertNode;
            size++;
            return;
        }
        nextNode.previous.next = insertNode;
        nextNode.previous = insertNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T changedItem = node.item;
        node.item = value;
        return changedItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        return removingLink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                removingLink(node);
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

    private Node<T> getNode(int index) {
        tryIndex(index);
        Node<T> node;
        int counter;
        if (index > size / 2) {
            counter = size - 1;
            node = tail;
            while (index < counter) {
                node = node.previous;
                counter--;
            }
        } else {
            counter = 0;
            node = head;
            while (counter < index) {
                node = node.next;
                counter++;
            }
        }
        return node;
    }

    private T removingLink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.previous;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
        }
        size--;
        return node.item;
    }

    private void tryIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This index is out of bounds");
        }
    }
}
