package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexIsValid(index, size, "Trying to add at nonexistent or negative index");
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addAtBeginning(value);
            return;
        }
        addInMiddle(value, index);
    }

    @Override
    public boolean addAll(List<T> list) {
        list.forEach(this::add);
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexIsValid(index, size - 1, "Trying to get the value at non "
                + "existing or negative index");
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexIsValid(index, size - 1, "Trying to set at nonexistent or negative index");
        Node<T> nodeAtIndex = findNodeByIndex(index);
        T oldValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexIsValid(index, size - 1, "Trying to remove at nonexistent or negative index");
        if (index == 0) {
            return removeFirstElement();
        }
        if (index == size - 1) {
            return removeLastElement();
        }
        return removeFromMiddle(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        if (head.value == object || head.value.equals(object)) {
            removeFirstElement();
            return true;
        }

        while (temp != null) {
            if (temp.value == null || temp.value.equals(object)) {
                removeFromMiddle(temp);
                return true;
            }
            temp = temp.next;
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
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndexIsValid(int index, int size, String message) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(message);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int count = 0;
        Node<T> searchResult = head;
        while (count < index) {
            searchResult = searchResult.next;
            count++;
        }
        return searchResult;
    }

    private T removeFromMiddle(Node<T> nodeAtIndex) {
        Node<T> previous = nodeAtIndex.previous;
        Node<T> next = nodeAtIndex.next;
        previous.next = next;
        next.previous = previous;
        size--;
        return nodeAtIndex.value;
    }

    private T removeLastElement() {
        Node<T> previousToTail = tail.previous;
        previousToTail.next = null;
        T oldValue = tail.value;
        tail = previousToTail;
        size--;
        return oldValue;
    }

    private T removeFirstElement() {
        Node<T> nextToHead = head.next;
        T oldValue = head.value;
        if (nextToHead != null) {
            nextToHead.previous = null;
            head = nextToHead;
        } else {
            head = null;
        }
        size--;
        return oldValue;
    }

    private void addInMiddle(T value, int index) {
        Node<T> nodeAtIndex = findNodeByIndex(index);
        Node<T> previousNodeToIndex = nodeAtIndex.previous;
        Node<T> newNode = new Node<>(previousNodeToIndex, value, nodeAtIndex);
        previousNodeToIndex.next = newNode;
        nodeAtIndex.previous = newNode;
        size++;
    }

    private void addAtBeginning(T value) {
        Node<T> tempNode = new Node<>(null, value, head);
        head.previous = tempNode;
        head = tempNode;
        size++;
    }

}
