package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> lastElement;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            setFirstElement(value);
        } else {
            Node<T> newElement = new Node<>(value, null, lastElement);
            lastElement.next = newElement;
            if (lastElement.prev == null) {
                first.next = newElement;
            }
            lastElement = newElement;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        if (size == 0 || index == size) {
            add(value);
        } else {
            Node<T> element = getElement(index);
            Node<T> newElement = new Node<T>(value, element, element.prev);
            if (index > 0) {
                element.prev.next = newElement;
                element.prev = newElement;
            } else {
                first = newElement;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add((T) list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = getElement(index);
        T oldValue = element.value;
        element.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> element = getElement(index);
        T removedItemValue = element.value;
        if (size == 1) {
            first = null;
            lastElement = null;
        } else {
            if (element.next == null) {
                lastElement = element.prev;
            } else {
                element.next.prev = element.prev;
            }
            if (element.prev == null) {
                first = element.next;
            } else {
                element.prev.next = element.next;
            }
        }
        size--;
        return removedItemValue;
    }

    @Override
    public boolean remove(T t) {
        int counter = 0;
        while (counter < size) {
            if (getElement(counter).value == t
                    || (t != null && t.equals(getElement(counter).value))) {
                remove(counter);
                return true;
            }
            counter++;
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

    private void setFirstElement(T value) {
        Node<T> newElement = new Node<>(value, null, null);
        first = newElement;
        lastElement = newElement;
        size++;
    }

    private Node<T> getElement(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        Node<T> element = first;
        int counter = 0;
        if (index < size / 2) {
            while (counter < index) {
                element = element.next;
                counter++;
            }
        } else {
            element = lastElement;
            counter = size - 1;
            while (counter > index) {
                element = element.prev;
                counter--;
            }
        }
        return element;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
