package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> headNode;
    private Node<T> tailNode;

    MyLinkedList() {
        headNode = new Node<>(null, null, null);
        tailNode = new Node<>(null, headNode, null);
        headNode.next = tailNode;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        insertNode(newNode, tailNode);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value, null, null);
        insertNode(newNode, getNodeByIndex(index));
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexForRestFunctions(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForRestFunctions(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForRestFunctions(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev;
        size--;
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> nodeToRemove = headNode.next;
        for (int i = 0; i < size; i++) {
            if (nodeToRemove.element == t || (nodeToRemove.element != null
                    && nodeToRemove.element.equals(t))) {
                nodeToRemove.prev.next = nodeToRemove.next;
                nodeToRemove.next.prev = nodeToRemove.prev;
                size--;
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private void insertNode(Node<T> newNode, Node<T> currentNode) {
        currentNode.prev.next = newNode; //  <prev> -> <new>
        newNode.prev = currentNode.prev; // <prev> <-> <new>
        newNode.next = currentNode; // <prev> <-> <new> -> <cur>
        currentNode.prev = newNode; // <prev> <-> <new> <-> <cur>
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (size == 0) {
            return tailNode;
        }
        if (index <= size >> 1) {
            return getDistantNext(index, headNode);
        } else {
            return getDistantPrev(size - index, tailNode);
        }
    }

    private Node<T> getDistantNext(int index, Node<T> node) {
        if (index <= 0) {
            return node.next;
        }
        index--;
        return getDistantNext(index, node.next);
    }

    private Node<T> getDistantPrev(int index, Node<T> node) {
        index--;
        if (index <= 0) {
            return node.prev;
        }
        return getDistantPrev(index, node.prev);
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("List has no such index");
        }
    }

    private void checkIndexForRestFunctions(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("List has no such index");
        }
    }

    private static class Node<T> {
        public T element;
        public Node<T> prev;
        public Node<T> next;

        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
