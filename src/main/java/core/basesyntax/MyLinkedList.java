package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUNDS_EXCEPTION = "Index out of bounds!";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
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
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            indexCheck(index);
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            currentNode.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode = getNodeByIndex(index);
        T previousNode = currentNode.value;
        currentNode.value = value;
        return previousNode;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            unlink(node);
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

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_EXCEPTION);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> bufferNode = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                bufferNode = bufferNode.next;
            }
        } else {
            for (int j = size; j > size - index; j--) {
                bufferNode = bufferNode.next;
            }
        }
        return bufferNode;
    }

    private Node<T> getNode(T value) {
        for (int i = 0; i < size; i++) {
            if ((getNodeByIndex(i).value == value)
                    || (value != null && value.equals(getNodeByIndex(i).value))) {
                return getNodeByIndex(i);
            }
        }
        return null;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.value;
    }
}
