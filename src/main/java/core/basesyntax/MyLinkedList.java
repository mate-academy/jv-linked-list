package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

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
        if (size > 0) {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> newNode = new Node<>(null,value, null);
            first = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexIfInBound(index);
        Node<T> temporaryNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(temporaryNode.prev, value, temporaryNode);
        size++;
        if (index > 0) {
            temporaryNode.prev.next = newNode;
            temporaryNode.prev = newNode;
            return;
        }
        first = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexIfInBound(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexIfInBound(index);
        Node<T> temporaryNode = findNodeByIndex(index);
        T oldRecord = temporaryNode.element;
        temporaryNode.element = value;
        return oldRecord;
    }

    @Override
    public T remove(int index) {
        checkIndexIfInBound(index);
        Node<T> temporaryNode = findNodeByIndex(index);
        unLink(temporaryNode);
        return temporaryNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temporaryNode = first;
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

    private void checkIndexIfInBound(int index) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException("It is not valid index!");
        }

    }

    private void unLink(Node<T> node) {
        Node<T> prev = node.prev;
        if (prev == null) {
            first = node.next;
        } else {
            prev.next = node.next;
        }
        Node<T> next = node.next;
        if (next == null) {
            last = node.prev;
        } else {
            next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index >= (size / 2) && index < size) {
            Node<T> temporaryNode = last;
            for (int i = size - 1; i > index; i--) {
                temporaryNode = temporaryNode.prev;
            }
            return temporaryNode;
        }
        if (index < (size / 2) && index >= 0) {
            Node<T> temporaryNode = first;
            for (int i = 0; i < index; i++) {
                temporaryNode = temporaryNode.next;
            }
            return temporaryNode;
        }
        throw new IndexOutOfBoundsException("Index invalid!");
    }
}
