package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        final Node<T> oldLast = last;
        final Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> newPrev = methodBoostPerformance(index).prev;
            final Node<T> newNode = new Node<>(newPrev, value, methodBoostPerformance(index));
            methodBoostPerformance(index).prev = newNode;
            if (newPrev == null) {
                first = newNode;
            } else {
                newPrev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        T[] arrayCollection = (T[]) list.toArray();
        Node<T> newPrev;
        Node<T> newNext;
        newNext = null;
        newPrev = last;
        for (T listT : arrayCollection) {
            @SuppressWarnings("unchecked") T collection = (T) listT;
            Node<T> newNode = new Node<>(newPrev, collection, null);
            if (newPrev == null) {
                first = newNode;
            } else {
                newPrev.next = newNode;
            }
            newPrev = newNode;
        }
        if (newNext == null) {
            last = newPrev;
        } else {
            newPrev.next = newNext;
            newNext.prev = newPrev;
        }
        size += arrayCollection.length;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return methodBoostPerformance(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> setNode = methodBoostPerformance(index);
        T oldVal = setNode.item;
        setNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(methodBoostPerformance(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> iterationNode = first;
                    iterationNode != null;
                    iterationNode = iterationNode.next) {
                if (iterationNode.item == null) {
                    unlink(iterationNode);
                    return true;
                }
            }
        } else {
            for (Node<T> iterationNode = first;
                    iterationNode != null;
                    iterationNode = iterationNode.next) {
                if (object.equals(iterationNode.item)) {
                    unlink(iterationNode);
                    return true;
                }
            }
        }
        return false;
    }

    T unlink(Node<T> removeNode) {
        final T element = removeNode.item;
        final Node<T> next = removeNode.next;
        final Node<T> prev = removeNode.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            removeNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            removeNode.next = null;
        }
        removeNode.item = null;
        size--;
        return element;
    }

    // Pay attention to the index you receive as an input,
    // you can iterate only first or second half of the list,
    // depending on the index value. That will boost your lists’ performance.
    Node<T> methodBoostPerformance(int index) {
        if (index < (size >> 1)) {
            Node<T> firstHalfNode = first;
            for (int i = 0; i < index; i++) {
                firstHalfNode = firstHalfNode.next;
            }
            return firstHalfNode;
        } else {
            Node<T> secondHalfNode = last;
            for (int i = size - 1; i > index; i--) {
                secondHalfNode = secondHalfNode.prev;
            }
            return secondHalfNode;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(outOfBoundsMassage(index));
        }
    }

    private String outOfBoundsMassage(int index) {
        return " Invalid index: " + index + "for size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(outOfBoundsMassage(index));
        }
    }

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
