package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        }
        if (size > 0) {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> target = getNode(index);
        linkBefore(value, target);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            if (element != null) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        return returnNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = returnNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeDeleted = getNode(index);
        unlink(nodeDeleted);
        return nodeDeleted.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeDeleted = head;
        for (int i = 0; i < size; i++) {
            T nodeValue = nodeDeleted.value;
            if (nodeValue != null && nodeValue.equals(object) || nodeValue == object) {
                unlink(nodeDeleted);
                return true;
            }
            nodeDeleted = nodeDeleted.next;
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
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> pointer;
        if (index < size / 2) {
            pointer = head;
            for (int i = 0; i < size / 2; i++) {
                if (i == index) {
                    return pointer;
                }
                pointer = pointer.next;
            }
        } else {
            pointer = tail;
            for (int i = size - 1; i >= size / 2; i--) {
                if (i == index) {
                    return pointer;
                }
                pointer = pointer.prev;
            }
        }
        return pointer;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> nodeToLink = new Node<>(target.prev, value, target);
        if (target.prev == null) {
            head = nodeToLink;
        } else {
            target.prev.next = nodeToLink;
        }
        target.prev = nodeToLink;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + "passed to the method is invalid. Actual size is " + size);
        }
    }

    private void unlink(Node<T> nodeDeleted) {
        if (nodeDeleted == head) {
            head = nodeDeleted.next;
        } else if (nodeDeleted == tail) {
            tail = nodeDeleted.prev;
        } else {
            nodeDeleted.prev.next = nodeDeleted.next;
            nodeDeleted.next.prev = nodeDeleted.prev;
        }
        size--;
    }

    private Node<T> returnNode(int index) {
        return getNode(index);
    }
}
