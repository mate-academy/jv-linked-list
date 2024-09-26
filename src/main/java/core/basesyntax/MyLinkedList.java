package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        tail = new Node<>(tail, value, null);
        if (head == null) {
            head = tail;
        } else {
            tail.prev.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkBoundsInclusive(index);
        if (index < size) {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            if (nodeByIndex.prev == null) {
                head = newNode;
            } else {
                nodeByIndex.prev.next = newNode;
            }
            nodeByIndex.prev = newNode;
            size++;
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNodeByIndex(index);
        T oldItem = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = getNodeByIndex(index);
        removeNode(nodeByIndex);
        return nodeByIndex.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.item == null)
                    || (object != null && object.equals(currentNode.item))) {
                removeNode(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        checkBoundsExclusive(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            while (index > 0) {
                currentNode = currentNode.next;
                index--;
            }
        } else {
            currentNode = tail;
            while (index < size - 1) {
                currentNode = currentNode.prev;
                index++;
            }
        }
        return currentNode;
    }

    private void checkBoundsInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size:" + size);
        }
    }

    private void checkBoundsExclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size:" + size);
        }
    }

    private void removeNode(Node<T> node) {
        size--;
        if (size == 0) {
            head = tail = null;
        } else {
            if (node == head) {
                head = node.next;
                head.prev = null;
            } else if (node == tail) {
                tail = node.prev;
                tail.next = null;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
