package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else if (currentNode == first && size == 0) {
            first = newNode;
            last = newNode;
            size++;
        } else if (currentNode == first) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> neededNode = first;
        for (int i = 0; i < index; i++) {
            neededNode = neededNode.next;
        }
        return neededNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = findNodeByIndex(index);
        T oldValue = setNode.value;
        setNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = findNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> redundantNode = findNodeByValue(object);
        if (redundantNode == null) {
            return false;
        }
        unlink(redundantNode);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + " is not exist");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private Node<T> findNodeByValue(T object) {
        Node<T> deletedNode = first;
        while (deletedNode != null) {
            if (deletedNode.value == object
                    || deletedNode != null && deletedNode.value.equals(object)) {
                return deletedNode;
            }
            deletedNode = deletedNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (size == 1) {
            first = null;
            last = null;
            return;
        }
        if (node == first) {
            first = next;
            next.prev = null;
            return;
        }
        if (node == last) {
            prev.next = null;
            return;
        }
        prev.next = node.next;
        next.prev = node.prev;
    }

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
}
