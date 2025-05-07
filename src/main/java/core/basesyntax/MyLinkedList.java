package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String ERROR_MESSAGE = "No such index!";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node next;
        private Node prev;
        private T value;

        public Node(Node prev, T value, Node next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            isSetIndex(index);
            Node<T> currentNode = getElementByIndex(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node(prevNode, value, currentNode);
            if (prevNode != null) {
                prevNode.next = newNode;
            } else {
                head = newNode;
            }

            currentNode.prev = newNode;
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
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getElementByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(ERROR_MESSAGE);
        }

        Node<T> removeNode = getElementByIndex(index);
        unlink(removeNode);

        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = head;
        while (searchNode != null) {
            if (object == searchNode.value || (object != null && object.equals(searchNode.value))) {
                unlink(searchNode);
                return true;
            }
            searchNode = searchNode.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        Node<T> nodePrev = node.prev;
        Node<T> nodeNext = node.next;

        if (node.prev == null) {
            head = node.next;
        } else {
            nodePrev.next = nodeNext;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            nodeNext.prev = nodePrev;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void isSetIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(ERROR_MESSAGE);
        }
    }

    private Node<T> getElementByIndex(int index) {
        isSetIndex(index);

        if (index < (size / 2)) {
            Node<T> elementByIndex = head;
            for (int i = 0; i < index; i++) {
                elementByIndex = elementByIndex.next;
            }
            return elementByIndex;
        } else {
            Node<T> elementByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                elementByIndex = elementByIndex.prev;
            }
            return elementByIndex;
        }
    }
}
