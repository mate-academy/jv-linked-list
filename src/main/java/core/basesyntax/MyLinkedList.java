package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private T item;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("exception");
        }
        if (size > 0 && index < (size - 1) && index > 0) {
            Node<T> node = new Node<>(head, value, tail);
            for (int i = 1; i < index; i++) {
                node.prev = node.prev.next;
            }
            node.prev.next = node;
            for (int i = 1; i < size - index; i++) {
                node.next = node.next.prev;
            }
            node.next.prev = node;
        }
        if ((size - 1) == index) {
            Node<T> node = new Node<>(null, value, null);
            node.prev = tail.prev;
            tail.prev = node;
            node.next = tail;
            node.prev.next = node;
        }
        if (index == 0 && size > 0) {
            Node<T> node = new Node<>(null, value, null);
            head.prev = node;
            node.next = head;
            head = node;
        }
        if (index == size) {
            add(value);
            size--;
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
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = node(index);
        T oldValue = get(index);
        setNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        final T removedValue = get(index);
        unLink(node(index));
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove;
        for (nodeToRemove = head; nodeToRemove != null; nodeToRemove = nodeToRemove.next) {
            if (nodeToRemove.item == object
                    || (nodeToRemove.item != null && nodeToRemove.item.equals(object))) {
                unLink(nodeToRemove);
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

    private Node<T> node(int index) {
        Node<T> currentNode;
        if (index < size << 1) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; --i) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't do this operation with index" + index);
        }
    }

    private void unLink(Node<T> node) {
        Node<T> previousNode;
        Node<T> nextNode;
        size--;
        if (node.prev == null && node.next == null) {
            return;
        }
        if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            previousNode = node.prev;
            nextNode = node.next;
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
        }
    }
}
