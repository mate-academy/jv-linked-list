package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        linkTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index < size && index >= 0) {
            linkNode(value, findNodeByIndex(index));
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
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T currentNodeValue = currentNode.value;
        currentNode.value = value;
        return currentNodeValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return removeNode(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> iterationNode = head;
        for (int i = 0; i < size; i++) {
            if (object == iterationNode.value
                        || object != null && object.equals(iterationNode.value)) {
                removeNode(iterationNode);
                return true;
            }
            iterationNode = iterationNode.next;
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

    private T removeNode(Node<T> nodeToRemove) {
        Node<T> beforeNodeToRemove = nodeToRemove.previous;
        Node<T> afterNodeToRemove = nodeToRemove.next;
        if (afterNodeToRemove == null) {
            tail = beforeNodeToRemove;
            nodeToRemove.previous = null;
        } else if (beforeNodeToRemove == null) {
            head = afterNodeToRemove;
            nodeToRemove.next = null;
        } else {
            beforeNodeToRemove.next = afterNodeToRemove;
            afterNodeToRemove.previous = beforeNodeToRemove;
        }
        size--;
        return nodeToRemove.value;
    }

    private void linkTail(T elementToAdd) {
        Node<T> lastElement = tail;
        Node<T> nodeToAdd = new Node<>(lastElement, elementToAdd,null);
        tail = nodeToAdd;
        if (lastElement == null) {
            head = nodeToAdd;
        } else {
            lastElement.next = nodeToAdd;
        }
        size++;
    }

    private void linkNode(T valueToAdd, Node<T> currentNode) {
        Node<T> previousNode = currentNode.previous;
        Node<T> nodeToAdd = new Node<>(previousNode, valueToAdd, currentNode);
        currentNode.previous = nodeToAdd;
        if (previousNode == null) {
            head = nodeToAdd;
        } else {
            previousNode.next = nodeToAdd;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> nodeToFind;
        if (index < size / 2) {
            nodeToFind = head;
            for (int i = 0; i < index; i++) {
                nodeToFind = nodeToFind.next;
            }
        } else {
            nodeToFind = tail;
            for (int i = size - 1; i > index; i--) {
                nodeToFind = nodeToFind.previous;
            }
        }
        return nodeToFind;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
