package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value);
            addNode(getNodeByIndex(index), newNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T elements : list) {
                add(elements);
            }
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T element = current.element;
        current.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(object, currentNode.element)) {
                unlink(currentNode);
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

    private T unlink(Node<T> removedNode) {
        if (removedNode.previous != null) {
            removedNode.previous.next = removedNode.next;
        }
        if (removedNode.next != null) {
            removedNode.next.previous = removedNode.previous;
        }
        if (removedNode == first) {
            first = removedNode.next;
        }
        if (removedNode == last) {
            last = removedNode.previous;
        }
        size--;
        return removedNode.element;
    }

    private void addNode(Node<T> nodeByIndex, Node<T> newNode) {
        newNode.next = nodeByIndex;
        newNode.previous = nodeByIndex.previous;
        if (first == nodeByIndex) {
            first = newNode;
        } else {
            newNode.previous.next = newNode;
        }
        nodeByIndex.previous = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Inserted index is not valid: "
                    + index + " Please try again.");
        }
        return (index <= size >> 1) ? getNodeFromStart(index) : getNodeFromEnd(index);
    }

    private Node<T> getNodeFromStart(int index) {
        int count = 0;
        Node<T> currentFromStart = first;
        while (count != index) {
            count++;
            currentFromStart = currentFromStart.next;
        }
        return currentFromStart;
    }

    private Node<T> getNodeFromEnd(int index) {
        int lastIndex = size - 1;
        Node<T> currentFromEnd = last;
        while (lastIndex != index) {
            lastIndex--;
            currentFromEnd = currentFromEnd.previous;
        }
        return currentFromEnd;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.element = value;
        }
    }
}
