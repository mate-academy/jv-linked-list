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

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = tail = new Node(null, value, null);
        } else {
            Node<T> newNode = new Node(tail, value, null);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkForEqualsIndex(index, size);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size || head == null) {
            add(value);
        } else if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node<T> node = getNodeByIndex(index);
            newNode.prev = node.prev;
            newNode.next = node;
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> getNode = getNodeByIndex(index);
        T element = getNode.value;
        getNode.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> removes = getNodeByIndex(index);
        if (index == 0 && size > 1) {
            Node<T> mustInHead = head.next;
            mustInHead.prev = null;
            head = mustInHead;
        } else if (size == index || removes.next == null) {
            tail = removes.prev;
            removes.next = null;
        } else {
            removes.prev.next = removes.next;
            removes.next.prev = removes.prev;
        }
        size--;
        return removes.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == get(i) || object != null && object.equals(get(i))) {
                remove(i);
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

    private Node<T> getNodeByIndex(int i) {
        checkIndex(i, size);
        boolean firstOrSecond = i > (size / 2);
        int currIndex = firstOrSecond ? size - 1 : 0;
        Node<T> newNode = firstOrSecond ? tail : head;
        if (firstOrSecond) {
            while (currIndex != i) {
                newNode = newNode.prev;
                currIndex--;
            }
        } else {
            while (currIndex != i) {
                newNode = newNode.next;
                currIndex++;
            }
        }
        return newNode;
    }

    private void checkForEqualsIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
    }

    private void checkIndex(int index, int size) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
    }
}
