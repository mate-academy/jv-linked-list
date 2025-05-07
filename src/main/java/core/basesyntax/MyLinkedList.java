package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS = "Index - %d is out of bounds";
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
        } else if (last == null) {
            last = new Node<>(first, value, null);
            first.next = last;
        } else {
            Node<T> previousNode = last;
            Node<T> newNode = new Node<>(previousNode, value, null);
            previousNode.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> nextNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(nextNode.previous, value, nextNode);
            nextNode.previous = newNode;
            first = newNode;
        } else {
            Node<T> nextNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(nextNode.previous, value, nextNode);
            nextNode.previous.next = newNode;
            nextNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T newItem : list) {
            add(newItem);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToBeSet = findNodeByIndex(index);
        T item = nodeToBeSet.item;
        nodeToBeSet.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToBeRemove = findNodeByIndex(index);
        unlinkNode(nodeToBeRemove, index);
        size--;
        return nodeToBeRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToBeRemove = first;
        for (int i = 0; i < size; i++) {
            if (nodeToBeRemove.item == object
                    || nodeToBeRemove.item != null && nodeToBeRemove.item.equals(object)) {
                unlinkNode(nodeToBeRemove, i);
                size--;
                return true;
            }
            nodeToBeRemove = nodeToBeRemove.next;
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

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS, index));
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkElementIndex(index);
        Node<T> resultNode = first;
        for (int i = 0; i < index; i++) {
            resultNode = resultNode.next;
        }
        return resultNode;
    }

    private void unlinkNode(Node<T> nodeToBeRemove, int index) {
        Node<T> previousNode = nodeToBeRemove.previous;
        Node<T> nextNode = nodeToBeRemove.next;
        if (previousNode == null && nextNode == null) {
            first = null;
        } else if (index == 0) {
            nextNode.previous = null;
            first = nextNode;
        } else if (index == size - 1) {
            assert previousNode != null;
            previousNode.next = null;
            last = previousNode;
        } else {
            assert previousNode != null;
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> previous;

        Node(Node<E> previous, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
