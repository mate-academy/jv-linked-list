package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds search index");
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elementOfList : list) {
            add(elementOfList);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = findNode(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T t) {
        int i = 0;
        while (i != size) {
            if (t == get(i) || get(i).equals(t)) {
                unlink(findNode(i));
                return true;
            }
            i = i + 1;
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

    private void linkFirst(T e) {
        MyLinkedList.Node<T> firstElement = first;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(e, firstElement, null);
        first = newNode;
        if (firstElement == null) {
            last = newNode;
        } else {
            firstElement.prev = newNode;
        }
        size++;
    }

    private void linkLast(T e) {
        Node<T> lastElement = last;
        Node<T> newNode = new MyLinkedList.Node<>(e, null, lastElement);
        last = newNode;
        if (lastElement == null) {
            first = newNode;
        } else {
            lastElement.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds search index");
        }
    }

    private void linkBefore(T element, Node<T> movingElement) {
        Node<T> currentPrev = movingElement.prev;
        Node<T> inputNode = new Node<>(element, movingElement, currentPrev);
        if (currentPrev == null) {
            first = inputNode;
        } else {
            currentPrev.next = inputNode;
        }
        movingElement.prev = inputNode;
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> currentElement;
        if (index < (size >> 1)) {
            currentElement = first;
            for (int i = 0; i < index; i++) {
                currentElement = currentElement.next;
            }
            return currentElement;
        } else {
            currentElement = last;
            for (int i = size - 1; i > index; i--) {
                currentElement = currentElement.prev;
            }
            return currentElement;
        }

    }

    private T unlink(Node<T> currentObject) {
        T element = currentObject.element;
        Node<T> prev = currentObject.prev;
        Node<T> next = currentObject.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            currentObject.next = null;
        }
        currentObject.element = null;
        size--;
        return element;
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

}
