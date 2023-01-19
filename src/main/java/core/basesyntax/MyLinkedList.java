package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else {
            Node<T> beforeNode = getNode(index);
            linkBefore(beforeNode,value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            T searchObject = node.value;
            if (searchObject == object || (searchObject != null && searchObject.equals(object))) {
                unlink(node);
                return true;
            }
            node = node.next;
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
        boolean isPositionIndex = index >= 0 && index < size;
        if (!isPositionIndex) {
            throw new IndexOutOfBoundsException("Passed index: " + index + " is invalid");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> pointer;
        if (index < size / 2) {
            pointer = head;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
            return pointer;
        } else {
            pointer = tail;
            for (int i = size - 1; i > index; i--) {
                pointer = pointer.prev;
            }
            return pointer;
        }
    }

    private void linkBefore(Node<T> targetNode, T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (targetNode.prev == null) {
            head = newNode;
        } else {
            targetNode.prev.next = newNode;
        }
        newNode.prev = targetNode.prev;
        targetNode.prev = newNode;
        newNode.next = targetNode;
        size++;
    }

    private T unlink(Node<T> nodeToUnlink) {
        T unlinkedValue = nodeToUnlink.value;
        if (size == 1) {
            head = null;
            tail = head;
            size--;
            return unlinkedValue;
        }
        if (nodeToUnlink == head) {
            head = nodeToUnlink.next;
            nodeToUnlink.prev = null;
        } else if (nodeToUnlink == tail) {
            tail = nodeToUnlink.prev;
            nodeToUnlink.next = null;
        } else {
            nodeToUnlink.prev.next = nodeToUnlink.next;
            nodeToUnlink.next.prev = nodeToUnlink.prev;
        }
        size--;
        return unlinkedValue;
    }
}
