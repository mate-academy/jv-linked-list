package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Illegal argument";
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.element = element;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        addElement(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        if (index == size) {
            addElement(value);
        } else {
            addElementByIndex(value, index);
        }
    }

    private void addElement(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    private void addElementByIndex(T value, int index) {
        Node<T> after = findByIndex(index);
        Node<T> before = after.prev;
        Node<T> newNode = new Node<>(before, value, after);
        after.prev = newNode;
        if (index == 0) {
            first = newNode;
        } else {
            before.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = findByIndex(index);
        T oldValue = setNode.element;
        setNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = findByIndex(index);
        return removeHelper(removeNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        Node<T> removeNode = null;
        for (int i = 0; i < size; i++) {
            if (currentNode.element == object
                    || currentNode.element != null
                    && currentNode.element.equals(object)) {
                removeNode = currentNode;
                break;
            }
            currentNode = currentNode.next;
        }

        if (removeNode != null) {
            removeHelper(removeNode);
            return true;
        }
        return false;
    }

    private T removeHelper(Node<T> removeNode) {
        Node<T> before = removeNode.prev;
        Node<T> after = removeNode.next;
        final T removeElement = removeNode.element;

        if (before == null) {
            first = after;
        } else {
            before.next = after;
            removeNode.prev = null;
        }

        if (after == null) {
            last = before;
        } else {
            after.prev = before;
            removeNode.next = null;
        }
        removeNode.element = null;
        size--;
        return removeElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findByIndex(int index) {
        checkIndex(index);
        return findNode(index);
    }

    private Node<T> findNode(int index) {
        int variableForCatchIndexFirst = 0;
        int variableForCatchIndexLast = size - 1;
        Node<T> findNode;
        if (index <= size / 2) {
            findNode = first;
            while (variableForCatchIndexFirst <= size / 2) {
                if (variableForCatchIndexFirst == index) {
                    return findNode;
                }
                findNode = findNode.next;
                variableForCatchIndexFirst++;
            }
        } else {
            findNode = last;
            while (variableForCatchIndexLast > size / 2) {
                if (variableForCatchIndexLast == index) {
                    return findNode;
                }
                findNode = findNode.prev;
                variableForCatchIndexLast--;
            }
        }
        return findNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }
}
