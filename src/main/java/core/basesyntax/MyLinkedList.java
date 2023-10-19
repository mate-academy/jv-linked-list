package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String BOUNDS_EXCEPTION = "This index is not correct: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addNode = new Node<>(tail, value, null);
        tail = (tail == null) ? (head = addNode) : (tail.next = addNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkGreaterOrLess(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> addNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            head = addNode;
        } else {
            currentNode.prev.next = addNode;
        }
        currentNode.prev = addNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkValidIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkValidIndex(index);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkValidIndex(index);
        Node<T> removeNode = getNodeByIndex(index);
        unlink(removeNode);
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                unlink(currentNode);
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        head = (prevNode == null) ? nextNode : head;
        tail = (nextNode == null) ? prevNode : tail;
        if (prevNode != null) {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(BOUNDS_EXCEPTION + index);
        }
    }

    private void checkGreaterOrLess(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(BOUNDS_EXCEPTION + index);
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
