package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Illegal argument";
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

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
        Node<T> lastForThisNode = last;
        last = newNode;
        if (lastForThisNode == null) {
            first = newNode;
        } else {
            lastForThisNode.next = newNode;
        }
        size++;
    }

    private void addElementByIndex(T value, int index) {
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, findByIndex(index));
            findByIndex(index).prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> after = findByIndex(index);
            Node<T> before = after.prev;
            Node<T> newNode = new Node<>(before, value, after);
            after.prev = newNode;
            before.next = newNode;
            size++;
        }
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
            if (Objects.equals(currentNode.element, object)) {
                removeNode = currentNode;
                break;
            }
            currentNode = currentNode.next;
        }

        if (removeNode == null) {
            return false;
        }
        removeHelper(removeNode);
        return true;
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
        indexOutOfBoundsExceptionCheck(index);
        if (index == 0) {
            return first;
        } else if (index == size - 1) {
            return last;
        }
        return findNode(index);
    }

    private Node<T> findNode(int index) {
        int variableForCatchIndexFirst = 0;
        int variableForCatchIndexLast = size - 1;
        Node<T> findNode;
        if (index <= size / 2) {
            findNode = first;
            while (variableForCatchIndexFirst <= size / 2) {
                findNode = findNode.next;
                if (++variableForCatchIndexFirst == index) {
                    return findNode;
                }
            }
        } else {
            findNode = last;
            while (variableForCatchIndexLast > size / 2) {
                findNode = findNode.prev;
                if (--variableForCatchIndexLast == index) {
                    return findNode;
                }
            }
        }
        return findNode;
    }

    private void indexOutOfBoundsExceptionCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }
}
