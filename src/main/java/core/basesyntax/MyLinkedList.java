package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (tail != null) {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        } else {
            head = tail = new Node<>(null, value, null);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (isEmpty()) {
            add(value);
            return;
        }
        if (index == size) {
            add(value);
        } else {
            linkBeforeNode(value, findNode(index));
        }
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlinkNode(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (object == current.value || object != null && object.equals(current.value)) {
                unlinkNode(current);
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

    private Node<T> findNode(int index) {
        Node<T> current;
        if (index < size >> 1) {
            current = head;
            for (int i = 0; i != index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i != index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index of " + index
                    + " for size " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index of " + index
                    + " for size " + size + " while adding");
        }
    }

    private void linkBeforeNode(T value, Node<T> nextNode) {
        Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
        if (nextNode == head) {
            head = newNode;
        } else {
            nextNode.prev.next = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    private T unlinkNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        size--;
        T value = node.value;
        node.value = null;
        return value;
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
}
