package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> interimLast = last;
        last = new Node<>(value, null, interimLast);
        size++;
        if (interimLast == null) {
            first = last;
        } else {
            interimLast.nextNode = last;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getNode(index);
            Node<T> prevNode = node.prevNode;
            Node<T> newNode = new Node<>(value, node, prevNode);
            node.prevNode = newNode;
            if (prevNode == null) {
                first = newNode;
            } else {
                prevNode.nextNode = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T returnNode = node.element;
        Node<T> prevNode = node.prevNode;
        Node<T> nextNode = node.nextNode;
        if (nextNode == null && prevNode == null) {
            node.element = null;
            first = null;
            last = null;
            size--;
            return returnNode;
        }
        if (prevNode == null) {
            nextNode.prevNode = null;
            first = nextNode;
            size--;
            return returnNode;
        }
        if (nextNode == null) {
            prevNode.nextNode = null;
            last = prevNode;
            size--;
            return returnNode;
        }
        prevNode.nextNode = nextNode;
        nextNode.prevNode = prevNode;
        size--;
        return returnNode;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((t == null && null == node.element) || (t != null && t.equals(node.element))) {
                remove(i);
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
        return size == 0;
    }

    private Node<T> getNode(int index) {
        validIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.nextNode;
            }
        } else {
            node = last;
            for (int i = size - 1; i >= index; i--) {
                node = node.prevNode;
            }
        }
        return node;
    }

    private void validIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T element;
        Node<T> nextNode;
        Node<T> prevNode;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.nextNode = next;
            this.prevNode = prev;
        }
    }
}
