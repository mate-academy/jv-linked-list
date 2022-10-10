package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int currentSize = 0;
    private Node<T> first = null;
    private Node<T> last = null;
    private Node<T> newNode = null;
    private Node<T> currentNode = null;

    static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (currentSize == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(value);
        } else if (index == currentSize) {
            addLast(value);
        } else {
            addMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.item;
        removeElement(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        boolean isRemoved = false;
        currentNode = first;
        if (object == null) {
            while (currentNode != null) {
                if (currentNode.item == null) {
                    isRemoved = true;
                    break;
                }
                currentNode = currentNode.next;
            }
        } else {
            while (currentNode != null) {
                if (object.equals(currentNode.item)) {
                    isRemoved = true;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
        if (isRemoved) {
            removeElement(currentNode);
        }
        return isRemoved;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addFirst(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        }
        currentSize++;
    }

    private void addLast(T value) {
        newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        currentSize++;
    }

    private void addMiddle(T value, int index) {
        currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        currentSize++;
    }

    private void removeElement(Node<T> node) {
        if (node.prev == null && node.next != null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null && node.prev != null) {
            node.prev.next = null;
        } else if (node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        currentSize--;
    }
}
