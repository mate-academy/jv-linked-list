package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else if (size == 1) {
            last = new Node<>(first, value, null);
            first.next = last;
            size++;
        } else {
            Node<T> node = last;
            last = new Node<>(node, value, null);
            node.next = last;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (first == null) {
            checkIndex(index);
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getCurrentNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            }
            if (index == 0) {
                first = newNode;
            }
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getCurrentNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getCurrentNode(index);
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
                if (currentNode.item != null && currentNode.item.equals(object)) {
                    unlink(currentNode);
                    return true;
                }
            }
        }
        return false;
    }

    private T unlink(Node<T> node) {
        final T removedItem = node.item;
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return removedItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds"
                    + System.lineSeparator()
                    + "Index: " + index + System.lineSeparator()
                    + "Size: " + size);
        }
    }

    private void rangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds"
                    + System.lineSeparator()
                    + "Index: " + index + System.lineSeparator()
                    + "Size: " + size);
        }
    }

    private Node<T> getCurrentNode(int index) {
        rangeCheck(index);
        Node<T> currentNode;
        if (index < size / 2) {
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
