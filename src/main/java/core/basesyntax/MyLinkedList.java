package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(value, null, first);
            if (first != null) {
                first.prev = newNode;
            }
            first = newNode;
            if (last == null) {
                last = newNode;
            }
            size++;
            return;
        }

        if (index == size()) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value, null, null);
        Node<T> nodeAtIndex = getNode(index);

        newNode.prev = nodeAtIndex.prev;
        newNode.next = nodeAtIndex;
        nodeAtIndex.prev = newNode;

        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        } else {
            first = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        size--;
        return node.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (object == null && current.data == null) {
                unlink(current);
                size--;
                return true;
            }
            if (object != null && object.equals(current.data)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        Node<T> current;
        int currentIndex;

        if (index < size() / 2) {
            current = first;
            currentIndex = 0;
            while (currentIndex < index) {
                current = current.next;
                currentIndex++;
            }
        } else {
            current = last;
            currentIndex = size() - 1;
            while (currentIndex > index) {
                current = current.prev;
                currentIndex--;
            }
        }

        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            first = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            last = prevNode;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

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
}
