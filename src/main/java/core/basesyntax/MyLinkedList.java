package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
    }

    static class Node<T> {
        T value;
        Node next;
        Node prev;

        Node(T d) {
            prev = null;
            value = d;
            next = null;
        }
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("" + index + "out of bounds");
        }
    }

    public Node iteratorByIndex(int index) {
        checkIndex(index);
        Node result = head;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return result;
            }
            result = result.next;
        }
        return null;
    }

    public int iteratorByValue(T value) {
        Node result = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, result.value)) {
                return i;
            }
            result = result.next;
        }
        return size;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
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
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node shiftedElement = iteratorByIndex(index);
            Node newNode = new Node(value);
            newNode.prev = shiftedElement.prev;
            newNode.next = shiftedElement;
            shiftedElement.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
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
        return (T) iteratorByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        iteratorByIndex(index).value = value;
    }

    @Override
    public T remove(int index) {
        Node removedNode = iteratorByIndex(index);
        final Object value = removedNode.value;
        if (size == 1) {
            head = tail = null;
            size--;
            return (T) value;
        }
        if (removedNode == tail) {
            iteratorByIndex(index - 1).next = null;
            tail = iteratorByIndex(index - 1);
            size--;
            return (T) value;
        }
        if (removedNode == head) {
            iteratorByIndex(index + 1).prev = null;
            head = iteratorByIndex(index + 1);
            size--;
            return (T) value;
        }
        iteratorByIndex(index - 1).next = iteratorByIndex(index + 1);
        iteratorByIndex(index - 1).next.prev = iteratorByIndex(index - 1);
        size--;
        return (T) value;
    }

    @Override
    public T remove(T t) {
        int index = iteratorByValue(t);
        if (index == size) {
            return null;
        }
        Node removedNode = iteratorByIndex(index);
        if (removedNode == tail) {
            iteratorByIndex(index - 1).next = null;
            tail = iteratorByIndex(index - 1);
            size--;
            return (T) removedNode.value;
        }
        if (removedNode == head) {
            iteratorByIndex(index + 1).prev = null;
            head = iteratorByIndex(index + 1);
            size--;
            return (T) removedNode.value;
        }
        iteratorByIndex(index - 1).next = iteratorByIndex(index + 1);
        iteratorByIndex(index - 1).next.prev = iteratorByIndex(index - 1);
        size--;
        return (T) removedNode.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

