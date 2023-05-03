package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkForValidIndex(index);
        Node<T> newNode = new Node<>(value,null, null);
        if (index < size) {
            connectToNext(newNode, getNodeByIndex(index));
        }
        if (index > 0) {
            connectToPrev(newNode, getNodeByIndex(index - 1));
        }
        setHeadAndTail(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        T prevElement = getNodeByIndex(index).element;
        getNodeByIndex(index).element = value;
        return prevElement;
    }

    @Override
    public T remove(int index) {
        checkForValidIndex(index);
        Node<T> toRemove = getNodeByIndex(index);
        unlink(toRemove);
        return toRemove.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.element == object
                    || (node.element != null
                    && node.element.equals(object))) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private Node<T> getNodeByIndex(int index) {
        checkForValidIndex(index);
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index < size - 1) {
            Node<T> node = head;
            int indexCount = 0;
            while (node != null) {
                if (indexCount == index) {
                    return node;
                }
                indexCount++;
                node = node.next;
            }
        } else {
            Node<T> node = tail;
            int indexCount = size - 1;
            while (node != null) {
                if (indexCount == index) {
                    return node;
                }
                indexCount--;
                node = node.prev;
            }
        }
        throw new NoSuchElementException();
    }

    private void unlink(Node<T> node) {
        Node<T> prev = null;
        Node<T> next = null;
        if (node.prev != null) {
            prev = node.prev;
            prev.next = null;
        }
        if (node.next != null) {
            next = node.next;
            next.prev = null;
        }
        if (prev != null) {
            connectToNext(prev, next);
            setHeadAndTail(prev);
        }
        if (next != null) {
            connectToPrev(next, prev);
            setHeadAndTail(next);
        }
        size--;
    }

    private void checkForValidIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void connectToPrev(Node<T> current, Node<T> prev) {
        if (prev == null) {
            current.prev = null;
            return;
        }
        current.prev = prev;
        prev.next = current;
    }

    private void connectToNext(Node<T> current, Node<T> next) {
        if (next == null) {
            current.next = null;
            return;
        }
        current.next = next;
        next.prev = current;
    }

    private void setHeadAndTail(Node<T> current) {
        if (current.prev == null) {
            head = current;
        }
        if (current.next == null) {
            tail = current;
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T element;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
