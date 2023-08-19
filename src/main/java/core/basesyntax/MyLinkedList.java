package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkRangeForIndex(index);
        Node<T> newNext = element_Definition(index);
        Node<T> newPred = newNext.prev;
        Node<T> newNode = new Node<>(newPred, value, newNext);
        newNext.prev = newNode;
        if (newPred == null) {
            first = newNode;
        } else {
            newPred.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkRangeForIndex(index);
        return element_Definition(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkRangeForIndex(index);
        Node<T> x = element_Definition(index);
        T oldValue = x.value;
        x.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRangeForIndex(index);
        Node<T> x = element_Definition(index);
        if (x.prev == null) {
            first = x.next;
        } else {
            x.prev.next = x.next;
        }
        if (x.next == null) {
            last = x.prev;
        } else {
            x.next.prev = x.prev;
        }
        size--;
        return x.value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (element_Definition(i).value == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(element_Definition(i).value)) {
                    remove(i);
                    return true;
                }
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

    private void checkRangeForIndex(int index) {
        if (index != 0 && (index < 0 || index >= size)) {
            throw new IndexOutOfBoundsException("The index is not valid");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> element_Definition(int index) {
        Node<T> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }
}
