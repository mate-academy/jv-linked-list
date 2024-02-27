package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> startNode;
    private Node<T> endNode;
    private int size;

    @Override
    public void add(T value) {
        if (!addedWithBaseOrTaleInitialization(value)) {
            linkLast(new Node<>(value));
        }
    }

    @Override
    public void add(T value, int index) {
        if (!addedWithBaseOrTaleInitialization(value, index)) {
            if (index == size) {
                linkLast(new Node<>(value));
            } else if (index == 0) {
                linkFirst(new Node<>(value));
            } else {
                indexValidation(index);
                linkAtMiddle(new Node<>(value), index);
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T returnValue = node.data;
        node.data = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            return removeByIndex(index);
        }
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node<T> node = startNode;
        while (node != null) {
            T data = node.data;
            if (data == object || object != null && object.equals(data)) {
                remove(index);
                return true;
            }
            index++;
            node = node.next;
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

    private T removeLast() {
        Node<T> nodeToRemove = endNode;
        unlink(endNode);
        return nodeToRemove.data;
    }

    private T removeFirst() {
        Node<T> nodeToRemove = startNode;
        unlink(startNode);
        return nodeToRemove.data;
    }

    private void unlink(Node<T> node) {
        if (node.previous == null) {
            startNode = startNode.next;
            size--;
        } else {
            endNode = endNode.previous;
            size--;
        }
    }

    private T removeByIndex(int index) {
        Node<T> node = findByIndex(index);
        if (size == 2) {
            if (index == 1) {
                startNode.next = null;
                endNode = null;
            } else if (index == 0) {
                startNode = endNode;
                endNode = null;
                startNode.next = null;
            }
        } else if (size == 1) {
            node = startNode;
            startNode = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
        return node.data;
    }

    private void linkLast(Node<T> node) {
        node.previous = endNode;
        endNode.next = node;
        endNode = node;
        size++;
    }

    private void linkFirst(Node<T> node) {
        startNode.previous = node;
        node.next = startNode;
        startNode = node;
        size++;
    }

    private void linkAtMiddle(Node<T> newNode, int index) {
        Node<T> existingNode = findByIndex(index);
        newNode.previous = existingNode.previous;
        existingNode.previous.next = newNode;
        newNode.next = existingNode;
        existingNode.previous = newNode;
        size++;
    }

    private Node<T> findByIndex(int index) {
        indexValidation(index);
        if (index >= 0 && index < 2 && size < 2) {
            return index == 0 ? startNode : endNode;
        }
        if (index - size / 2 <= 0) {
            return findNodeFromStart(index);
        } else {
            return findNodeFromEnd(index);
        }
    }

    private Node<T> findNodeFromStart(int index) {
        Node<T> x = startNode;
        int currentIndex = 0;
        while (index != currentIndex) {
            x = x.next;
            currentIndex++;
        }
        return x;
    }

    private Node<T> findNodeFromEnd(int index) {
        Node<T> x = endNode;
        int currentIndex = size - 1;
        while (index != currentIndex) {
            x = x.previous;
            currentIndex--;
        }
        return x;
    }

    private void initializeBase(Node<T> node) {
        startNode = node;
        size++;
    }

    private void initializeTale(Node<T> node) {
        endNode = node;
        startNode.next = endNode;
        endNode.previous = startNode;
        size++;
    }

    private void indexValidation(int index) {
        if (index < size && index >= 0) {
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private boolean addedWithBaseOrTaleInitialization(T value, int index) {
        if (size == 0 && index == 0) {
            initializeBase(new Node<>(value));
            return true;
        } else if (size == 1 && index == 1) {
            initializeTale(new Node<>(value));
            return true;
        }
        return false;
    }

    private boolean addedWithBaseOrTaleInitialization(T value) {
        if (size == 0) {
            initializeBase(new Node<>(value));
            return true;
        } else if (size == 1) {
            initializeTale(new Node<>(value));
            return true;
        }
        return false;
    }

    private class Node<T> {
        private T data;
        private Node<T> previous;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }
}
