package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> headNode;
    private Node<T> tailNode;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        addLastNode(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLastNode(value);
            return;
        }
        isCorrectIndex(index);
        addBefore(value, findNode(index));
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T valuesFromList : list) {
            add(valuesFromList);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isCorrectIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        isCorrectIndex(index);
        Node<T> founded = findNode(index);
        T oldValue = founded.item;
        founded.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isCorrectIndex(index);
        Node<T> removed = findNode(index);
        T value = removed.item;
        removing(removed);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = headNode;
        if (object == null) {
            while (currentNode != null && currentNode.item != null) {
                currentNode = currentNode.nextNode;
            }
            if (currentNode == null) {
                return false;
            }
            removing(currentNode);
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (currentNode.item.equals(object)) {
                removing(currentNode);
                return true;
            }
            currentNode = currentNode.nextNode;
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

    private static class Node<T> {
        private T item;
        private Node<T> nextNode;
        private Node<T> previous;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.nextNode = next;
            this.previous = previous;
        }
    }

    private void addLastNode(T value) {
        Node<T> lastNode = tailNode;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tailNode = newNode;
        if (lastNode == null) {
            headNode = newNode;
        } else {
            lastNode.nextNode = newNode;
        }
        size++;
    }

    private void isCorrectIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index - " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> caughtNode = headNode;
        for (int i = 0; i < index; i++) {
            caughtNode = caughtNode.nextNode;
        }
        return caughtNode;
    }

    private void addBefore(T value, Node<T> neighbour) {
        Node<T> previous = neighbour.previous;
        Node<T> newNode = new Node<>(previous, value, neighbour);
        neighbour.previous = newNode;
        if (previous == null) {
            headNode = newNode;
        } else {
            previous.nextNode = newNode;
        }
        size++;
    }

    private void removing(Node<T> node) {
        Node<T> previousNode = node.previous;
        Node<T> nextNode = node.nextNode;
        if (nextNode == null) {
            tailNode = previousNode;
            node.previous = null;
        } else if (previousNode == null) {
            headNode = nextNode;
            node.nextNode = null;
        } else {
            previousNode.nextNode = nextNode;
            nextNode.previous = previousNode;
        }
        size--;
    }
}
