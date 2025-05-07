package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE
            = "Index %d out of bounds for length %d";
    private static final int ZERO_INDEX = 0;
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(null, value, null);
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else if (index == ZERO_INDEX) {
            first.prev = new Node<>(null, value, first);
            first = first.prev;
            size++;
        } else {
            Node<T> movedNode = getNodeByIndex(index);
            movedNode.prev.next = new Node<>(movedNode.prev, value, movedNode);
            movedNode.prev = movedNode.prev.next;
            size++;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeOfReplacedValue = getNodeByIndex(index);
        T returnValue = nodeOfReplacedValue.item;
        nodeOfReplacedValue.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByValue(object);
        if (nodeToRemove == null) {
            return false;
        }
        unlink(nodeToRemove);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> nodeToRemove) {
        Node<T> nextNode = nodeToRemove.next;
        Node<T> previousNode = nodeToRemove.prev;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            nodeToRemove.prev = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
            nodeToRemove.next = null;
        }
        size--;
    }

    private Node<T> getNodeByValue(T value) {
        if (size == 0) {
            return null;
        }
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if ((currentNode.item == value)
                    || (currentNode.item != null && currentNode.item.equals(value))) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        int iterator;
        Node<T> currentElement;
        if (index < size / 2) {
            iterator = 0;
            currentElement = first;
            while (iterator != index) {
                currentElement = currentElement.next;
                iterator++;
            }
        } else {
            iterator = size - 1;
            currentElement = last;
            while (iterator != index) {
                currentElement = currentElement.prev;
                iterator--;
            }
        }
        return currentElement;
    }

    private void validateIndex(int index) {
        if (index >= size || index < ZERO_INDEX) {
            throw new IndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index, size));
        }
    }

    private static class Node<E> {
        private Node<E> prev;
        private E item;
        private Node<E> next;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
