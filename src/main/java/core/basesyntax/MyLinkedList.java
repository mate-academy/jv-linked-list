package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX = "Invalid index ";
    private static final String EMPTY_LIST = "List is empty";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkTheIndexBeforeAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> currentNode = getNodeByIndex(index - 1);
            newNode.next = currentNode.next;
            if (currentNode.next != null) {
                currentNode.next.prev = newNode;
            }
            currentNode.next = newNode;
            newNode.prev = currentNode;
            if (newNode.next == null) {
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException(EMPTY_LIST);
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkTheIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkTheIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T valueToChange = currentNode.item;
        currentNode.item = value;
        return valueToChange;
    }

    @Override
    public T remove(int index) {
        checkTheIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException(EMPTY_LIST);
        }
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if ((object == nodeToRemove.item)
                    || (nodeToRemove.item != null && nodeToRemove.item.equals(object))) {
                unlink(nodeToRemove);
                size--;
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
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (size / 2 > index) {
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
    }

    private void checkTheIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private void checkTheIndexBeforeAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
