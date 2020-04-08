package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        assertIndexForAdd(index);
        Node<T> newNode = new Node(value);
        if (index == size) {
            add(value);
        } else {
            Node<T> oldNode = findByIndex(index);
            oldNode.previous.next = newNode;
            oldNode.previous = newNode;
            newNode.previous = oldNode.previous;
            newNode.next = oldNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        assertIndex(index);
        Node<T> result = findByIndex(index);
        return result.element;
    }

    @Override
    public T set(T value, int index) {
        assertIndex(index);
        T oldValue;
        Node<T> setNode = findByIndex(index);
        oldValue = setNode.element;
        setNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        assertIndex(index);
        T oldValue;
        if (index == 0 && size > 1) {
            Node<T> newFirst = first.next;
            oldValue = first.element;
            newFirst.previous = null;
            first = newFirst;
        } else if (index == 0 && size == 1) {
            oldValue = first.element;
            first = null;
            last = null;
        } else if (index == size - 1) {
            oldValue = last.element;
            last = last.previous;
            last.next = null;
        } else {
            Node<T> deleteNode = findByIndex(index);
            deleteNode.previous.next = deleteNode.next;
            deleteNode.next.previous = deleteNode.previous;
            oldValue = deleteNode.element;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T t) {
        Node<T> deleteNode = first;
        for (int i = 0; i < size; i++) {
            if (deleteNode.element == t || (t != null && t.equals(deleteNode.element))) {
                remove(i);
                return true;
            }
            deleteNode = deleteNode.next;
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

    private void assertIndexForAdd(int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void assertIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> previous;

        public Node(T element) {
            this.element = element;
            this.next = null;
            this.previous = null;
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> removeNode = first;
        for (int i = 0; i < index; i++) { ////
            removeNode = removeNode.next;
        }
        return removeNode;
    }
}
