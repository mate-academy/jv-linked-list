package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstElement(value);
            return;
        }
        addElementAtLastIndex(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addElementAtZeroIndex(value);
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNodeByIndex(index);
        T oldValue = oldNode.item;
        oldNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> oldElement = getNodeByIndex(index);
        return unlink(oldElement).item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if ((currentNode.item == object || (currentNode.item != null
                    && currentNode.item.equals(object)))) {
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

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index <= size / 2 + 1) {
            return scanFromBegin(index);
        }
        return scanFromEnd(index);
    }

    private void addFirstElement(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first = newNode;
        last = newNode;
        size++;
    }

    private void addElementAtZeroIndex(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addElementAtLastIndex(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void removeSingleElement() {
        first = null;
        last = null;
        size--;
    }

    private void removeFirstElement() {
        Node<T> oldElement = first;
        first = oldElement.next;
        if (first != null) {
            first.prev = null;
        }
        oldElement.next = null;
        size--;
    }

    private void removeLastElement() {
        Node<T> oldElement = last;
        last = oldElement.prev;
        last.next = null;
        oldElement.prev = null;
        size--;
    }

    private Node<T> scanFromBegin(int index) {
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode;
    }

    private Node<T> scanFromEnd(int index) {
        Node<T> currentNode = last;
        int counter = size - 1;
        while (counter > index) {
            currentNode = currentNode.prev;
            counter--;
        }
        return currentNode;
    }

    private Node<T> unlink(Node<T> nodeToRemove) {
        if (nodeToRemove.prev == null) {
            removeFirstElement();
            return nodeToRemove;
        }
        if (nodeToRemove.next == null) {
            removeLastElement();
            return nodeToRemove;
        }

        Node<T> prevNode = nodeToRemove.prev;
        Node<T> nextNode = nodeToRemove.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        nodeToRemove.next = null;
        nodeToRemove.prev = null;
        size--;
        return nodeToRemove;
    }
}
