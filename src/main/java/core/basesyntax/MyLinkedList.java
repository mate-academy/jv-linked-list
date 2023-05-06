package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddByIndex(index);
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index - 1);
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int index = 0; index < list.size(); index++) {
            add(list.get(index));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T element = currentNode.element;
        currentNode.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T element;
        if (index == 0) {
            element = first.element;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> currentNode = getNodeByIndex(index - 1);
            element = currentNode.next.element;
            currentNode.next = currentNode.next.next;
            if (index == size - 1) {
                last = currentNode;
            }
        }
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        int index = 0;
        while (currentNode.element != object) {
            currentNode = currentNode.next;
            index++;
            if (index == size) {
                return false;
            }
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndexForAddByIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index > size or index < 0");
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Input index " + index
                    + " for size " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
