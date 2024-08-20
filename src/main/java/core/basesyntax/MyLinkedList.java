package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value, null, null);
        if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> indexNode = findNodeByIndex(index);
            newNode.previous = indexNode.previous;
            newNode.next = indexNode;
            indexNode.previous.next = newNode;
            indexNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = findNodeByIndex(index);

        if (nodeToRemove == first) {
            first = first.next;
            if (first != null) {
                first.previous = null;
            }
        } else if (nodeToRemove == last) {
            last = last.previous;
            if (last != null) {
                last.next = null;
            }
        } else {
            nodeToRemove.previous.next = nodeToRemove.next;
            nodeToRemove.next.previous = nodeToRemove.previous;
        }

        size--;
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(currentNode.element, object)) {
                if (currentNode == first) {
                    first = first.next;
                    if (first != null) {
                        first.previous = null;
                    }
                } else if (currentNode == last) {
                    last = last.previous;
                    if (last != null) {
                        last.next = null;
                    }
                } else {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                size--;
                return true;
            }
            currentNode = currentNode.next;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> indexNode;

        if (index < (size / 2)) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.previous;
            }
        }

        return indexNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element, Node<T> next, Node<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
