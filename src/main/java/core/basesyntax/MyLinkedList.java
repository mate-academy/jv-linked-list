package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        addLastValue(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLastValue(value);
        } else {
            addFirstValue(value, findNodeByIndex(index));
        }
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
        return findNodeByIndex(index).currentElemet;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> element = findNodeByIndex(index);
        T elementToReplace = element.currentElemet;
        element.currentElemet = value;
        return elementToReplace;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T t) {
        for (Node<T> node = firstNode; node != null; node = node.nextElement) {
            if (node.currentElemet == t || node.currentElemet != null
                    && node.currentElemet.equals(t)) {
                unlink(node);
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

    private void addLastValue(T value) {
        Node<T> prev = lastNode;
        Node<T> newNode = new Node<>(prev, value, null);
        lastNode = newNode;
        if (prev == null) {
            firstNode = newNode;
        } else {
            prev.nextElement = newNode;
        }
        size++;
    }

    private void addFirstValue(T t, Node<T> element) {
        Node<T> prev = element.prevElement;
        Node<T> newNode = new Node<>(prev, t, element);
        element.prevElement = newNode;
        if (prev == null) {
            firstNode = newNode;
        } else {
            prev.nextElement = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> nodeToReturn = firstNode;
            for (int i = 0; i < index; i++) {
                nodeToReturn = nodeToReturn.nextElement;
            }
            return nodeToReturn;
        } else {
            Node<T> nodeToReturn = lastNode;
            for (int i = size - 1; i > index; i--) {
                nodeToReturn = nodeToReturn.prevElement;
            }
            return nodeToReturn;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.currentElemet;
        Node<T> next = node.nextElement;
        Node<T> prev = node.prevElement;
        if (prev == null) {
            firstNode = next;
        } else {
            prev.nextElement = next;
            node.prevElement = null;
        }
        if (next == null) {
            lastNode = prev;
        } else {
            next.prevElement = prev;
            node.nextElement = null;
        }
        node.currentElemet = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element with such index doesn't exist!");
        }
    }

    private static class Node<T> {
        T currentElemet;
        Node<T> nextElement;
        Node<T> prevElement;

        public Node(Node<T> prevElement, T currentElemet, Node<T> nextElement) {
            this.prevElement = prevElement;
            this.currentElemet = currentElemet;
            this.nextElement = nextElement;
        }

    }
}
