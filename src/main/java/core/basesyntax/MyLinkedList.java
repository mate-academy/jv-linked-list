package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> headElement;
    private Node<T> tailElement;
    private int size;

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

    @Override
    public boolean add(T value) {
        setTailElement(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        chekIndex(index, size);
        if (index == size) {
            setTailElement(value);
        } else {
            getElementBefore(value, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        chekIndex(index, size - 1);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        chekIndex(index, size - 1);
        Node<T> node = getNode(index);
        T oldNodeValue = node.value;
        node.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        chekIndex(index, size - 1);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = headElement;
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(object) || node.value == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void chekIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void setTailElement(T element) {
        final Node<T> lastNode = tailElement;
        final Node<T> newNode = new Node<>(lastNode, element, null);
        tailElement = newNode;
        if (lastNode == null) {
            headElement = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> neededNode;
        if (index < size / 2) {
            neededNode = headElement;
            for (int i = 0; i < index; i++) {
                neededNode = neededNode.next;
            }
        } else {
            neededNode = tailElement;
            for (int i = size - 1; i > index; i--) {
                neededNode = neededNode.prev;
            }
        }
        return neededNode;
    }

    private void getElementBefore(T value, Node<T> element) {
        Node<T> prevElement = element.prev;
        Node<T> newElement = new Node<>(prevElement, value, element);
        element.prev = newElement;
        if (prevElement == null) {
            headElement = newElement;
        } else {
            prevElement.next = newElement;
        }
        size++;
    }

    private T unlink(Node<T> unlinkNode) {
        Node<T> prev = unlinkNode.prev;
        Node<T> next = unlinkNode.next;

        if (prev == null) {
            headElement = next;
        } else {
            prev.next = next;
            unlinkNode.prev = null;
        }

        if (next == null) {
            tailElement = prev;
        } else {
            next.prev = prev;
            unlinkNode.next = null;
        }

        T value = unlinkNode.value;
        unlinkNode.value = null;
        size--;
        return value;
    }
}
