package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_SIZE = 10;
    private static final double MULTIPLIER = 1.5;
    private int size;
    private Node[] elements;

    public MyLinkedList() {
        elements = new Node[DEFAULT_SIZE];
    }

    @Override
    public void add(T value) {
        grow();
        if (size == 0) {
            elements[0] = new Node<>(null, value, null);

        } else {
            elements[size] = new Node<>(elements[size - 1], value, null);
            elements[size - 1].next = elements[size];
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdd(index);
        grow();
        if (size == 0) {
            add(value);
            return;
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        if (index == 0) {
            elements[index] = new Node<>(null, value, elements[index + 1]);
            elements[index + 1].prev = elements[index];
            size++;
            return;
        } else if (index == size) {
            elements[index] = new Node<>(elements[index - 1], value, null);
            elements[index - 1].next = elements[index];
            size++;
            return;
        }
        elements[index] = new Node<>(elements[index - 1], value, elements[index + 1]);
        elements[index - 1].next = elements[index];
        elements[index + 1].prev = elements[index];
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return (T) elements[index].item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        T answer = (T) elements[index].item;
        elements[index].item = value;
        return answer;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        T removedElement = (T) elements[index].item;
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        int index = indexOf(object);
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

    private void grow() {
        if (size >= elements.length) {
            Node[] newNode = new Node[(int) (elements.length * MULTIPLIER)];
            System.arraycopy(elements, 0, newNode, 0, elements.length);
            elements = newNode;
        }
    }

    private int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (value == null && elements[i].item == null
                    || value != null && value.equals(elements[i].item)) {
                return i;
            }
        }
        return -1;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void indexCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
