package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node node = new Node(null, value, null);
        if (head == null) {
            head = node;
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexExist(index);
        Node<T> currentNode = getNodeByIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addMiddle(value, currentNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue;
        checkIndexExist(index);
        Node<T> currentNode = getNodeByIndex(index);
        oldValue = get(index);
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void checkIndexExist(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("You can't use this index: " + index);
        }
    }

    private void addFirst(T value) {
        head = new Node<>(null, value, head);
    }

    private void addLast(T value) {
        Node<T> node = new Node<>(null, value, null);
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = node;
        node.prev = currentNode;
        tail = node;
    }

    private void addMiddle(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        if (index <= size >> 1) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            node.prev = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return (T) node.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
