package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int MIDDLE_DIVIDER = 2;
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        final Node<T> oldNode = findNodeByIndex(index);
        final Node<T> previousNode = oldNode.prev;
        final Node<T> newNode = new Node<>(previousNode, value, oldNode);
        oldNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> nodeToSet = findNodeByIndex(index);
        final T oldValue = nodeToSet.item;
        nodeToSet.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findNodeByIndex(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.item == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.item)) {
                    unlink(currentNode);
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

    private void linkLast(T element) {
        Node<T> newNode = new Node<>(last, element, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / MIDDLE_DIVIDER) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> nextNode = node.next;
        final Node<T> previousNode = node.prev;
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
        node.item = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
