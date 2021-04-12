package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> inputValue;
        if (size == 0) {
            inputValue = new Node<>(null, value, null);
            first = inputValue;
        } else {
            inputValue = new Node<>(last, value, null);
            last.next = inputValue;
        }
        last = inputValue;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexSize(index);
        if (index == 0) {
            Node<T> addedValue = new Node<>(null, value, first);
            first.prev = addedValue;
            first = addedValue;
        } else {
            Node<T> currentValue = getNode(index);
            Node<T> addedValue = new Node<>(currentValue.prev, value, currentValue);
            currentValue.prev.next = addedValue;
            currentValue.prev = addedValue;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexSize(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexSize(index);
        T returnedValue = getNode(index).item;
        getNode(index).item = value;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        indexSize(index);
        Node<T> removedValue = getNode(index);
        if (removedValue.next == null) {
            last = removedValue.prev;
        } else if (removedValue.prev == null) {
            first = removedValue.next;
        } else {
            removedValue.prev.next = removedValue.next;
            removedValue.next.prev = removedValue.prev;
        }
        size--;
        return removedValue.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).item == object || object != null && object.equals(getNode(i).item)) {
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> inputValue;
        if (size / 2 >= index) {
            inputValue = first;
            for (int i = 0; i < index; i++) {
                inputValue = inputValue.next;
            }
        } else {
            inputValue = last;
            for (int i = size - 1; i > index; i--) {
                inputValue = inputValue.prev;
            }
        }
        return inputValue;
    }

    private void indexSize(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Such index doesn't exist");
        }
    }
}
