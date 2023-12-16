package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String ILLEGAL_INDEX = "Illegal index ";
    public static final int NUMBER_ZERO = 0;
    public static final int NUMBER_ONE = 1;
    public static final int NUMBER_TWO = 2;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(Collection<? extends T> collection) {
        this();
        addCollection(collection);
    }

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == NUMBER_ZERO) {
            linkHead(value);
            return;
        } else if (index == size) {
            linkTail(value);
            return;
        }

        Node<T> nodeByIndex = getNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        nodeByIndex.prev.next = newNode;
        nodeByIndex.prev = newNode;
        ++size;
    }

    @Override
    public void addAll(List<T> list) {
        addCollection(list);
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNodeByIndex(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        T removedValue = nodeToRemove.value;
        unlink(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (nodeToRemove.value == object
                    || (nodeToRemove.value != null && nodeToRemove.value.equals(object))) {
                unlink(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void addCollection(Collection<? extends T> collection) {
        for (T item : collection) {
            linkTail(item);
        }
    }

    private void linkHead(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (isEmpty()) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        head = newNode;
        ++size;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        ++size;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null && next == null) {
            head = null;
            tail = null;
            --size;
            return;
        }

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        --size;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index == NUMBER_ZERO) {
            return head;
        }

        if (index == size - NUMBER_ONE) {
            return tail;
        }

        Node<T> currentNode = null;
        if (index < size / NUMBER_TWO) {
            currentNode = head.next;
            for (int i = NUMBER_ONE; i < size; i++) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail.prev;
            for (int i = size - NUMBER_TWO; i >= NUMBER_ZERO; i--) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < NUMBER_ZERO || index > size - NUMBER_ONE) {
            throw new IndexOutOfBoundsException(ILLEGAL_INDEX + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
