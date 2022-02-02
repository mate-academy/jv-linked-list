package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == 0) {
            addFirstElementToList(value);
            return;
        }
        if (index == 0) {
            addHeadElementToList(value);
            return;
        }
        if (index == size) {
            addTailElementToList(value);
            return;
        }
        insertNode(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexAdvanced(index);
        Node<T> node = getOptimizeWay(index);
        node = node == head
                ? moveFromHead(index, node)
                : moveFromTail(index, node);
        return node == null ? null : node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexAdvanced(index);
        Node<T> node = getOptimizeWay(index);
        node = (node == head)
                ? moveFromHead(index, node)
                : moveFromTail(index, node);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexAdvanced(index);
        if (size == 1) {
            return removeLastElementFromList();
        }
        if (index == 0) {
            return removeHeadElementFromList();
        }
        if (index == size - 1) {
            return removeTailElementFromList();
        }

        Node<T> node = getOptimizeWay(index);
        node = (node == head)
                ? moveFromHead(index, node)
                : moveFromTail(index, node);
        return node == null ? null : unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object
                    || node.value != null
                    && node.value.equals(object)) {
                remove(i);
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

    private void addFirstElementToList(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = tail = node;
        size++;
    }

    private void addHeadElementToList(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addTailElementToList(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private T removeLastElementFromList() {
        head = tail = null;
        size--;
        return null;
    }

    private T removeHeadElementFromList() {
        Node<T> node = head;
        final T removedValue = head.value;
        node.next.prev = node.prev;
        head = node.next;
        size--;
        return removedValue;
    }

    private T removeTailElementFromList() {
        Node<T> node = tail;
        final T removedValue = tail.value;
        node.prev.next = node.next;
        tail = node.prev;
        size--;
        return removedValue;
    }

    private T unlink(Node<T> node) {
        final T removedValue = node.value;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return removedValue;
    }

    private void insertNode(T value, int index) {
        Node<T> node = head;
        for (int i = 1; i < size; i++) {
            if (i == index) {
                Node<T> newNode = new Node<>(null, value, null);
                node.next.prev = newNode;
                newNode.next = node.next;
                node.next = newNode;
                newNode.prev = node;
                size++;
                break;
            }
            node = head.next;
        }
    }

    private void checkIndexAdvanced(int index) {
        checkIndex(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Index [" + index + "] "
                    + "is out of bound for size " + size);
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index [" + index + "] "
                    + "is out of bound for size " + size);
        }
    }

    private Node<T> getOptimizeWay(int index) {
        return index >= size / 2 ? tail : head;
    }

    private Node<T> moveFromTail(int index, Node<T> node) {
        for (int i = size - 1; i > 0; i--) {
            if (i == index) {
                return node;
            }
            node = node.prev;
        }
        return null;
    }

    private Node<T> moveFromHead(int index, Node<T> node) {
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}
