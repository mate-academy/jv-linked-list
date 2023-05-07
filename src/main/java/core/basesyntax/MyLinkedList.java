package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

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

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index of element: " + index
                    + "Size of the list: " + size);
        }
    }

    private void checkRangeAdd(int index) {
        if (index < 0 || index > size + 1) {
            throw new IndexOutOfBoundsException("Index of elements: " + index
                    + " Size of the list: " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkRange(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void addTop(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        if (first == null) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        first = newNode;
        size++;
    }

    private void addBottom(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    private void addMiddle(T value, int index) {
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    private boolean removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
        return true;
    }

    @Override
    public void add(T value) {
        addBottom(value);
    }

    @Override
    public void add(T value, int index) {
        checkRangeAdd(index);

        if (index == 0) {
            addTop(value);
        } else if (index == size) {
            addBottom(value);
        } else {
            addMiddle(value, index);
        }
        ;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addBottom(value);
        }
    }

    @Override
    public T get(int index) {
        checkRange(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        Node<T> nodeAtIndex = getNode(index);
        T oldValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> toRemove = getNode(index);
        Node<T> nodeBefore = toRemove.prev;
        Node<T> nodeAfter = toRemove.next;
        if (nodeBefore == null) {
            first = nodeAfter;
        }
        if (nodeAfter == null) {
            last = nodeBefore;
        } else {
            nodeAfter.prev = nodeBefore;
        }
        size--;
        return toRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.value == null) {
                if (object == null) {
                    return removeNode(currentNode);
                }
            } else if (currentNode.value.equals(object)) {
                return removeNode(currentNode);
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override public boolean isEmpty() {
        return size == 0;
    }
}
