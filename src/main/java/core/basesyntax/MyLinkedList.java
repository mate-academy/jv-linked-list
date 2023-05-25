package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        size++;
        if (size == 1) {
            last = newNode;
            first = newNode;
            return;
        }
        newNode.prev = last;
        last.next = newNode;
        this.last = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        size++;
        if (node == first) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            return;
        }
        newNode.prev = node.prev;
        newNode.next = node;
        newNode.prev.next = newNode;
        node.prev = newNode;
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
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        T removeValue = node.value;
        unlink(node);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == node.value || node.value != null && node.value.equals(object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> pointer;
        if (index < (size >> 1)) {
            pointer = first;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
        } else {
            pointer = last;
            for (int i = size - 1; i > index; i--) {
                pointer = pointer.prev;
            }
        }
        return pointer;
    }

    private void unlink(Node<T> nodeToDelete) {
        size--;
        if (size == 0) {
            first = null;
            last = null;
            return;
        }
        if (nodeToDelete == first) {
            first = nodeToDelete.next;
            first.prev = null;
            return;
        }
        if (nodeToDelete == last) {
            last = nodeToDelete.prev;
            last.next = null;
            return;
        }
        nodeToDelete.next.prev = nodeToDelete.prev;
        nodeToDelete.prev.next = nodeToDelete.next;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
