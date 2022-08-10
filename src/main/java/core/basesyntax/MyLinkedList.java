package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;

        }
    }

    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> node = new Node<>(value);
        if (index == 0) {
            node.next = head;
            head = node;
            if (head.next == null) {
                tail = head;
            }
        } else if (index == size) {
            tail.next = node;
            tail = node;
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            node.next = previous.next;
            previous.next = node;
        }
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
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> nodeByIndex = getNodeByIndex(index);
        T element = nodeByIndex.value;
        nodeByIndex.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            removedElement = previous.next.value;
            previous.next = previous.next.next;
            if (index == size - 1) {
                tail = previous;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || object != null && object.equals(get(i))) {
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

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bound" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index, size);
        if (index == size - 1) {
            return tail;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }
}
