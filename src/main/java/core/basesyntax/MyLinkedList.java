package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "The index is out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = searchNodeByIndex(index);
            Node<T> previousNode = currentNode.prev;
            Node<T> newNode = new Node<>(value);
            newNode.prev = previousNode;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            if (previousNode == null) {
                first = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (T listItem : list) {
            add(listItem);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (first == null) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = searchNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
        T oldValue = currentNode.value;
        unlink(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.value == object || object != null && object.equals(currentNode.value)) {
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
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private void unlink(Node<T> currentNode) {
        Node<T> previousNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
            currentNode.next = null;
        }
        size--;
    }

    private Node<T> searchNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        int i;
        if (index <= size / 2) {
            currentNode = first;
            i = 0;
            while (i != index) {
                currentNode = currentNode.next;
                i++;
            }
        } else {
            currentNode = last;
            i = size - 1;
            while (i != index) {
                currentNode = currentNode.prev;
                i--;
            }
        }
        return currentNode;
    }

    private class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        private E value;

        public Node(E value) {
            this.value = value;
        }
    }
}
