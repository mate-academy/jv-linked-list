package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> nextNode = new Node(tail, value, null);
            tail.next = nextNode;
            tail = nextNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> nextNode = new Node<>(null, value, head);
            head.prev = nextNode;
            head = nextNode;
        } else {
            checkIndex(index);
            Node<T> targetNode = getNode(index);
            Node<T> newnode = new Node<>(targetNode.prev, value, targetNode);
            targetNode.prev.next = newnode;
            targetNode.prev = newnode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index isn't valid.");
        }
    }

    Node<T> getNode(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = getNode(index);
        T returnValue = targetNode.value;
        targetNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> targetNode = getNode(index);
        return unlink(targetNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        if (object == null) {
            while (node != null) {
                if (node.value == null) {
                    unlink(node);
                    return true;
                }
                node = node.next;
            }
        } else {
            while (node != null) {
                if (node.value.equals(object)) {
                    unlink(node);
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    T unlink(Node<T> variable) {
        if (variable.prev == null) {
            head = variable.next;
        } else if (variable.next == null) {
            tail = variable.prev;
            variable.prev.next = null;
        } else {
            variable.prev.next = variable.next;
            variable.next.prev = variable.prev;
        }
        size--;
        return variable.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
