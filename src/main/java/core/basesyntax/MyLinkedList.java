package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode = findElement(index);
        checkIndex(index, index > size);
        if (index == 0) {
            addFirst(value, currentNode);
            return;
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> prev = currentNode.prev;
            Node<T> newNode = new Node<>(prev, value, currentNode);
            currentNode.prev = newNode;
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    private void addFirst(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(null, value, currentNode);
        first = newNode;
        if (currentNode == null) {
            last = newNode;
        } else {
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null) {
            return false;
        }
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, index >= size);
        return findElement(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, index >= size);
        Node<T> newNode = findElement(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, index >= size);
        Node<T> currentNode = findElement(index);
        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                delete(i);
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

    private void checkIndex(int index, boolean b) {
        if (index < 0 || b) {
            throw new IndexOutOfBoundsException("The index " + index + " is invalid");
        }
    }

    private Node<T> findElement(int index) {
        if (size / 2 > index) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void removeFirst(Node<T> currentNode) {
        if (size == 1) {
            first = last = null;
            size--;
            return;
        }
        first = currentNode.next;
        currentNode.prev = null;
        size--;
    }

    private void removeLast(Node<T> currentNode) {
        last = currentNode.prev;
        currentNode.next = null;
        size--;
    }

    private void delete(int index) {
        Node<T> currentNode = findElement(index);
        Node<T> oldValue = currentNode;
        if (index == 0) {
            removeFirst(currentNode);
            return;
        }
        if (index == size - 1) {
            removeLast(currentNode);
            return;
        }
        currentNode.prev.next = oldValue.next;
        currentNode.next.prev = oldValue.prev;
        size--;
    }

    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
