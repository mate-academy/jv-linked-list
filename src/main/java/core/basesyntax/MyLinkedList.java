package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
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
        Node<T> nodeToRemove = getNode(index);
        if (nodeToRemove == first) {
            removeFirst();
        } else if (nodeToRemove == last) {
            removeLast();
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
            size--;
        }
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((current.data == null && object == null) || (current.data != null
                    && current.data.equals(object))) {
                if (current == first) {
                    removeFirst();
                } else if (current == last) {
                    removeLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
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
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index, index: "
                    + index + ", size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index, index: "
                    + index + ", size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index <= size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        if (first == null) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        first = newNode;
        size++;
    }

    private void removeFirst() {
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
    }

    private void removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.data = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
