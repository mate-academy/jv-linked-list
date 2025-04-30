package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> startNode;
    private Node<T> endNode;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            startNode = new Node<>(value, endNode, null);
        } else if (size == 1) {
            endNode = new Node<>(value, null, startNode);
            endNode.previous.next = endNode;
        } else {
            endNode = new Node<>(value, null, endNode);
            endNode.previous.next = endNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index - 1);
        linkAtMiddle(value, index);

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
        validateIndex(index);
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
        size--;
        return nodeToRemove.data;
    }

    private T removeFirst() {
        Node<T> nodeToRemove = startNode;
        unlink(startNode);
        size--;
        return nodeToRemove.data;
    }

    private void unlink(Node<T> node) {
        if (node == startNode) {
            if (startNode.next != null) {
                startNode = node.next;
            }
            return;
        }
        if (node == endNode) {
            endNode = node.previous;
            endNode.next = null;
            return;
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    private T removeByIndex(int index) {
        Node<T> node = findByIndex(index);
        unlink(node);
        size--;
        return node.data;
    }

    private void linkFirst(T value) {
        if (size == 0) {
            startNode = new Node<>(value, endNode, null);
        } else {
            startNode = new Node<>(value, startNode, null);
            startNode.next.previous = startNode;
        }
        size++;
    }

    private void linkAtMiddle(T value, int index) {
        Node<T> existingNode = findByIndex(index);
        Node<T> newNode = new Node<>(value,existingNode, existingNode.previous);
        if (newNode.next != null) {
            newNode.next.previous = newNode;
        }
        if (newNode.previous != null) {
            newNode.previous.next = newNode;
        }
        size++;
    }

    private Node<T> findByIndex(int index) {
        validateIndex(index);
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

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> previous;
        private Node<T> next;

        private Node(T data, Node<T> next, Node<T> previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }
}
