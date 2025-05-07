package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(value);
            head = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(value);
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            head.prev = new Node<>(value, null, head);
            head = head.prev;
        } else {
            Node<T> node = new Node<>(value);
            Node<T> current = getNode(index);
            insertValue(current.prev, node);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T previousItem = currentNode.item;
        currentNode.item = value;
        return previousItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T currentItem = currentNode.item;
        unlink(currentNode);
        return currentItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNode(object);
        if (currentNode != null) {
            unlink(currentNode);
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
        return size == 0;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + "is incorrect."
                    + "Element by this index is not exist."
                    + "List size: " + size);
        }
    }

    private boolean isMoveFromHead(int index) {
        return index <= (size / 2);
    }

    private Node<T> getNode(int index) {
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (isMoveFromHead(index)) {
            Node<T> currentNode = head;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item != null && currentNode.item.equals(value)
                                            || currentNode.item == value) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void insertValue(Node<T> previous, Node<T> newNode) {
        newNode.prev = previous.next.prev;
        previous.next.prev = newNode;
        newNode.next = previous.next;
        previous.next = newNode;
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T item) {
            this.item = item;
        }

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
