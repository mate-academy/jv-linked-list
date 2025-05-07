package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        final Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            addBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T valueInList : list) {
            add(valueInList);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T value) {
        for (Node<T> nodeToFind = head; nodeToFind != null; nodeToFind = nodeToFind.next) {
            if ((value == null && nodeToFind.value == null)
                    || (value != null && value.equals(nodeToFind.value))) {
                unlink(nodeToFind);
                return true;
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

    private T unlink(Node<T> nodeToUnlink) {
        final T value = nodeToUnlink.value;
        final Node<T> nextNode = nodeToUnlink.next;
        final Node<T> previousNode = nodeToUnlink.prev;

        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
            nodeToUnlink.prev = null;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            nodeToUnlink.next = null;
        }
        nodeToUnlink.value = null;
        size--;
        return value;
    }

    private void addBefore(T value, Node<T> displacedNode) {
        final Node<T> previousOne = displacedNode.prev;
        final Node<T> newNode = new Node<>(previousOne, value, displacedNode);
        if (previousOne == null) {
            head = newNode;
        } else {
            previousOne.next = newNode;
        }
        displacedNode.prev = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size / 2)) {
            return serchFromHead(index);
        } else {
            return serchFromTail(index);
        }
    }

    private Node<T> serchFromHead(int index) {
        Node<T> nodeToReturn = head;
        for (int i = 0; i < index; i++) {
            nodeToReturn = nodeToReturn.next;
        }
        return nodeToReturn;
    }

    private Node<T> serchFromTail(int index) {
        Node<T> nodeToReturn = tail;
        for (int i = size - 1; i > index; i--) {
            nodeToReturn = nodeToReturn.prev;
        }
        return nodeToReturn;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + " is out of bounds for size: " + size;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
