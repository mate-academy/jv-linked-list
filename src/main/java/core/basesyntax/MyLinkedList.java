package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null && tail == null && size == 0) {
            addToEmptyList(value);
        } else if (head != null && tail == null) {
            addInside(value);
        } else {
            addIfLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is bigger than size");
        }
        if (size == 0) {
            add(value);
            return;
        }
        if (index == 0) {
            addAtFirstPosition(value);
            return;
        } else if (index == size) {
            addOnLastPosition(value);
            return;
        }
        addByIndex(index, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getElementByIndex(index).value;
        getElementByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getElementByIndex(index));
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == t || node.value != null && node.value.equals(t)) {
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

    private Node<T> getElementByIndex(int index) {
        checkIndex(index);
        if (index <= size / 2) {
            Node<T> current = head;
            int i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
            return current;
        } else {
            Node<T> current = tail;
            int i = size - 1;
            while (i != index) {
                current = current.previous;
                i--;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is bigger than size: " + size);
        }
    }

    private void addToEmptyList(T value) {
        Node<T> node = new Node<T>(null, value, null);
        head = node;
        tail = node;
        size++;
    }

    private void addInside(T value) {
        Node<T> node = new Node<>(head, value, null);
        head.next = node;
        tail = node;
        node.previous = head;
        size++;
    }

    private void addIfLast(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    private void addAtFirstPosition(T value) {
        final Node<T> node = new Node<>(null, value, head);
        head.previous = node;
        head = node;
        size++;
    }

    private void addOnLastPosition(T value) {
        final Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    private void addByIndex(int index, T value) {
        Node<T> current = getElementByIndex(index);
        final Node<T> node = new Node<>(current.previous, value, current);
        current.previous.next = node;
        current.previous = node;
        size++;
    }

    private T unlink(Node node) {
        final Node<T> current = node;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return current.value;
        }
        Node<T> tempPrevious = node.previous;
        Node<T> tempNext = node.next;
        if (node.previous == null) {
            tempNext.previous = null;
            head = tempNext;
            size--;
            return current.value;
        } else if (node.next == null) {
            tempPrevious.next = null;
            tail = tempPrevious;
            size--;
            return current.value;
        } else {
            tempPrevious.next = tempNext;
            tempNext.previous = tempPrevious;
            size--;
            return current.value;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
