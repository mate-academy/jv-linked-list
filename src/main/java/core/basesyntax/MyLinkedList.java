package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Oh, no! Your <b>index</b> is out of bounds!";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T item) {
        Node<T> add;
        if (size != 0) {
            add = new Node<>(item, tail, null);
            tail.next = add;
        } else {
            add = new Node<>(item, null, null);
            head = add;
        }
        tail = add;
        size++;
        return true;
    }

    @Override
    public void add(T item, int index) {
        if (index == size) {
            add(item);
            return;
        }

        Node<T> nextAfterAdd = getNode(index);
        Node<T> prev = nextAfterAdd.prev;
        Node<T> add = new Node<>(item, prev, nextAfterAdd);
        if (prev == null) {
            head = add;
        } else {
            prev.next = add;
        }
        nextAfterAdd.prev = add;
        size++;
    }

    @Override
    public boolean addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T set, int index) {
        Node<T> requestNode = getNode(index);
        T item = requestNode.item;
        requestNode.item = set;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> requestNode = getNode(index);
        return unlink(requestNode);
    }

    @Override
    public boolean remove(T item) {
        Node<T> requestNode = head;
        for (int i = 0; i < size; i++) {
            if (requestNode.item == item
                    || requestNode.item != null && requestNode.item.equals(item)) {
                unlink(requestNode);
                return true;
            }
            requestNode = requestNode.next;
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
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        indexException(index);
        if (index <= size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> requestNode = head;
        for (int i = 0; i < index; i++) {
            requestNode = requestNode.next;
        }
        return requestNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> requestNode = tail;
        for (int i = size - 1; i > index; i--) {
            requestNode = requestNode.prev;
        }
        return requestNode;
    }

    private void indexException(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private T unlink(Node<T> requestNode) {
        if (requestNode.next == null) {
            tail = requestNode.prev;
        } else if (requestNode.prev == null) {
            head = requestNode.next;
        } else {
            requestNode.prev.next = requestNode.next;
            requestNode.next.prev = requestNode.prev;
        }
        size--;
        return requestNode.item;
    }

}
