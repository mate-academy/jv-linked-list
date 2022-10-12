package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INDEX_NOT_FOUND = -1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        isValidIndex(index);
        if (index == 0) {
            linkHead(value);
        } else {
            linkMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        isValidIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToChangeValue = getNode(index);
        T oldValue = nodeToChangeValue.value;
        nodeToChangeValue.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        T removedValue = nodeToRemove.value;
        if (nodeToRemove != head && nodeToRemove != tail) {
            Node<T> left = nodeToRemove.prev;
            Node<T> right = nodeToRemove.next;
            left.next = right;
            right.prev = left;
            size--;
        } else if (nodeToRemove == tail) {
            unlinkTail();
        } else {
            unlinkHead();
        }
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        final int valueIndex = indexOf(object);
        if (valueIndex != INDEX_NOT_FOUND) {
            remove(valueIndex);
            return true;
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

    private int indexOf(Object obj) {
        int i = 0;
        for (Node<T> x = head; x != null; x = x.next, i++) {
            if (obj == x.value || (obj != null && obj.equals(x.value))) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    private void linkTail(T value) {
        final Node<T> tailNode = tail;
        final Node<T> newNode = new Node<>(tailNode, value, null);
        tail = newNode;
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        size++;
    }

    private void unlinkTail() {
        Node<T> prevTail = tail.prev;
        if (prevTail == null) {
            head = null;
        } else {
            prevTail.next = null;
        }
        tail = prevTail;
        size--;
    }

    private void linkHead(T value) {
        final Node<T> headNode = head;
        final Node<T> newNode = new Node<>(null, value, headNode);
        head = newNode;
        if (headNode == null) {
            tail = newNode;
        } else {
            headNode.prev = newNode;
        }
        size++;
    }

    private void unlinkHead() {
        Node<T> nextHead = head.next;
        if (nextHead == null) {
            tail = null;
        } else {
            nextHead.prev = null;
            head = nextHead;
        }
        size--;
    }

    private void linkMiddle(T value, int index) {
        Node<T> oldNode = getNode(index);
        Node<T> prevNode = oldNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, oldNode);
        oldNode.prev = newNode;
        prevNode.next = newNode;
        size++;
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index:" + index);
        }
    }

    private Node<T> getNode(int index) {
        isValidIndex(index);
        Node<T> foundNode;
        if (index < (size >> 1)) {
            foundNode = head;
            for (int i = 0; i < index; i++) {
                foundNode = foundNode.next;
            }
        } else {
            foundNode = tail;
            for (int i = size - 1; i > index; i--) {
                foundNode = foundNode.prev;
            }
        }
        return foundNode;
    }
}
