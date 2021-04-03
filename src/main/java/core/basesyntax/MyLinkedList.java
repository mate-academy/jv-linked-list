package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public boolean add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isIndexExist(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        isIndexExist(index);
        Node<T> currentIndex = node(index);
        T oldVal = currentIndex.element;
        currentIndex.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        isIndexExist(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> newNode = head; newNode != null; newNode = newNode.last) {
            if (newNode.element == object
                    || (newNode.element != null && newNode.element.equals(object))) {
                unlink(newNode);
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

    private void isIndexExist(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element can't be found!");
        }
    }

    private Node<T> node(int index) {
        Node<T> newNodeHead = head;
        Node<T> newNodeTail = tail;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                newNodeHead = newNodeHead.last;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                newNodeTail = newNodeTail.first;
            }
        }
        return index < (size >> 1) ? newNodeHead : newNodeTail;
    }

    private void linkBefore(T item, Node<T> current) {
        Node<T> prev = current.first;
        Node<T> newNode = new Node<>(prev, item, current);
        current.first = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.last = newNode;
        }
        size++;
    }

    private T unlink(Node<T> nodePosition) {
        T element = nodePosition.element;
        Node<T> next = nodePosition.last;
        Node<T> prev = nodePosition.first;
        if (prev == null) {
            head = next;
        } else {
            prev.last = next;
            nodePosition.first = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.first = prev;
            nodePosition.last = null;
        }
        nodePosition.element = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T element;
        private Node<T> first;
        private Node<T> last;

        private Node(Node<T> first, T element, Node<T> last) {
            this.element = element;
            this.first = first;
            this.last = last;
        }
    }
}
