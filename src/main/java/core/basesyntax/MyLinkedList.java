package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
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
            return;
        }
        if (index == 0) {
            Node<T> newFirst = new Node<>(value, null, first);
            first.prev = newFirst;
            first = newFirst;
        } else {
            Node<T> node = findNode(index);
            Node<T> prevNode = node.prev;
            Node<T> newNode = new Node<>(value, prevNode, node);
            node.prev = newNode;
            prevNode.next = newNode;
        }
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = findNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = findNode(index);
        unLink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (elementsAreEqual(currentNode.value, object)) {
                unLink(currentNode);
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

    private Node<T> findNode(int index) {
        checkIndex(index);
        int nodeIndex = 0;
        Node<T> currentNode = first;
        while (nodeIndex != index) {
            currentNode = currentNode.next;
            nodeIndex++;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index + "for size " + size);
        }
    }

    private void unLink(Node<T> node) {
        if (node == first) {
            first = first.next;
        } else if (node == last) {
            last = last.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private boolean elementsAreEqual(T first, T last) {
        if (first == last || (first != null) && first.equals(last)) {
            return true;
        }
        return false;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
