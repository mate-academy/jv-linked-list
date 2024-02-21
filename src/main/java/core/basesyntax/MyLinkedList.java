package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            addLast(value);
        } else {
            Node<T> nodeAtIndex = getNode(index);
            Node<T> newNode = new Node<>(value, nodeAtIndex.prev, nodeAtIndex);
            if (nodeAtIndex.prev != null) {
                nodeAtIndex.prev.next = newNode;
            } else {
                first = newNode;
            }
            nodeAtIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        }
        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        if (nodeToRemove == first) {
            first = nodeToRemove.next;
        }
        if (nodeToRemove == last) {
            last = nodeToRemove.prev;
        }
        size--;
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = first;
        while (current != null) {
            if (value == null ? current.data == null : value.equals(current.data)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    first = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    last = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void addAll(List<T> elements) {
        for (T element : elements) {
            addLast(element);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        if (first == null) {
            first = last;
        }
        size++;
    }
}
