package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newElement = new Node<T>(last, value, null);
        if (size == 0) {
            first = newElement;
        } else {
            last.next = newElement;
        }
        last = newElement;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> currentNode = new Node(null, value, first);
            first.prev = currentNode;
            first = currentNode;
        } else {
            Node<T> currentNode = findNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        T setValue = currentNode.value;
        currentNode.value = value;
        return setValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = findNode(index);
        final T removedValue = removedNode.value;
        if (removedNode.next == null) {
            last = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
        }
        if (removedNode.prev == null) {
            first = removedNode.next;
        } else {
            removedNode.prev.next = removedNode.next;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> currentNode = first;
        for (int index = 0; index < size; index++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                remove(index);
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
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index is not valid");
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        int count = 0;
        if (index <= size / 2) {
            currentNode = first;
            while (count < index) {
                currentNode = currentNode.next;
                count++;
            }
        } else {
            currentNode = last;
            count = size - 1;
            while (count > index) {
                currentNode = currentNode.prev;
                count--;
            }
        }
        return currentNode;
    }
}
