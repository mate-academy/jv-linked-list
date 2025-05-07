package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = findNodeByIndex(index);
        addBeforeNode(value, node);
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        if (index == size - 1) {
            return removeLast();
        }
        if (index == 0) {
            return removeFirst();
        }
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.item == object || current.item != null
                    && current.item.equals(object)) {
                unlink(current);
                return true;
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

    private void addBeforeNode(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private T removeLast() {
        final T element = tail.item;
        Node<T> prevLast = tail.prev;
        tail = prevLast;
        if (prevLast == null) {
            head = null;
        } else {
            prevLast.next = null;
        }
        size--;
        return element;
    }

    private T removeFirst() {
        final T element = head.item;
        Node<T> nextFirst = head.next;
        head = nextFirst;
        if (nextFirst == null) {
            tail = null;
        } else {
            nextFirst.prev = null;
        }
        size--;
        return element;
    }

    private void unlink(Node<T> nodeToRemove) {
        Node<T> prevNode = nodeToRemove.prev;
        Node<T> nextNode = nodeToRemove.next;
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
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndexBounds(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            while (index > 0) {
                currentNode = currentNode.next;
                index--;
            }
            return currentNode;
        } else {
            currentNode = tail;
            while (index < size - 1) {
                currentNode = currentNode.prev;
                index++;
            }
            return currentNode;
        }
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
