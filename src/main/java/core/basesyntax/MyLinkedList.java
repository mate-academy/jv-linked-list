package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> lastBeforeAdd = tail;
        final Node<T> newNode = new Node<>(lastBeforeAdd, value, null);
        tail = newNode;
        if (lastBeforeAdd == null) {
            head = newNode;
        } else {
            lastBeforeAdd.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T element, int index) {
        checkElementIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = element;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    unlink(x);
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

    private T unlink(Node<T> nodeToUnlink) {
        final T element = nodeToUnlink.value;
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
        return element;
    }

    private void linkBefore(T value, Node<T> displacedNode) {
        final Node<T> previousOne = displacedNode.prev;
        final Node<T> newNode = new Node<>(previousOne, value, displacedNode);
        displacedNode.prev = newNode;
        if (previousOne == null) {
            head = newNode;
        } else {
            previousOne.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> nodeToReturn = head;
            for (int i = 0; i < index; i++) {
                nodeToReturn = nodeToReturn.next;
            }
            return nodeToReturn;
        } else {
            Node<T> nodeToReturn = tail;
            for (int i = size - 1; i > index; i--) {
                nodeToReturn = nodeToReturn.prev;
            }
            return nodeToReturn;
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + " is out of bounds for size: " + size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
}
