package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

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
        if (index == size) {
            add(value);
            return;
        }
        get(index);
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        if (index == 0) {
            first = newNode;
        } else {
            oldNode.prev.next = newNode;
        }
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        isIndexValid(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        get(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);
        Node<T> removedNode = getNode(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (object != null && object.equals(i.value) || object == i.value) {
                unlink(i);
                return true;
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

    private void isIndexValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can`t get value by index: " + index);
        }
    }

    private Node<T> getNode(int index) {
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }

    }
}

