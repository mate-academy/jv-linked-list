package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;
    private  int midIndex = size / 2;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            addNoteToEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkElementIndexForAdd(index);
        if (size == 0) {
            addFirstNode(value);
        } else if (index == 0) {
            addNodeToStart(value);
        } else if (index == size) {
            addNoteToEnd(value);
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode.next);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = findNodeByIndex(index);
        T newValue = nodeByIndex.element;
        nodeByIndex.element = value;
        return newValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
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

    private void addNodeToStart(T value) {
        Node<T> newNode = new Node<>(first, value, null);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addNoteToEnd(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkElementIndex(int index) {

        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds exception! Please enter a correct value!");
        }
    }

    private void checkElementIndexForAdd(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds exception! Please enter a correct value!");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        int steps;

        if (index <= midIndex) {
            currentNode = first;
            steps = index;
        } else {
            currentNode = last;
            steps = size - 1 - index;
        }
        for (int i = 0; i < steps; i++) {
            currentNode = (index <= midIndex) ? currentNode.next : currentNode.prev;
        }
        return currentNode;
    }

    private T unlink(Node<T> x) {
        final T element = x.element;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.element = null;
        size--;
        return element;
    }

    private Node<T> node(int index) {

        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    public void addFirstNode(T element) {
        first = new Node<>(null, element, null);
        last = first;
        size++;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
