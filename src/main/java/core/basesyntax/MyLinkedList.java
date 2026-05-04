package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(value, null, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> oldHead = head;
            Node<T> newNode = new Node<>(value, null, oldHead);
            oldHead.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> previousNode = currentNode.prev;
            Node<T> newNode = new Node<>(value, previousNode, currentNode);
            previousNode.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldElement = currentNode.element;
        currentNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null) {
                if (current.element == null) {
                    unlink(current);
                    return true;
                }
            } else {
                if (object.equals(current.element)) {
                    unlink(current);
                    return true;
                }
            }
            current = current.next;
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

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds for add. Valid range: 0 to " + size
            );
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds. Valid range: 0 to " + (size - 1)
            );
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);

        if (index < size / 2) {
            Node<T> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private T unlink(Node<T> removedNode) {
        Node<T> prevNode = removedNode.prev;
        Node<T> nextNode = removedNode.next;
        final T removedElement = removedNode.element;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        removedNode.prev = null;
        removedNode.next = null;
        removedNode.element = null;
        size--;
        return removedElement;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
