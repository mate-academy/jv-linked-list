package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't do anything with this index");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(node(index).prev, value, node(index).next);
            node(index).prev.next = newNode;
            node(index).next.prev = newNode;
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
        while (!(Objects.equals(removingNode.item, object))) {
            if (removingNode.next == null) {
                return false;
            }
            removingNode = removingNode.next;
        }
        unlink(removingNode);
        size--;
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
        if (node == first) {
            first = node.next;
        }
        T item = node.item;
        node.next.prev = node.prev;
        if (node.prev != null) {
            node.prev.next = node.next;
            node.prev = null;
        }
        node.next = null;
        node.item = null;
        size--;
        return item;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't do anything with this index");
        }
    }
}
