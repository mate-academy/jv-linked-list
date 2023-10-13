package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Provided index is out of valid range";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addToHead(newNode);
        } else {
            addByIndex(newNode, index);
        }
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T returnValue = getNode(index).item;
        getNode(index).item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        T deletedItem = getNode(index).item;
        unlink(getNode(index));
        size--;
        return deletedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                remove(index);
                return true;
            } else {
                index++;
                current = current.next;
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

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        if (index < size / 2) {
            Node<T> returnNode = head;
            for (int i = 0; i < index; i++) {
                returnNode = returnNode.next;
            }
            return returnNode;
        }
        Node<T> returnNode = tail;
        for (int i = size - 1; i > index; i--) {
            returnNode = returnNode.prev;
        }
        return returnNode;
    }

    private void addByIndex(Node<T> node, int index) {
        if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node<T> sameIndexNode = getNode(index);
            node.prev = sameIndexNode.prev;
            sameIndexNode.prev.next = node;
            sameIndexNode.prev = node;
            node.next = sameIndexNode;
        }
    }

    private void addToHead(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
        } else if (node.equals(tail)) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T item;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
