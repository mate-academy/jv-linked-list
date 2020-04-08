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
        Node<T> setNode = findByIndex(index);
        T oldValue = setNode.element;;
        setNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        assertIndex(index);
        Node<T> node = findByIndex(index);
        Node<T> prev = node.previous;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
        }
        size--;
        return node.element;
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
        Node<T> removeNode;
        if (index <= size / 2) {
            removeNode = first;
            for (int i = 0; i < index; i++) {
                removeNode = removeNode.next;
            }
        } else {
            removeNode = last;
            for (int i = size - 1; i > index; i--) {
                removeNode = removeNode.previous;
            }
        }
        return removeNode;
    }
}
