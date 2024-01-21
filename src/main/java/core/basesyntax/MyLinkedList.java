package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            addNodeInside(value, index);
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> result = getNode(index);
        T oldValue = result.value;
        result.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (object != null && object.equals(node.value) || object == node.value) {
                unlink(node);
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
        isIndexValid(index);
        Node<T> node = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void addNodeInside(T value, int index) {
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    private T unlink(Node<T> node) {
        if (node == first) {
            first = node.next;
        } else if (node == last) {
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return (T) node.value;
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

