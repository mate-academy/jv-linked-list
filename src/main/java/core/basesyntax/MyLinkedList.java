package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T item, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size) {
            add(item);
            return;
        }
        Node<T> newNode = new Node<>(item);
        if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            newNode.next = currentNode;
            newNode.previous = currentNode.previous;
            currentNode.previous.next = newNode;
            currentNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T item, int index) {
        Node<T> node = getNode(index);
        T oldItem = node.item;
        node.item = item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unlink(node);
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T item) {
        Node<T> current = first;
        while (current != null) {
            if (current.item != null && current.item.equals(item)
                    || current.item == null && item == null) {
                unlink(current);
                size--;
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.previous;
        Node<T> next = node.next;
        if (previous != null) {
            previous.next = next;
        } else {
            first = next;
        }
        if (next != null) {
            next.previous = previous;
        } else {
            last = previous;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
}
