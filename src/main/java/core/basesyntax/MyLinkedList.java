package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(value, null, null);
            first = newNode;
        } else {
            newNode = new Node<>(value, last, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(value, null, first);
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> currentPlace = iterator(index);
            newNode = new Node<>(value, currentPlace.prev, currentPlace);
            currentPlace.prev.next = newNode;
            currentPlace.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index == size - 1) {
            return last.element;
        }
        Node<T> currentNode = iterator(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldElement;
        if (index == 0) {
            oldElement = first.element;
            first.element = value;
            return oldElement;
        }
        if (index == size - 1) {
            oldElement = last.element;
            last.element = value;
            return oldElement;
        }
        Node<T> currentNode = iterator(index);
        oldElement = currentNode.element;
        currentNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T result;
        if (index == 0) {
            result = first.element;
            if (size == 1) {
                first = last = null;
            } else {
                first.next.prev = null;
                first = first.next;
            }
        } else if (index == size - 1) {
            result = last.element;
            last = last.prev;
            last.next = null;
        } else {
            Node<T> removedNode = iterator(index);
            result = removedNode.element;
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> removedNode = first;
        for (int i = 0; i < size; i++) {
            if (removedNode.element == t
                    || removedNode.element != null && removedNode.element.equals(t)) {
                remove(i);
                return true;
            }
            removedNode = removedNode.next;
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

    private Node<T> iterator(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i != index; i++) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index does not exist");
        }
    }

    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        public Node(T element, Node<T> previous, Node<T> next) {
            this.element = element;
            this.prev = previous;
            this.next = next;
        }
    }
}
