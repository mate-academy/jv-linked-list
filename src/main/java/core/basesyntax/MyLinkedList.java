package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> lastBeforeAdd = last;
        Node<T> newNode = new Node(lastBeforeAdd, value, null);
        last = newNode;
        if (size == 0) {
            first = newNode;
        } else {
            lastBeforeAdd.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkRightIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> prevOfReplacedNode = getNode(index).prev;
            Node<T> newNode = new Node(prevOfReplacedNode, value, getNode(index));
            getNode(index).prev = newNode;
            if (size == 0) {
                first = newNode;
            } else {
                prevOfReplacedNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> elements) {
        for (T t : elements) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkExistsElement(index);
        return getNode(index).element;
    }

    @Override
    public void set(T value, int index) {
        checkExistsElement(index);
        Node<T> replacedNode = getNode(index);
        replacedNode.element = value;
    }

    @Override
    public T remove(int index) {
        checkExistsElement(index);
        Node<T> removedNode = getNode(index);
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        T element = removedNode.element;
        removedNode = null;
        size--;
        return element;
    }

    @Override
    public T remove(T t) {
        int counter = 0;
        for (Node desired = first; desired != null; desired = desired.next) {
            if (desired.element == null || t.equals(desired.element)) {
                return remove(counter);
            }
            counter++;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkExistsElement(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkRightIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> desired;
        if (index < size / 2) {
            desired = first;
            for (int i = 0; i < index; ++i) {
                desired = desired.next;
            }
            return desired;
        } else {
            desired = last;
            for (int i = size - 1; i > index; i--) {
                desired = desired.prev;
            }
            return desired;
        }
    }
}
