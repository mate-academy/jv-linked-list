package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Can't add an element on index " + index + " for list size " + size);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> searchingNode = node(index);
            Node<T> newNode = new Node<>(searchingNode.prev, value, searchingNode);
            if (index == 0) {
                first = newNode;
            } else {
                newNode.prev.next = newNode;
            }
            newNode.next.prev = newNode;
            size++;
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
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T item = node(index).item;
        node(index).item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removingNode = first;
        while (!((removingNode.item == object)
                || (removingNode.item != null && removingNode.item.equals(object)))) {
            if (removingNode.next == null) {
                return false;
            }
            removingNode = removingNode.next;
        }
        unlink(removingNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> node(int index) {
        checkIndex(index);
        Node<T> searchingNode;
        if (size / 2 > index) {
            searchingNode = first;
            for (int i = 0; i < index; i++) {
                searchingNode = searchingNode.next;
            }
        } else {
            searchingNode = last;
            for (int i = 0; i < size - index - 1; i++) {
                searchingNode = searchingNode.prev;
            }
        }
        return searchingNode;
    }

    private T unlink(Node<T> node) {
        final T item = node.item;
        if (node == first) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == last) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.item = null;
        node.next = null;
        size--;
        return item;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index " + index + " for list size " + size);
        }
    }
}
