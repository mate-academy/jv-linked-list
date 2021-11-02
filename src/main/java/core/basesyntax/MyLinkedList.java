package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E value) {
            item = value;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(null, value, null);

        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out off list length " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (index == 0) {
            first = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
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
        checkIndex(index);
        Node<T> searchElement = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            searchElement = searchElement.next;
        }
        return searchElement.getItem();
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out off list length " + size);
        }
        Node<T> searchNode = getNodeByIndex(index);
        T oldValue = searchNode.item;
        searchNode.setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        removeNode(nodeByIndex);
        return nodeByIndex.getItem();
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = first;
        while (searchNode != null) {
            T item = searchNode.getItem();
            if (Objects.equals(item, object)) {
                removeNode(searchNode);
                return true;
            }
            searchNode = searchNode.next;
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
        checkIndex(index);
        Node searchNode = first;
        for (int i = 0; i < index; i++) {
            searchNode = searchNode.next;
            if (searchNode == null) {
                return null;
            }
        }
        return searchNode;

    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out off list length " + size);
        }
    }
}
