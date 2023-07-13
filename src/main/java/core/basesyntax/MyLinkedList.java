package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EMPTY_SIZE = 0;
    private Node<T> first;
    private Node<T> last;
    private int size;

    static class Node<T> {
        private T value;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T value, MyLinkedList.Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkForIndexEqualSize(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> actual = getNode(index);
            Node<T> newNode = new Node<>(null, value, actual);
            actual.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> actual = getNode(index);
            Node<T> newNode = new Node<>(actual.prev, value, actual.next);
            newNode.prev = actual.prev;
            actual.prev.next = newNode;
            actual.prev = newNode;
            newNode.next = actual;
            size++;
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
        checkForIndexBound(index);
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkForIndexBound(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkForIndexBound(index);
        Node<T> nodeForRemove = getNode(index);
        T value = (T) nodeForRemove.value;
        if (nodeForRemove == first) {
            first = nodeForRemove.next;
        } else if (nodeForRemove == last) {
            last = nodeForRemove.prev;
        } else {
            nodeForRemove.next.prev = nodeForRemove.prev;
            nodeForRemove.prev.next = nodeForRemove.next;
        }
        size--;
        return (T) value;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        if (object == null) {
            for (Node<T> current = first; current != null; current = current.next) {
                if (current.value == null) {
                    remove(index);
                    return true;
                }
                index++;
            }
        } else {
            for (Node<T> current = first; current != null; current = current.next) {
                if ((current.value != null) && (current.value.equals(object))) {
                    remove(index);
                    return true;
                } else {
                    index++;
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
        return size == EMPTY_SIZE;
    }

    private Node<T> getNode(int index) {
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private void checkForIndexBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);
        }
    }

    private void checkForIndexEqualSize(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);
        }
    }
}
