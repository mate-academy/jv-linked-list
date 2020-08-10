package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> newElement = new Node<>(last, value, null);
        if (size != 0) {
            last.next = newElement;
        } else {
            first = newElement;
        }
        last = newElement;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexCheck(index);
        if (index != 0) {
            Node<T> currentNode = getNodeByIndex(index).prev;
            Node<T> newElement = new Node<>(currentNode, value, currentNode.next);
            currentNode.next.prev = newElement;
            currentNode.next = newElement;
        } else {
            Node<T> newElement = new Node<>(null, value, first);
            first.prev = newElement;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> oldNode = getNodeByIndex(index);
        T deletedItem = oldNode.item;
        oldNode.item = value;
        return deletedItem;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == null ? t == null : currentNode.item.equals(t)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Index %s out of bounds for length %s", index, size));
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> currentNode = first;
            int counter = 0;
            while (counter < index) {
                currentNode = currentNode.next;
                counter++;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            int counter = size - 1;
            while (counter > index) {
                currentNode = currentNode.prev;
                counter--;
            }
            return currentNode;
        }
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
