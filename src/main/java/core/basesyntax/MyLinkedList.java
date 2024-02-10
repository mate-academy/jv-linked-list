package core.basesyntax;

import java.util.List;
import java.util.Objects;

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
        return getElementOnIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getElementOnIndex(index).value;
        getElementOnIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        final Node<T> current = getElementOnIndex(index);
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return null;
        }
        Node<T> tempPrevious = getElementOnIndex(index).previous;
        Node<T> tempNext = getElementOnIndex(index).next;
        if (getElementOnIndex(index).previous == null) {
            tempNext.previous = null;
            head = tempNext;
            size--;
            return current.value;
        } else if (getElementOnIndex(index).next == null) {
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

    @Override
    public boolean remove(T object) {
        if (size == 1) {
            head = null;
            size--;
            return true;
        }
        if (head.value.equals(object)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> current = head;
        int index = 0;
        while (!Objects.equals(current.value, object)) {
            current = current.next;
            index++;
            if (current == null) {
                break;
            }
        }
        if (index < size) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getElementOnIndex(int index) {
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
            throw new IndexOutOfBoundsException("Index is bigger than size");
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
        final Node<T> node = new Node<>();
        node.value = value;
        node.next = head;
        head.previous = node;
        head = node;
        size++;
    }

    private void addOnLastPosition(T value) {
        final Node<T> node = new Node<>();
        node.next = null;
        node.previous = tail;
        node.value = value;
        tail.next = node;
        tail = node;
        size++;
    }

    private void addByIndex(int index, T value) {
        Node<T> current = getElementOnIndex(index);
        final Node<T> node = new Node<>();
        node.previous = current.previous;
        current.previous.next = node;
        node.value = value;
        node.next = current;
        current.previous = node;
        size++;
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

        public Node() {
        }
    }
}
