package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexNotIncludSize(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
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
        checkIndexIncludSize(index);
        Node<T> node = findNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexIncludSize(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexIncludSize(index);
        return unlink(findNodeByIndex(index));
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void linkLast(T element) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, element, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> pushedNode) {
        Node<T> prevNode = pushedNode.prev;
        Node<T> newNode = new Node(prevNode, element, pushedNode);
        pushedNode.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        final T element = node.item;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = (size - 1); i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndexNotIncludSize(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Illegal Index: " + index);
        }
    }

    private void checkIndexIncludSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal Index: " + index);
        }
    }
}
