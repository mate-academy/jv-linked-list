package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUNDS = "Index [%d] is out of bounds for list of size [%d]";
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLastElement(value);
    }

    @Override
    public void add(T value, int index) {
        checkingIndex(index, size + 1);
        if (index == size) {
            addLastElement(value);
        } else {
            addElementByIndex(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkingIndex(index, size);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index, size);
        Node<T> nodeOnIndex = getNodeByIndex(index);
        T oldValue = nodeOnIndex.element;
        nodeOnIndex.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index, size);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || get(i) != null && get(i).equals(object)) {
                unlink(getNodeByIndex(i));
                return true;
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
        return first == null;
    }

    private void checkingIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(OUT_OF_BOUNDS, index, size));
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
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

    private T unlink(Node<T> node) {
        if (node == first) {
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else if (node == last) {
            last = last.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return node.element;
    }

    private void addLastElement(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void addElementByIndex(T value, Node<T> nodeOnIndex) {
        Node<T> previousNode = nodeOnIndex.prev;
        Node<T> newNode = new Node<>(previousNode, value, nodeOnIndex);
        nodeOnIndex.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }
}
