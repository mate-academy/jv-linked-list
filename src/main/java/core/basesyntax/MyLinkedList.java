package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (last == null) {
            newNode = new Node<>(null, value, null);
            first = newNode;
        } else {
            newNode = new Node<>(last, value, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0 && first != null) {
            Node<T> newNode = new Node<>(null, value, null);
            newNode.prev = null;
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            checkIndex(index);
            Node<T> targetNode = findNodeByIndex(index);
            Node<T> prevNode = targetNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, targetNode);
            prevNode.next = newNode;
            targetNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T entry : list) {
            add(entry);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToChange = findNodeByIndex(index);
        T oldValue = nodeToChange.value;
        nodeToChange.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if ((currentNode.value == null && object == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            } else {
                currentNode = currentNode.next;
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
        return (size == 0);
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < (size >> 1)) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
            size--;
            return;
        } else if (node.prev == null) {
            first = node.next;
            first.prev = null;
            size--;
            return;
        } else if (node.next == null) {
            last = node.prev;
            last.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
