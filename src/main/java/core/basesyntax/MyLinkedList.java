package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE =
            "Index: is out of bound for length ";
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkNewHead(value);
            return;
        }
        linkNewTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            linkNewHead(value);
            return;
        }
        if (index == size) {
            linkNewTail(value);
            return;
        }
        linkBefore(value, getNodeByIndex(index));
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
        Node<T> targetNode = getNodeByIndex(index);
        T oldValue = targetNode.item;
        targetNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        if (index == 0) {
            return unlinkHead(nodeToRemove);
        }
        if (index == size - 1) {
            return unlinkTail(nodeToRemove);
        }
        return unlink(nodeToRemove);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkBefore(T value, Node<T> next) {
        Node<T> previousNode = next.prev;
        Node<T> newNode = new Node<>(value, next, previousNode);
        next.prev = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private void linkNewHead(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(value, head, null);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkNewTail(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private T unlink(Node<T> nodeToUnlink) {
        final T removeValue = nodeToUnlink.item;
        Node<T> nextNode = nodeToUnlink.next;
        Node<T> previous = nodeToUnlink.prev;
        if (previous == null) {
            head = nextNode;
        } else {
            previous.next = nextNode;
            nodeToUnlink.prev = null;
        }

        if (nextNode == null) {
            tail = previous;
        } else {
            nextNode.prev = previous;
            nodeToUnlink.next = null;
        }
        nodeToUnlink.item = null;
        size--;
        return removeValue;
    }

    private T unlinkHead(Node<T> nodeToUnlink) {
        final T removeItem = nodeToUnlink.item;
        Node<T> nextNode = nodeToUnlink.next;
        nodeToUnlink.item = null;
        nodeToUnlink.next = null;
        head = nextNode;
        if (nextNode == null) {
            tail = null;
        } else {
            nextNode.prev = null;
        }
        size--;
        return removeItem;
    }

    private T unlinkTail(Node<T> nodeToUnlink) {
        final T removeItem = nodeToUnlink.item;
        Node<T> prev = nodeToUnlink.prev;
        nodeToUnlink.item = null;
        nodeToUnlink.prev = null;
        tail = prev;
        if (prev == null) {
            head = null;
        } else {
            prev.next = null;
        }
        size--;
        return removeItem;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE + index);
        }
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        if (Objects.equals(value, head.item)) {
            return head;
        }
        while (currentNode.next != null) {
            if (Objects.equals(value, currentNode.item)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index > size / 2 + 1) {
            return searchByIndexFromTail(index);
        }
        return searchByIndexFromHead(index);
    }

    private Node<T> searchByIndexFromHead(int targetIndex) {
        Node<T> current = head;
        for (int i = 0; i <= targetIndex; i++) {
            if (i == targetIndex) {
                break;
            }
            current = current.next;
        }
        return current;
    }

    private Node<T> searchByIndexFromTail(int targetIndex) {
        Node<T> current = tail;
        for (int i = size - 1; i >= targetIndex; i--) {
            if (i == targetIndex) {
                break;
            }
            current = current.prev;
        }
        return current;
    }
}
