package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index  " + index
                    + "is invalid for size " + size);
        }
        Node<T> next = findNode(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        next.prev = newNode;
        size++;
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
        Node<T> currentNode = first;
        currentNode.next.prev = currentNode;
        currentNode = findNode(currentNode, index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T result;
        if (index == 0) {
            result = first.value;
            first = first.next;
            size--;
        } else if (index == size - 1) {
            result = last.value;
            last.prev.next = null;
            last = last.prev;
            size--;
        } else {
            Node<T> nodeDeleted = first;
            nodeDeleted.next.prev = nodeDeleted;
            nodeDeleted = findNode(nodeDeleted, index);
            result = nodeDeleted.value;
            nodeDeleted.prev.next = nodeDeleted.next;
            nodeDeleted.next.prev = nodeDeleted.prev;
            size--;
        }
        return result;
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        Node<T> nodeRemoved = first;
        while (count != size && nodeRemoved.value != object) {
            if (nodeRemoved.value != null && nodeRemoved.value.equals(object)) {
                count++;
                remove(count);
                return true;
            }
            nodeRemoved = nodeRemoved.next;
            count++;
        }
        if (count == size) {
            return false;
        }
        remove(count);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index  " + index
                    + "is invalid for size " + size);
        }
    }

private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
