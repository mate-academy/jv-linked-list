package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> previous;

        public Node(Node<T> next, T value, Node<T> previous) {
            this.next = next;
            this.value = value;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == size) {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else {
            Node<T> nodeByIndex = nodeSearch(index);
            Node<T> previousNode = nodeByIndex.previous;
            newNode.next = nodeByIndex;
            nodeByIndex.previous = newNode;
            newNode.previous = previousNode;
            previousNode.next = newNode;
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
        checkByIndexEqualSize(index);
        return nodeSearch(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkByIndexEqualSize(index);
        Node<T> nodeSearch = nodeSearch(index);
        T oldValue = nodeSearch.value;
        nodeSearch.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkByIndexEqualSize(index);
        Node<T> deletedNode = nodeSearch(index);
        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            tail = tail.previous;
        } else {
            deletedNode.previous.next = deletedNode.next;
            deletedNode.next.previous = deletedNode.previous;
        }
        size--;
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                if (currentNode == head) {
                    head = head.next;
                } else if (currentNode == tail) {
                    tail = tail.previous;
                } else {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                size--;
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

    private void checkByIndexEqualSize(int index) {
        if (index >= size || index < 0) {
            trowException();
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            trowException();
        }
    }

    private void trowException() {
        throw new IndexOutOfBoundsException("Not a valid index, outside the"
                + " limit of the permissible value");
    }

    private Node<T> nodeSearch(int index) {
        int indexNode = 0;
        Node<T> currentNode = head;
        while (indexNode != index) {
            currentNode = currentNode.next;
            indexNode++;
        }
        return currentNode;
    }
}
