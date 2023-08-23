package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
        }

    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
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
        checkAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
            size++;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            newNode.next = previous.next;
            previous.next.prev = newNode;
            previous.next = newNode;
            newNode.prev = previous;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
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
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue;
        if (size == 1) {
            removedValue = head.value;
            head = tail = null;
        } else if (index == 0) {
            removedValue = head.value;
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            removedValue = tail.value;
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            removedValue = previous.next.value;
            previous.next = previous.next.next;
            previous.next.prev = previous;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        int removeByIndex = getIndexIfContains(object);
        if (removeByIndex == -1) {
            return false;
        }
        remove(removeByIndex);
        return true;
    }

    private int getIndexIfContains(T object) {
        int index = -1;
        Node<T> current = head;
        int iteration = 0;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return iteration;
            }
            current = current.next;
            iteration++;
        }
        return index;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        if (size / 2 >= index) {
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }
}
