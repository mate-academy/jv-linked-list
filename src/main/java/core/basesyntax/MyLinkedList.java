package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> newNode;
    private Node<T> last;
    private Node<T> first;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T element) {
        linkLast(element);
    }

    @Override
    public void add(T element, int index) {
        if (index == size) {
            linkLast(element);
        } else {
            checkIndexPos(index);
            linkBefore(element, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T current : list) {
            linkLast(current);
        }
    }

    @Override
    public T get(int index) {
        checkIndexPos(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T element, int index) {
        checkIndexPos(index);
        Node<T> x = findNodeByIndex(index);
        T original = x.element;
        x.element = element;
        return original;
    }

    @Override
    public T remove(int index) {
        checkIndexPos(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (object == node.element || (object != null && object.equals(node.element))) {
                unlink(node);
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

    private Node<T> findNodeByIndex(int index) {
        int halfSize = size / 2;
        if (index < halfSize) {
            newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = last;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private void checkIndexPos(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index is invalid");
        }
    }

    private void linkBefore(T inputData, int index) {
        Node<T> midNode = findNodeByIndex(index);
        Node<T> someNode = midNode.prev;
        Node<T> newNode = new Node<>(someNode, inputData, midNode);
        midNode.prev = newNode;
        if (someNode == null) {
            first = newNode;
        } else {
            someNode.next = newNode;
        }
        size++;
    }

    void linkLast(T inputData) {
        Node<T> leftToNode = last;
        newNode = new Node<T>(leftToNode, inputData, null);
        last = newNode;
        if (leftToNode == null) {
            first = newNode;
        } else {
            leftToNode.next = newNode;
        }
        size++;
    }

    T unlink(Node<T> inputData) {
        T element = inputData.element;
        Node<T> next = inputData.next;
        Node<T> prev = inputData.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            inputData.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            inputData.next = null;
        }

        inputData.element = null;
        size--;

        return element;
    }
}
