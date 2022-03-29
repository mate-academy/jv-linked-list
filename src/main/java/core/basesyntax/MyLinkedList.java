package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(value, null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            insert(value, index);
        }

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
        Node<T> foundedNode = findNode(index);
        return foundedNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexPositionNode = findNode(index);
        T oldContent = indexPositionNode.value;
        indexPositionNode.value = value;
        return oldContent;
    }

    @Override
    public T remove(int index) {

        checkIndex(index);
        return unLink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeForRemoving = findNode(object);
        if (nodeForRemoving == null) {
            return false;
        }
        unLink(nodeForRemoving);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private T unLink(Node<T> node) {
        T removedNodeContent = node.value;
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.next;
            head.previous = null;
        } else if (node == tail) {
            tail = tail.previous;
            tail.next = null;
        } else {
            Node<T> next = node.next;
            Node<T> previous = node.previous;
            next.previous = previous;
            previous.next = next;
        }
        size--;
        return removedNodeContent;
    }

    private Node<T> findNode(T content) {
        Node<T> iterationNode = head;
        while (iterationNode != null) {
            if (iterationNode.value != null && iterationNode.value.equals(content)
                    || (iterationNode.value == content)) {
                return iterationNode;
            }
            iterationNode = iterationNode.next;
        }
        return null;
    }

    private Node<T> findNode(int index) {
        return (size - index) > index ? searchFromHead(index) : searchFromTail(index);
    }

    private Node<T> searchFromHead(int index) {
        Node<T> neededNode = head;
        for (int i = 0; i < index; i++) {
            neededNode = neededNode.next;
        }
        return neededNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> neededNode = tail;
        for (int i = size - 1; index < i; i--) {
            neededNode = neededNode.previous;
        }
        return neededNode;
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
    }

    private void insert(T value, int index) {
        Node<T> indexPositionNode = findNode(index);
        Node<T> previousIndexPositionNode = indexPositionNode.previous;
        Node<T> newNode = new Node<>(value, indexPositionNode, previousIndexPositionNode);
        indexPositionNode.previous = newNode;
        if (previousIndexPositionNode == null) {
            head = newNode;
        } else {
            previousIndexPositionNode.next = newNode;
        }
        size++;
    }
}
