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
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        T[] addArray = (T[]) list.toArray();
        int lengthOfArray = addArray.length;
        if (lengthOfArray == 0) {
            return false;
        }
        Node<T> prev;
        prev = last;
        for (T elementAddArray : addArray) {
            Node<T> inputNode = new Node<>(elementAddArray, null, prev);
            if (prev == null) {
                first = inputNode;
            } else {
                prev.next = inputNode;
            }
            prev = inputNode;
        }
        size += lengthOfArray;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = node(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T t) {
        if (t == null) {
            for (Node<T> i = first; i != null; i = i.next) {
                if (i.element == null) {
                    unlink(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = first; i != null; i = i.next) {
                if (i.element.equals(t)) {
                    unlink(i);
                    return true;
                }
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

    private void linkFirst(T e) {
        final MyLinkedList.Node<T> f = first;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(e, f, null);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new MyLinkedList.Node<>(e, null, l);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds search index");
        }
    }

    public void linkBefore(T element, Node<T> movingElement) {
        Node<T> currentPrev = movingElement.prev;
        Node<T> inputNode = new Node<>(element, movingElement, currentPrev);
        movingElement.prev = inputNode;
        if (currentPrev == null) {
            first = inputNode;
        } else {
            currentPrev.next = inputNode;
        }
        size++;
    }

    public Node<T> node(int index) {
        Node<T> currentElement = first;
        for (int i = 0; i < index; i++) {
            currentElement = currentElement.next;
        }
        return currentElement;
    }

    public T unlink(Node<T> currentObject) {
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
