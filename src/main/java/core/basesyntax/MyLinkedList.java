package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> currentNode = last;
        Node<T> newNode = new Node<>(currentNode, value, null);
        last = newNode;
        if (currentNode == null) {
            first = newNode;
        } else {
            currentNode.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, index > size);
        if (index == size) {
            add(value);
        } else {
            if (size / 2 > index) {
                Node<T> currentNode = first;
                for (int i = 0; i < index; i++) {
                    currentNode = currentNode.next;
                }
                Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                if (index == 0) {
                    currentNode = first;
                    newNode = new Node<>(null, value, currentNode);
                    first = newNode;
                    if (currentNode == null) {
                        first = newNode;
                    } else {
                        currentNode.prev = newNode;
                    }
                    size++;
                    return;
                }
                newNode.prev.next = newNode;
                currentNode.next = newNode;
                size++;
            } else {
                Node<T> currentNode = last;
                for (int i = size - 1; i > index; i--) {
                    currentNode = currentNode.prev;
                }
                Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                newNode.next.prev = newNode;
                currentNode.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null) {
            return false;
        }
        for (T t : list) {
            add(t);
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
        if (size / 2 > index) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            Node<T> oldValue = currentNode;
            if (index == 0) {
                removeFirst(currentNode);
                return oldValue.item;
            }
            currentNode.prev.next = oldValue.next;
            currentNode.next.prev = oldValue.prev;
            size--;
            return oldValue.item;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            Node<T> oldValue = currentNode;
            if (index == size - 1) {
                removeLast(currentNode);
                return oldValue.item;
            }
            currentNode.prev.next = oldValue.next;
            currentNode.next.prev = oldValue.prev;
            size--;
            return oldValue.item;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == null || currentNode.item.equals(object)) {
                remove(i);
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

    private void removeLast(Node<T> currentNode) {
        last = currentNode.prev;
        currentNode.next = null;
        size--;
    }

    private void removeFirst(Node<T> currentNode) {
        first = currentNode.next;
        currentNode.prev = null;
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
