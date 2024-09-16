package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            newNode.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> searchIndex = searchIndex(index);
            Node<T> newNode = new Node<>(searchIndex.prev, value, searchIndex);
            searchIndex.prev.next = newNode;
            searchIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchIndex(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private Node<T> searchIndex(int index) {
        Node<T> searchedNode = first;
        int i = 0;
        while (i < index) {
            searchedNode = searchedNode.next;
            i++;
        }
        return searchedNode;
    }

    private T unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null && next == null) {
            first = last = null;
        } else if (previous == null) {
            first = next;
            next.prev = null;
        } else if (next == null) {
            last = previous;
            previous.next = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
        size--;
        return node.value;
    }
}
