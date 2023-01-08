package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (size == 0 && index == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            checkIndex(index);
            Node<T> nodeByIndex = getNodeByIndex(index);
            nodeByIndex.prev.next = newNode;
            newNode.prev = nodeByIndex.prev;
            nodeByIndex.prev = newNode;
            newNode.next = nodeByIndex;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.stream().forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T removedValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            if (size == 1) {
                head = tail = null;
                size--;
                return removedElement;
            }
            head.next.prev = null;
            head = head.next;
            size--;
            return removedElement;
        } else if (index == size - 1 && !(index < 0)) {
            removedElement = tail.value;
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return removedElement;
        } else {
            checkIndex(index);
            Node<T> nodeByIndex = getNodeByIndex(index);
            removedElement = nodeByIndex.value;
            nodeByIndex.prev.next = nodeByIndex.next;
            nodeByIndex.next.prev = nodeByIndex.prev;
            size--;
            return removedElement;
        }
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == getNodeByIndex(i).value
                    || object != null && object.equals(getNodeByIndex(i).value)) {
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
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index value(Out of bound). "
                    + "Index should be not less than 0 and not more than " + (size - 1) + ".");
        }
    }
}
