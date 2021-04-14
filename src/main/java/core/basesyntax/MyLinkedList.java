package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION = "Index out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        addNode(new Node<>(last, value, null));
        return true;
    }

    @Override
    public void add(T value, int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION + index);
        }
        if (index == size) {
            add(value);
        } else {
            addElementByIndex(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (object == current.item || object != null
                    && object.equals(current.item)) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private boolean checkElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkIndex(int index) {
        if (!checkElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        Node<T> currentNode = last;
        for (int i = 0; i < size - index - 1; i++) {
            currentNode = currentNode.previous;
        }
        return currentNode;
    }

    private void addNode(Node<T> node) {
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.previous;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.previous = prevNode;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private void addElementByIndex(T value, int index) {
        Node<T> nodeIndex = findNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeIndex.previous, value, nodeIndex);
        nodeIndex.previous = newNode;
        if (newNode.previous == null) {
            first = newNode;
        } else {
            newNode.previous.next = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }
}
