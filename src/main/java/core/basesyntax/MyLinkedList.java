package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> nextNode = findNode(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            if (prevNode == null) {
                first = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
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
        checkPosition(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPosition(index);
        Node<T> currentNode = findNode(index);
        T returnItem = currentNode.item;
        currentNode.item = value;
        return returnItem;
    }

    @Override
    public T remove(int index) {
        checkPosition(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((object == null && findNode(i).item == null)
                    || (object != null && object.equals(findNode(i).item))) {
                unlink(findNode(i));
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

    private T unlink(Node<T> deletedNode) {
        Node<T> nextNode = deletedNode.next;
        Node<T> prevNode = deletedNode.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return deletedNode.item;
    }

    private void checkPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid!");
        }
    }

    private void checkPositionAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is invalid!");
        }
    }

    private Node<T> findNode(int index) {
        Node<T> findNode;
        if (index < size / 2) {
            findNode = first;
            for (int i = 0; i < index; i++) {
                findNode = findNode.next;
            }
        } else {
            findNode = last;
            for (int i = size - 1; i > index; i--) {
                findNode = findNode.prev;
            }
        }
        return findNode;
    }
}
