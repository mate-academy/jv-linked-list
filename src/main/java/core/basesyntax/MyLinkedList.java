package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            linkToTheEnd(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(value, null, null);
        if (index == 0) {
            if (size == 0) {
                head = tail = newNode;
            } else {
                head.prev = newNode;
                newNode.next = head;
                head = newNode;
            }
        } else if (index == size) {
            linkToTheEnd(newNode);
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> next = prev.next;
            prev.next = newNode;
            newNode.prev = prev;
            newNode.next = next;
            next.prev = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).value;
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
        checkIndex(index);
        T valueToReturn;
        if (index == 0) {
            valueToReturn = head.value;
            head = head.next;
        } else if (index == size - 1) {
            valueToReturn = tail.value;
            tail = tail.prev;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            valueToReturn = prev.next.value;
            Node<T> next = prev.next.next;
            prev.next = next;
            next.prev = prev;
        }
        size--;
        return valueToReturn;
    }

    @Override
    public boolean remove(T object) {
        int index = getNodeIndexByValue(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkToTheEnd(Node<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            indexOutOfBounds(index);
        }
    }

    private void indexOutOfBounds(int index) {
        throw new IndexOutOfBoundsException(
                String.format("Index '%d' out of bounds for size '%d'", index, size)
        );
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            indexOutOfBounds(index);
        }
    }

    private int getNodeIndexByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (value == current.value || value != null && value.equals(current.value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
