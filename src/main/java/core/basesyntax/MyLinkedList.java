package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
        } else {
            if (tail == null) {
                tail = new Node<>(head, value, null);
                head.next = tail;
            } else {
                Node<T> newNode = new Node<>(tail, value, null);
                tail.next = newNode;
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == null && object == null) {
                break;
            }
            if (node.item != null && node.item.equals(object)) {
                break;
            }
            node = node.next;
        }
        if (node == null) {
            return false;
        }
        unlink(node);
        size--;
        return true;
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

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Specified index is invalid");
        }
    }

    private Node<T> searchFromStart(int index) {
        Node<T> item = head;
        int counter = 0;
        do {
            if (counter == index) {
                return item;
            }
            item = item.next;
            counter++;
        } while (counter <= index);
        return item;
    }

    private Node<T> searchFromEnd(int index) {
        Node<T> item = tail;
        int counter = size - 1;
        do {
            if (counter == index) {
                return item;
            }
            item = item.prev;
            counter--;
        } while (counter >= index);
        return item;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        int listMiddleIndex = size / 2;
        if (index <= listMiddleIndex) {
            return searchFromStart(index);
        } else {
            return searchFromEnd(index);
        }
    }

    private boolean isListHasOneNode() {
        return tail == null;
    }

    private boolean isHead(Node<T> node) {
        return node.prev == null;
    }

    private boolean isTail(Node<T> node) {
        return node.next == null;
    }

    private void unlink(Node<T> node) {
        if (isListHasOneNode()) {
            head = null;
            return;
        }

        if (isHead(node)) {
            head = node.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
        }

        if (isTail(node)) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
        }
    }
}
