package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> nextNode;
        private Node<T> previousNode;

        public Node(Node<T> previousNode, T value, Node<T> nextNode) {
            this.nextNode = nextNode;
            this.value = value;
            this.previousNode = previousNode;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.nextNode = newNode;
            tail = newNode;

        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdd(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> insertingNode = new Node<>(null, value, head);
            head = insertingNode;
            size++;
        } else {
            Node previousNode = getNodeNyIndex(index - 1);
            Node<T> insertingNode = new Node<>(previousNode, value, previousNode.nextNode);
            Node afteradditingNode = previousNode.nextNode;
            previousNode.nextNode = insertingNode;
            afteradditingNode.previousNode = insertingNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int j = 0; j < list.size(); j++) {
            add(list.get(j));
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> currentNote = getNodeNyIndex(index);
        return currentNote.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode = getNodeNyIndex(index);
        T valueReplacing = currentNode.value;
        currentNode.value = value;
        return valueReplacing;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = getNodeNyIndex(index);
        return delete(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = new Node<>(null, null, head);
        for (int i = 0; i < size; i++) {
            currentNode = currentNode.nextNode;
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                delete(currentNode);
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

    private void indexCheck(int index) {
        if (size <= index || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index is out of bound");
        }
    }

    private void indexCheckForAdd(int index) {
        if (size < index || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index is out of bound");
        }
    }

    private Node getNodeNyIndex(int index) {
        Node currentNode = null;
        if (size / 2 - 1 < index) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previousNode;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.nextNode;
            }
        }
        return currentNode;
    }

    private T delete(Node<T> removedNode) {
        Node<T> prev = removedNode.previousNode;
        Node<T> next = removedNode.nextNode;
        if (prev == null) {
            head = next;
        } else {
            prev.nextNode = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previousNode = prev;
        }
        T oldValue = removedNode.value;
        size--;
        return oldValue;
    }

}

