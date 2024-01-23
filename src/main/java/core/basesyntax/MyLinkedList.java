package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
     private Node<T> last;
    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node (Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> current = (Node<T>) get(index);
        if (isEmpty()) {
            linkFirst(value);
        } else if (index == size()) {
            linkLast(value);
        } else {
            Node<T> newNode = new Node<>(current, value, current.next);
            current.next.prev = newNode;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            current = current.next;
            if (i == index){
                return (T) current;
            }
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index + 1);
        Node<T> current = (Node<T>) get(index);
        current.value = value;
        return (T) current;
    }

    @Override
    public T remove(int index) {
        checkIndex(index + 1);
        Node<T> current = (Node<T>) get(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        return (T) current;
    }

    @Override
    public boolean remove(T object) {
        int indexObject = searchIndex(object);
        if (checkIndex(indexObject)) {
            remove(indexObject);
            return true;
        };
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

    private void linkLast(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        last.next = newNode;
        last = newNode;
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<T>(null, value, first);
        first = newNode;
        last = newNode;
    }

    private boolean checkIndex(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is bad for size " + size());
        }
        return true;
    }

    private int searchIndex(T object) {
        if (object == null) {
            return -1;
        }
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            current = current.next;
            if (current.value.equals(object)){
                return i;
            }
        }
        return -1;
    }
}