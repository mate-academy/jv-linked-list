package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            tail.next = new Node<>(value, null, tail);
            tail = tail.next;
        }
        increaseSize(1);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(value, head, null);
            increaseSize(1);
        } else {
            Node<T> currentNode = searchNode(index);
            currentNode.prev.next = new Node<>(value, currentNode, currentNode.prev);
            currentNode.prev = currentNode.prev.next;
            increaseSize(1);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = searchNode(index);
        T oldNodeValue = currentNode.value;
        currentNode.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = searchNode(index);
        T value = currentNode.value;
        unLinked(currentNode);

        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int index = 0; index < size(); index++) {
            if (currentNode.value == object
                    || object != null && object.equals(currentNode.value)) {
                unLinked(currentNode);
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

    private void unLinked(Node<T> node) {
        if (node.prev == null) {
            if (head.next == null) {
                tail = null;
            } else {
                head.next.prev = null;
            }
            head = head.next;
        } else if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        increaseSize(-1);
    }

    private void increaseSize(int value) {
        size += value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The specified index is outside the list");
        }
    }

    private Node<T> searchNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        int currentIndex;

        if (index < size / 2) {
            currentNode = head;
            currentIndex = 0;
            while (index != currentIndex) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            currentNode = tail;
            currentIndex = (size - 1);
            while (index != currentIndex) {
                currentNode = currentNode.prev;
                currentIndex--;
            }
        }
        return currentNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

}
