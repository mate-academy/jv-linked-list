package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (first == null) {
            first = node;
        } else {
            node.prev = last;
            node.next = null;
            last.next = node;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 | index > size) {
            throw new IndexOutOfBoundsException("Index of element is"
                    + " out of bounds of linked list");
        }
        Node<T> node = new Node<>(value);
        Node<T> currentNode = findByIndex(index);
        if (currentNode == null) {
            add(value);
        } else if (index == 0) {
            node.prev = null;
            node.next = currentNode;
            currentNode.prev = node;
            first = node;
            size++;
        } else {
            currentNode.prev.next = node;
            node.prev = currentNode.prev;
            currentNode.prev = node;
            node.next = currentNode;
            size++;
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
        checkIndex(index);
        Node<T> currentNode = findByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findByIndex(index);
        T removedNodeValue = currentNode.value;
        if (currentNode == first) {
            if (currentNode.next == null) {
                first = null;
                last = null;
            } else {
                first = currentNode.next;
                first.prev = null;
                currentNode.next = null;
            }
        } else if (currentNode == last) {
            currentNode.prev.next = null;
            last = currentNode;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode.next = null;
            currentNode.prev = null;
        }
        size--;
        return removedNodeValue;
    }

    @Override
    public boolean remove(T object) {
        int index = findByElement(object);
        if (index == -1) {
            return false;
        }
        return Objects.equals(remove(index), object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> currentNode;
        if (index == size) {
            return null;
        }
        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = 0; i < size - index - 1; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private int findByElement(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if ((object == null & currentNode.value == null)
                    || currentNode.value.equals(object)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0 | index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid " + index);
        }
    }
}
