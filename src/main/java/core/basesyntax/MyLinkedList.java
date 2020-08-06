package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    Node<T> first;
    Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> newElement = new Node<>(last, value, null);
        if (size == 0) {
            first = newElement;
        }
        last = newElement;
        if (newElement.prev != null) {
            newElement.prev.next = newElement;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Index %s out of bounds for length %s", index, size));
        }
        if (first == null || index == size) {
            add(value);
            return;
        }
        if (index != 0) {
            Node<T> currentNode = indexIterator(index).prev;
            Node<T> newElement = new Node<>(currentNode, value, currentNode.next);
            currentNode.next = newElement;
            currentNode.next.prev = newElement;
        } else {
            Node<T> currentNode = first;
            Node<T> newElement = new Node<>(null, value, currentNode);
            currentNode.prev = newElement;
            first = newElement;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return indexIterator(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        T oldItem = indexIterator(index).item;
        indexIterator(index).item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = indexIterator(index);
        if (index > 0 && index < size - 1) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        } else {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                if (index == 0) {
                    first = currentNode.next;
                    currentNode.next.prev = null;
                }
                if (index == size - 1) {
                    last = currentNode.prev;
                    currentNode.prev.next = null;
                }
            }
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < size) {
            if (currentNode.item == null ? t == null : currentNode.item.equals(t)) {
                remove(counter);
                return true;
            }
            currentNode = currentNode.next;
            counter++;
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

    private boolean indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Index %s out of bounds for length %s", index, size));
        }
        return true;
    }

    private Node<T> indexIterator(int index) {
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
