package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DIVISOR = 2;
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLastElement(value);
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        if (size == index) {
            linkLastElement(value);
        } else {
            if (index >= 0 && index <= size) {
                Node<T> prev = node.prev;
                Node<T> newNode = new Node<>(prev, value, node);
                node.prev = newNode;
                if (prev == null) {
                    first = newNode;
                } else {
                    prev.next = newNode;
                }
            } else {
                throw new IndexOutOfBoundsException("there is not element at this position");
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }

    }

    @Override
    public T get(int index) {
        checkElementByIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementByIndex(index);
        Node<T> oldNode = getNodeByIndex(index);
        T oldVal = oldNode.value;
        oldNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementByIndex(index);
        return unlinkElement(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            Node<T> nodeForDelete = getNodeByIndex(i);
            if (element == nodeForDelete.value
                    || (element != null && element.equals(nodeForDelete.value))) {
                unlinkElement(nodeForDelete);
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
        return size == 0;
    }

    private void linkLastElement(T value) {

        Node<T> start = last;
        Node<T> newNode = new Node<>(start, value, null);
        last = newNode;

        if (start == null) {
            first = newNode;
        } else {
            start.next = newNode;
        }
        size++;

    }

    private boolean checkElementByIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new IndexOutOfBoundsException("isn't element at this index");
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = null;
        if (index < size / DIVISOR) {
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

    private T unlinkElement(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.next = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.prev = null;
        }
        size--;
        return node.value;

    }
}
