package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(value);
        if (oldNode.prev == null) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (oldNode.next == null) {
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev = newNode;
            oldNode.prev.next = newNode;
        } else {
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
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

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T result = getNode(index).value;
        unlink(getNode(index));
        return result;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || object != null && object.equals(node.value)) {
                unlink(node);
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
        return (size == 0);
    }

    private static class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> pointer;
        if (index <= size / 2) {
            pointer = head;
            for (int i = 0; i < size / 2; i++) {
                if (i == index) {
                    return pointer;
                } else {
                    pointer = pointer.next;
                }
            }
        } else {
            pointer = tail;
            for (int i = size - 1; i >= size / 2; i--) {
                if (i == index) {
                    return pointer;
                } else {
                    pointer = pointer.prev;
                }
            }
            return pointer;
        }
        return pointer;
    }

    private void unlink(Node node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index exception: index "
                    + index + " for size " + size);
        }
    }
}
