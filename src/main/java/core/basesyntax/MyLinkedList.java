package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> last;
    private Node<T> first;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(value, last, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, first);
            first.previous = newNode;
            first = newNode;
        } else {
            checkIndex(index);
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, nodeByIndex.previous, nodeByIndex);
            nodeByIndex.previous.next = newNode;
            nodeByIndex.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        T previousValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        for (node = first; node != null; node = node.next) {
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
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            node.next = null;
        }
        T element = node.value;
        node.value = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Sorry, bad index entered" + index);
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> previous;

        Node(E value, Node<E> previous, Node<E> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
