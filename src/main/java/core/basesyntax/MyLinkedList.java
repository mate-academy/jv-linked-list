package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> indexNode = searchNode(index);
            Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
            if (indexNode.prev == null) {
                head = newNode;
            } else {
                indexNode.prev.next = newNode;
            }
            indexNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T node : list) {
                add(node);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> foundNode = searchNode(index);
        T deletedValue = foundNode.item;
        foundNode.item = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> foundNode = searchNode(index);
        return deleteLink(foundNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> n = head; n != null; n = n.next) {
            if (object == n.item || object != null && object.equals(n.item)) {
                deleteLink(n);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " is invalid");
        }
    }

    private Node<T> searchNode(int index) {
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

    private T deleteLink(Node<T> deletedNode) {
        Node<T> next = deletedNode.next;
        Node<T> prev = deletedNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            deletedNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            deletedNode.next = null;
        }
        T deletedElement = deletedNode.item;
        deletedNode.item = null;
        size--;
        return deletedElement;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
