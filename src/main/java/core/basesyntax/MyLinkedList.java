package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        final Node<T> prevNode = last;
        final Node<T> newNode = new Node<>(prevNode, value, null);
        last = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> pointedNode = getNodeByIndex(index);
        addBefore(pointedNode, value);
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null) {
            return false;
        }
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> ourNode = getNodeByIndex(index);
        return ourNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> ourNode = getNodeByIndex(index);
        T oldValue = ourNode.item;
        ourNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> pointedNode = null;
        if (index == 0) {
            pointedNode = getNodeByIndex(index);
            final T value = pointedNode.item;
            removeNode(pointedNode);
            return value;
        }
        checkIndex(index);
        pointedNode = getNodeByIndex(index);
        final T value = pointedNode.item;
        removeNode(pointedNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
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

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addBefore(Node<T> node, T value) {
        if (node == null) {
            first = new Node<>(null, value, null);
            size++;
        } else {
            final Node<T> previousNode = node.prev;
            final Node<T> newNode = new Node<>(previousNode, value, node);
            node.prev = newNode;
            if (previousNode == null) {
                first = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> goalNode = first;
            for (int i = 0; i < index; i++) {
                goalNode = goalNode.next;
            }
            return goalNode;
        } else {
            Node<T> goalNode = last;
            for (int i = size - 1; i > index; i--) {
                goalNode = goalNode.prev;
            }
            return goalNode;
        }
    }

    private void removeNode(Node<T> node) {
        if (node != null) {
            final Node<T> previousNode = node.prev;
            final Node<T> nextNode = node.next;
            if (previousNode == null) {
                first = nextNode;
            } else {
                previousNode.next = nextNode;
                node.prev = null;
            }
            if (nextNode == null) {
                last = previousNode;
            } else {
                nextNode.prev = previousNode;
                node.next = null;
            }
            size--;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of LinkedList size");
        }
    }
}
