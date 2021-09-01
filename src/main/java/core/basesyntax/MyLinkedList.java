package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;
    private Node<T> newNode;
    private Node<T> temporaryNode;

    private static class Node<T> {
        protected T element;
        protected Node<T> prev;
        protected Node<T> next;

        protected Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, null, null);
            last = new Node<>(null, null, null);
            temporaryNode = new Node<>(first, value, last);
            first = temporaryNode;
            last = temporaryNode;
            size++;
            return;
        }
        newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexIfInBound(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return indexToNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        temporaryNode = indexToNode(index);
        T oldRecord = temporaryNode.element;
        temporaryNode.element = value;
        return oldRecord;
    }

    @Override
    public T remove(int index) {
        temporaryNode = indexToNode(index);
        unLink(temporaryNode, index);
        return temporaryNode.element;
    }

    @Override
    public boolean remove(T object) {
        temporaryNode = first;
        for (int i = 0; i < size; i++) {
            if (temporaryNode.element == object || (temporaryNode.element != null
                    && temporaryNode.element.equals(object))) {
                remove(i);
                return true;
            }
            temporaryNode = temporaryNode.next;
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

    private void checkIndexIfInBound(T value, int index) {
        if (index >= 0 && index < size) {
            temporaryNode = indexToNode(index);
            newNode = new Node<>(temporaryNode.prev, value, temporaryNode);
            size++;
            if (index == 0) {
                first = newNode;
                return;
            }
            temporaryNode.prev.next = newNode;
            temporaryNode.prev = newNode;
            return;
        }
        throw new IndexOutOfBoundsException("It is not valid index!");
    }

    private void unLink(Node<T> node, int index) {

        if (index == 0) {
            first = node.next;
            first.prev = null;
            size--;
            return;
        }
        if (index == size - 1) {
            last = node.prev;
            last.next = null;
            size--;
            return;
        }
        (node.prev).next = node.next;
        (node.next).prev = node.prev;
        size--;
    }

    private Node<T> indexToNode(int index) {
        if (index >= (size / 2) && index < size) {
            temporaryNode = last;
            for (int i = size - 1; i > index; i--) {
                temporaryNode = temporaryNode.prev;
            }
            return temporaryNode;
        }
        if (index < (size / 2) && index >= 0) {
            temporaryNode = first;
            for (int i = 0; i < index; i++) {
                temporaryNode = temporaryNode.next;
            }
            return temporaryNode;
        }
        throw new IndexOutOfBoundsException("Index invalid!");
    }
}
