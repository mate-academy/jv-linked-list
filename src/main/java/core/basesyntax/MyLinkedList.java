package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        Node<T> prevNode;
        Node<T> nextNode;
        if (index == 0) {
            prevNode = null;
            nextNode = first;
        } else {
            prevNode = getNode(index - 1);
            nextNode = prevNode.nextNode;
        }
        Node<T> newNode = new Node<>(value, prevNode, nextNode);
        if (nextNode != null) {
            nextNode.prevNode = newNode;
        } else {
            last = newNode;
        }
        if (prevNode != null) {
            prevNode.nextNode = newNode;
        } else {
            first = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T prevValue = null;
        if (node == null) {
            add(value, index);
        } else {
            prevValue = node.value;
            node.value = value;
        }
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null) {
            if (objectsAreEqual(node.value, object)) {
                unlink(node);
                return true;
            }
            node = node.nextNode;
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

    private static class Node<E> {
        private Node<E> nextNode;
        private Node<E> prevNode;
        private E value;

        Node(E value, Node<E> prevNode, Node<E> nextNode) {
            this.value = value;
            this.prevNode = prevNode;
            this.nextNode = nextNode;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prevNode != null) {
            node.prevNode.nextNode = node.nextNode;
        } else {
            first = node.nextNode;
        }
        if (node.nextNode != null) {
            node.nextNode.prevNode = node.prevNode;
        } else {
            last = node.prevNode;
        }
        size--;
        node.prevNode = null;
        node.nextNode = null;
    }

    private void checkOutOfBoundsException(int index) {
        if ((index >= size) || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " outside of array list size (" + size + ")");
        }
    }

    private Node<T> getNode(int index) {
        checkOutOfBoundsException(index);
        Node<T> node;
        if (index <= size / 2) {
            int currentIndex = 0;
            node = first;
            while (currentIndex < index) {
                node = node.nextNode;
                currentIndex++;
            }
        } else {
            int currentIndex = size - 1;
            node = last;
            while (currentIndex > index) {
                node = node.prevNode;
                currentIndex--;
            }
        }

        return node;
    }

    private static boolean objectsAreEqual(Object obj1, Object obj2) {
        return obj1 == obj2 || ((obj1 == null || obj2 == null) ? false : obj1.equals(obj2));
    }
}
