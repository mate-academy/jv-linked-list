package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T items : list) {
            add(items);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T nodeValue = node.value;
        node.value = value;
        return nodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        unlink(target);
        return target.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = head;

        for (int i = 0; i <= size; i++) {
            if (target == null) {
                return false;
            }
            if (target.value == object || object != null && object.equals(target.value)) {
                unlink(target);
                return true;
            }
            target = target.next;
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + "is not exist for " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;

        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> needToRemoveNode) {
        if (needToRemoveNode == head) {
            head = needToRemoveNode.next;
            needToRemoveNode.prev = null;
        } else if (needToRemoveNode == tail) {
            tail = needToRemoveNode.prev;
            needToRemoveNode.next = null;
        } else {
            needToRemoveNode.prev.next = needToRemoveNode.next;
            needToRemoveNode.next.prev = needToRemoveNode.prev;
        }
        size--;
    }
}
