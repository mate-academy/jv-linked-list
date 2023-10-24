package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            Node<T> currentNode = getNode(index);
            addBefore(value, currentNode);
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
        checkIndexExistence(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexExistence(index);
        Node<T> currentNode = getNode(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndexExistence(index);
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || object != null && object.equals(currentNode.value)) {
            unlink(currentNode);
            return true;
            }
            currentNode = currentNode.next;
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

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                node = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            int i = size - 1;
            while (current != null && i >= index) {
                if (i == index) {
                    return current;
                }
                i--;
                current = current.prev;
            }
        }
    }

    private Node<T> addNode(T value, int index, Node<T> previousNode, Node<T> nextNode) {
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (index == 0) {
            head = newNode;
        }
        if (index == size) {
            tail = newNode;
        }
        size++;
        return newNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (head == node) {
            head = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
        size--;
    }

    private void checkIndexExistence(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private class Node<T> {
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
