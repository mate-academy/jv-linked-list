package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO_START_INDEX = 0;
    private static final int ZERO_SIZE = 0;
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value, null, null);
        int currentIndex = ZERO_START_INDEX;
        checkIndex(index < 0);
        if (first == null) {
            first = newNode;
        } else {
            for (Node<T> current = first; current != null; current = current.next) {
                if (index == currentIndex || current == null) {
                    current.previous.next = newNode;
                    newNode.previous = current.previous;
                    current.previous = newNode;
                    newNode.next = current;
                }
                currentIndex++;
            }
            checkIndex(index > currentIndex);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        int currentIndex = ZERO_START_INDEX;
        checkIndex(index < 0);
        for (Node<T> current = first; current != null; current = current.next) {
            if (index == currentIndex) {
                return current.value;
            }
            currentIndex++;
        }
        checkIndex(index >= currentIndex);
        return null;
    }

    @Override
    public T set(T value, int index) {
        int currentIndex = ZERO_START_INDEX;
        checkIndex(index < 0);
        for (Node<T> current = first; current != null; current = current.next) {
            if (index == currentIndex) {
                T oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            currentIndex++;
        }
        checkIndex(index >= currentIndex);
        return null;
    }

    @Override
    public T remove(int index) {
        int currentIndex = ZERO_START_INDEX;
        checkIndex(index < 0);
        for (Node<T> current = first; current != null; current = current.next) {
            if (index == currentIndex) {
                if(current == first) {
                    current.next.previous = null;
                    first = current.next;
                    size--;
                    return current.value;
                } else if (current == last) {
                    current.previous.next = null;
                    last = current.previous;
                    size--;
                    return current.value;
                } else {
                    current.next.previous = current.previous;
                    current.previous.next = current.next;
                    size--;
                    return current.value;
                }
            }
            currentIndex++;
        }
        checkIndex(index >= currentIndex);
        return null;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = first; current != null; current = current.next) {
            if (object != null && object.equals(current.value)) {
                if (current == first) {
                    if(current.next == null){
                        first = null;
                        last = null;
                        size--;
                        return true;
                    }
                    current.next.previous = null;
                    first = current.next;
                    size--;
                    return true;
                } else if(current == last) {
                     current.previous.next = null;
                     last = current.previous;
                     size--;
                     return true;
                } else {
                    current.next.previous = current.previous;
                    current.previous.next = current.next;
                    size--;
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
        return size == ZERO_SIZE;
    }

    private void checkIndex(boolean outOfBoundCondition ) {
        if (outOfBoundCondition) {
            throw new IndexOutOfBoundsException();
        }
    }

        private static class Node<T> {
            T value;
            Node<T> next;
            Node<T> previous;

            Node(T value, Node<T> previous, Node<T> next) {
                this.value = value;
                this.next = next;
                this.previous = previous;
            }
        }
    }
