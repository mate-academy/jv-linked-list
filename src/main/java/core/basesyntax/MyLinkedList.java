package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = new Node<T>(null, value, null);
            last = first;
        } else {
            Node<T> oldLast = last;
            last = new Node<T>(oldLast, value, null);
            oldLast.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index" + index);
        }
        Node<T> current = findNode(index);
        Node<T> newNode = new Node<T>(null, value, null);
        if (current.prev == null) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
            newNode.next = current;
        }
        size++;
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
        indexCheck(index);
        Node<T> current = findNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> current = findNode(index);
        T returnedValue = current.value;
        current.value = value;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> current = findNode(index);
        deleteNode(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value == object || current.value != null
                    && current.value.equals(object)) {
                deleteNode(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void deleteNode(Node<T> current) {
        if (current.equals(first)) {
            first = first.next;
        } else {
            if (current.equals(last)) {
                last = last.prev;
                last.next = null;
            } else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        }
        size--;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
