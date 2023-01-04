package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        putNode(value, size == 0 ? "first" : "top");
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index > 0 && index < size) {
            putNodeMiddle(value, getNodeByIndex(index));
            return;
        }
        putNode(value, index == 0 ? size == 0 ? "first" : "bottom" : "top");
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new NoSuchElementException("You can't add any data from empty list");
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return getNodeByIndex(index).getElement();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> node = getNodeByIndex(index);
        T element = node.getElement();
        node.setElement(value);
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> node = getNodeByIndex(index);
        removeNode(node);
        return node.getElement();
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new NoSuchElementException("This list is empty! "
                    + "You can't remove anything from it!");
        }
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (object == currentNode.getElement()
                    || (object != null && object.equals(currentNode.getElement()))) {
                removeNode(currentNode);
                return true;
            }
            currentNode = currentNode.getNext();
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

    private void checkIndex(int index, int range) {
        if (index < 0 || index > range) {
            throw new IndexOutOfBoundsException("Index [" + index
                    + "] is out of available range. Available range is [0 - " + range + "]");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        int nodeIndex;
        if (index > size >> 1) {
            node = lastNode;
            nodeIndex = size - 1;
            while (nodeIndex > index) {
                node = node.getPrev();
                nodeIndex--;
            }
            return node;
        }
        node = firstNode;
        nodeIndex = 0;
        while (nodeIndex < index) {
            node = node.getNext();
            nodeIndex++;
        }
        return node;
    }

    private void putNodeMiddle(T value, Node<T> operationNode) {
        Node<T> newNode = new Node<>(value, operationNode.getPrev(), operationNode);
        operationNode.getPrev().setNext(newNode);
        operationNode.setPrev(newNode);
        size++;
    }

    private void putNode(T value, String position) {
        Node<T> newNode;
        switch (position) {
            case "top":
                newNode = new Node<>(value, lastNode, null);
                lastNode.setNext(newNode);
                lastNode = newNode;
                break;
            case "bottom":
                newNode = new Node<>(value, null, firstNode);
                firstNode.setPrev(newNode);
                firstNode = newNode;
                break;
            default:
                newNode = new Node<>(value, null, null);
                firstNode = newNode;
                lastNode = newNode;
        }
        size++;
    }

    private void removeNode(Node<T> node) {
        if (!node.equals(firstNode) && !node.equals(lastNode)) {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
        if (node.equals(firstNode) && size > 1) {
            node.getNext().setPrev(null);
            firstNode = node.getNext();
        }
        if (node.equals(lastNode) && size > 1) {
            node.getPrev().setNext(null);
            lastNode = node.getPrev();
        }
        if (firstNode.equals(lastNode)) {
            firstNode = null;
            lastNode = null;
        }
        size--;
    }

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }
    }
}
