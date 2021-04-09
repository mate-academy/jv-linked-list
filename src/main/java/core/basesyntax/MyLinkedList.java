package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        linkNode(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE + "[" + index + "]");
        }
        if (index == size) {
            linkNode(value);
            return;
        }
        linkNode(value, getNodeByIndex(index));
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexPosition(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexPosition(index);
        Node<T> node = getNodeByIndex(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndexPosition(index);
        return unlinkNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item
                    || object != null && object.equals(currentNode.item)) {
                unlinkNode(currentNode);
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

    private void linkNode(T item, Node<T> nearestNode) {
        Node<T> previousNode = nearestNode.prev;
        Node<T> newNode = new Node<>(previousNode, item, nearestNode);
        nearestNode.prev = newNode;
        if (previousNode != null) {
            previousNode.next = newNode;
        } else {
            first = newNode;
        }
        size++;
    }

    private void linkNode(T item) {
        Node<T> currentLastNode = last;
        Node<T> newLastNode = new Node<>(currentLastNode, item, null);
        last = newLastNode;
        if (currentLastNode != null) {
            currentLastNode.next = newLastNode;
        } else {
            first = newLastNode;
        }
        size++;
    }

    private T unlinkNode(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode != null) {
            previousNode.next = nextNode;
            node.prev = null;
        } else {
            first = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = previousNode;
            node.next = null;
        } else {
            last = previousNode;
        }
        size--;
        return node.item;
    }

    private boolean isElementPosition(int index) {
        return index >= 0 && index < size;
    }

    private void checkIndexPosition(int index) {
        if (!isElementPosition(index)) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE + "[" + index + "]");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if ((size >> 1) > index) {
            Node<T> nearestNode = first;
            for (int i = 0; i < index; i++) {
                nearestNode = nearestNode.next;
            }
            return nearestNode;
        }
        Node<T> nearestNode = last;
        for (int i = size - 1; i > index; i--) {
            nearestNode = nearestNode.prev;
        }
        return nearestNode;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
