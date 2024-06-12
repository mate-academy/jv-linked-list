package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        insertLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexNotInclude(index);
        if (index == size) {
            insertLast(value);
        } else {
            insertBefore(findNodeByIndex(index), value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexInclude(index);
        Node<T> currentNode = findNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInclude(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInclude(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.value == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.value)) {
                    unlink(currentNode);
                    return true;
                }
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndexNotInclude(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void checkIndexInclude(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            while (index > 0) {
                currentNode = currentNode.next;
                index--;
            }
        } else {
            currentNode = last;
            while (index < size - 1) {
                currentNode = currentNode.prev;
                index++;
            }
        }
        return currentNode;
    }

    private void insertLast(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private void insertBefore(Node<T> nextNode, T value) {
        Node<T> previousNode = nextNode.prev;
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        nextNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }

        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
        }

        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
        }

        size--;
        return node.value;
    }
}
