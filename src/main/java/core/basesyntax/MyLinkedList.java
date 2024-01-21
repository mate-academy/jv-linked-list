package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            Node<T> headNode = head;
            Node<T> newNode = new Node<>(null, value, headNode);
            head = newNode;
            if (headNode == null) {
                tail = newNode;
            } else {
                headNode.prev = newNode;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else {
            linkBetween(value, currentNodePosition(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return currentNodePosition(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldItem = currentNodePosition(index).item;
        currentNodePosition(index).item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(currentNodePosition(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                unlink(current);
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

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    private void linkBetween(T element, Node<T> currentIndexNode) {
        Node<T> prevNode = currentIndexNode.prev;
        Node<T> newNode = new Node<>(prevNode, element, currentIndexNode);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        currentIndexNode.prev = newNode;
        size++;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is out of bounds of size: " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is non existing index");
        }
    }

    private Node<T> getNodeByIndexFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNodeByIndexFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> currentNodePosition(int index) {
        return index < (size / 2) ? getNodeByIndexFromHead(index) : getNodeByIndexFromTail(index);
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
