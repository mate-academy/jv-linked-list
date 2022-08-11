package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.nextElement = new Node<>(tail, value, null);
            tail = tail.nextElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> nextNode = searchByIndex(index);
        Node<T> previousNode = nextNode.previousElement;
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        nextNode.previousElement = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.nextElement = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentElement = searchByIndex(index);
        T oldValue = currentElement.element;
        currentElement.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentElement = head;
        for (int i = 0; currentElement != null; i++) {
            if (currentElement.element == object || currentElement.element != null
                    && currentElement.element.equals(object)) {
                unlink(currentElement);
                return true;
            }
            currentElement = currentElement.nextElement;
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
        private Node<T> previousElement;
        private T element;
        private Node<T> nextElement;

        public Node(Node<T> previousElement, T element, Node<T> nextElement) {
            this.previousElement = previousElement;
            this.element = element;
            this.nextElement = nextElement;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " low than 0 or bigger than array size" + size);
        }
    }

    private Node<T> searchByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.nextElement;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.previousElement;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        T element = node.element;
        Node<T> previousElement = node.previousElement;
        Node<T> nextElement = node.nextElement;
        if (previousElement == null) {
            head = nextElement;
        } else {
            previousElement.nextElement = nextElement;
            node.previousElement = null;
        }
        if (nextElement == null) {
            tail = previousElement;
        } else {
            nextElement.previousElement = previousElement;
            node.nextElement = null;
        }
        node.element = null;
        size--;
        return element;
    }
}
