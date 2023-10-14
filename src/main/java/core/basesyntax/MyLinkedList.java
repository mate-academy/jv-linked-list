package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkForSuitableIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(newNode);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            newNode.next = currentNode;
            newNode.previous = currentNode.previous;
            currentNode.previous.next = newNode;
            currentNode.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("The list is null!");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeOldValue = getNodeByIndex(index);
        T oldValue = nodeOldValue.value;
        nodeOldValue.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(index).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value
                    || (object != null && object.equals(currentNode.value))) {
                remove(i);
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
        Node<T> curentNode;
        if (index < size / 2) {
            curentNode = head;
            for (int i = 0; i < index; i++) {
                curentNode = curentNode.next;
            }
        } else {
            curentNode = tail;
            for (int i = size - 1; i > index; i--) {
                curentNode = curentNode.previous;
            }
        }
        return curentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid!");
        }
    }

    private void checkForSuitableIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid!");
        }
    }

    private Node<T> unlink(int index) {
        Node<T> unlinkNode = getNodeByIndex(index);
        if (unlinkNode.next == null && unlinkNode.previous == null) {
            head = tail = null;
        } else if (unlinkNode.previous == null) {
            head = head.next;
            head.previous = null;
        } else if (unlinkNode.next == null) {
            tail = tail.previous;
            tail.next = null;
        } else {
            unlinkNode.previous.next = unlinkNode.next;
            unlinkNode.next.previous = unlinkNode.previous;
        }
        size--;
        return unlinkNode;
    }

    private void addFirst(Node<T> newNode) {
        head.previous = newNode;
        newNode.next = head;
        head = newNode;
        size++;
    }
}
