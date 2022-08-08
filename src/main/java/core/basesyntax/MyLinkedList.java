package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        } else {
            Node<T> preceding = getNodeByIndex(index - 1);
            newNode.next = preceding.next;
            newNode.next.previous = newNode;
            newNode.previous = preceding;
            preceding.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue;
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T removedValue;
        Node<T> removedNode;
        if (index == 0) {
            removedValue = first.value;
            removedNode = first;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> preceding = getNodeByIndex(index - 1);
            removedNode = preceding.next;
            removedValue = preceding.next.value;
            preceding.next = preceding.next.next;
            if (index == size - 1) {
                last = preceding;
            } else {
                preceding.next.previous = preceding;
            }
        }
        unlink(removedNode);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        int nodeIndex = getIndexOfNode(object);
        if (nodeIndex == -1) {
            return false;
        } else {
            remove(nodeIndex);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private int getIndexOfNode(T value) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(value) || current.value == value) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        node.next = null;
        node.previous = null;
    }

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
