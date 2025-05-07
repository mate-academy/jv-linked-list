package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private void addLast(T value) {
        Node<T> currentLast = last;
        Node<T> newNode = new Node<>(currentLast, value, null);
        last = newNode;
        if (currentLast == null) {
            first = newNode;
        } else {
            currentLast.next = newNode;
        }
        size++;
    }

    private void addBefore(T value, Node<T> before) {
        Node<T> pred = before.prev;
        Node<T> newNode = new Node<>(pred, value, before);
        before.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        indexExists(index);
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;

        }
        return temp;
    }

    private T unlink(Node<T> nodeToRemove) {
        final T element = nodeToRemove.item;
        Node<T> next = nodeToRemove.next;
        Node<T> prev = nodeToRemove.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            nodeToRemove.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            nodeToRemove.next = null;
        }
        nodeToRemove.item = null;
        size--;
        return element;
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, getNodeByIndex(index));
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T removed = remove(index);
        add(value, index);
        return removed;
    }

    @Override
    public T remove(int index) {
        indexExists(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
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

    private boolean indexExists(int index) {
        if (index < size && index >= 0) {
            return true;
        } else {
            throw new IndexOutOfBoundsException("Such index does not exist!");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
