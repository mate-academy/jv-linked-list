package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, false);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> current = getNode(index);

        if (current != null) {
            Node<T> prevNode = current.prev;
            if (prevNode != null) {
                prevNode.next = newNode;
            }
            newNode.prev = prevNode;
            newNode.next = current;
            current.prev = newNode;

            if (index == 0) {
                head = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, true);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, true);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, true);
        Node<T> current = getNode(index);
        T removedValue = current.value;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value.equals(object)) {
                remove((T) current);
                return true;
            }
            current = current.next;
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

    private Node<T> getNode(int index) {
        checkIndex(index, true);
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

    private void checkIndex(int index, boolean mustExist) {
        if (index < 0 || index >= size || (mustExist && getNode(index) == null)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }
}
