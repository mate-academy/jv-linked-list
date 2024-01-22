package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int SIZE_BIT_DIVIDER = 1;
    private static final int SIZE_SHIFTER = 1;
    private static final int ZERO_SIZE = 0;
    private static final int MINUS_ONE = -1;
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexElement(index);
        if (index == size) {
            add(value);
        } else {
            linkPreviousElement(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexPosition(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexPosition(index);
        Node<T> tempNode = getNodeByIndex(index);
        T oldValue = tempNode.value;
        tempNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexPosition(index);
        return unlinkNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> current = first; current != null; current = current.next) {
                if (current.value == null) {
                    unlinkNode(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = first; current != null; current = current.next) {
                if (object.equals(current.value)) {
                    unlinkNode(current);
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
        return size == ZERO_SIZE;
    }

    private void checkIndexElement(int index) {
        if (!isIndexElement(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexPosition(int index) {
        if (!isIndexPosition(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isIndexElement(int index) {
        return index > MINUS_ONE && index <= size;
    }

    private boolean isIndexPosition(int index) {
        return index > MINUS_ONE && index < size;
    }

    private T unlinkNode(Node<T> targetNode) {
        T value = targetNode.value;
        Node<T> previousNode = targetNode.previous;
        Node<T> nextNode = targetNode.next;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            targetNode.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            targetNode.next = null;
        }
        targetNode.value = null;
        size--;
        return value;
    }

    private void linkPreviousElement(T value, Node<T> targetNode) {
        Node<T> previousNode = targetNode.previous;
        Node<T> newNode = new Node<>(value,previousNode,targetNode);
        targetNode.previous = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size >> SIZE_BIT_DIVIDER)) {
            Node<T> iterateNodeFromFirst = first;
            for (int i = 0; i < index; i++) {
                iterateNodeFromFirst = iterateNodeFromFirst.next;
            }
            return iterateNodeFromFirst;
        } else {
            Node<T> iterateNodeFromLast = last;
            for (int i = size - SIZE_SHIFTER; i > index; i--) {
                iterateNodeFromLast = iterateNodeFromLast.previous;
            }
            return iterateNodeFromLast;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
