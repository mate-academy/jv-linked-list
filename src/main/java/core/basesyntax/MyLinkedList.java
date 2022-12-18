package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> theNode = getNode(index);
        Node<T> newNode = new Node<T>(theNode.prev, value, theNode);
        if (theNode.prev == null) {
            first = newNode;
        } else {
            theNode.prev.next = newNode;
        }
        theNode.prev = newNode;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> replaceNode = getNode(index);
        T removeElement = replaceNode.item;
        replaceNode.item = value;
        return removeElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = getNode(index);
        T removeElement = removeNode.item;
        unlink(removeNode);
        return removeElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> theNode = first;
        while (theNode != null) {
            if (object == theNode.item || object != null && object.equals(theNode.item)) {
                unlink(theNode);
                return true;
            }
            theNode = theNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect");
        }
    }

    private void unlink(Node<T> removeNode) {
        if (removeNode == first) {
            first = removeNode.next;
        } else if (removeNode == last) {
            last = removeNode.prev;
        } else {
            removeNode.next.prev = removeNode.prev;
            removeNode.prev.next = removeNode.next;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> nodeByIndex;
        if (index < size / 2) {
            nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }
}
