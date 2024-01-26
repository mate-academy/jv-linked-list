package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> tailNode = tail;
        Node<T> nodeToAdd = new Node<>(value, tail, null);
        tail = nodeToAdd;
        if (tailNode == null) {
            head = nodeToAdd;
        } else {
            tailNode.next = nodeToAdd;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAddOperation(index);
        Node<T> foundNode = findNodeByIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> previousNode = foundNode.prev;
            Node<T> nodeToAdd = new Node<>(value, previousNode, foundNode);
            if (previousNode != null) {
                foundNode.prev.next = nodeToAdd;
                foundNode.prev = nodeToAdd;
            } else {
                head = nodeToAdd;
                nodeToAdd.next = foundNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheckForGetSetRemoveOperations(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheckForGetSetRemoveOperations(index);
        Node<T> foundNode = findNodeByIndex(index);
        T oldItem = foundNode.item;
        foundNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        indexCheckForGetSetRemoveOperations(index);
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlinkNode(nodeToRemove);
        size--;
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> iteratorNode = head;
        for (int i = 0; i < size; i++) {
            if (iteratorNode.item == object
                    || (iteratorNode.item != null && iteratorNode.item.equals(object))) {
                unlinkNode(iteratorNode);
                size--;
                return true;
            }
            iteratorNode = iteratorNode.next;
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private void indexCheckForAddOperation(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void indexCheckForGetSetRemoveOperations(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
            return;
        }
        if (node.prev == null) {
            head = node.next;
            head.prev = null;
            return;
        }
        if (node.next == null) {
            tail = node.prev;
            tail.next = null;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> firstNode = head;
            for (int i = 0; i < index; i++) {
                firstNode = firstNode.next;
            }
            return firstNode;
        } else {
            Node<T> lastNode = tail;
            for (int i = size - 1; i > index; i--) {
                lastNode = lastNode.prev;
            }
            return lastNode;
        }
    }
}
