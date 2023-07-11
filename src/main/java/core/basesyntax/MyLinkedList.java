package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    private int listSize;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node(value, null, null);
            tail = head;
        } else {
            Node newNode = new Node(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index == listSize) {
            add(value);
            return;
        }
        checkIndexIsValid(index);
        if (index == 0) {
            head = new Node(value, null, head);
            head.next.prev = head;
        } else {
            Node newNode = new Node(value, getNodeByIndex(index).prev, getNodeByIndex(index));
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
        }
        listSize++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T el : list) {
            add(el);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node nodeToSet = getNodeByIndex(index);
        T old = nodeToSet.value;
        nodeToSet.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    private void checkIndexIsValid(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cant be < 0; ");
        }
        if (index >= listSize) {
            throw new IndexOutOfBoundsException("index cant be >= LinkedList size; ");
        }
    }

    private void unlink(Node nodeToUnlink) {
        if (listSize == 1) {
            head = null;
            tail = null;
        } else if (nodeToUnlink == head) {
            head = head.next;
            head.prev = null;
        } else if (nodeToUnlink == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            nodeToUnlink.prev.next = nodeToUnlink.next;
            nodeToUnlink.next.prev = nodeToUnlink.prev;
        }
        listSize--;
    }

    private Node getNodeByIndex(int index) {
        checkIndexIsValid(index);
        if (index <= listSize / 2) {
            Node currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node currentNode = tail;
            for (int i = listSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }
}
