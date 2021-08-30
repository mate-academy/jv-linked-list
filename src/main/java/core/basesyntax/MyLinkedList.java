package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String
            INDEX_OF_BOUND_EXCEPTION_MESSAGE = "Index is more than size or less than 0!";
    private static final String FROM_START_TYPE_OF_ITERATION = "fromStart";
    private static final String FROM_END_TYPE_OF_ITERATION = "fromEnd";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String RIGHT_BRACKET = "]";
    private static final String LEFT_BRACKET = "[";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (isEmpty()) {
            addFirstElement(value);
        } else if (index == 0) {
            addElementInBeginning(value);
        } else if (index == size) {
            addElementInEnd(value);
        } else if (index < size && value != null) {
            addElementInTheMiddle(index, value);
        }
        size++;
    }

    private void addElementInBeginning(T value) {
        head.prev = new Node<>(null, value, head);
        head = head.prev;
    }

    private void addElementInEnd(T value) {
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    private void addFirstElement(T value) {
        tail = new Node<>(null, value, null);
        head = tail;
    }

    private void addElementInTheMiddle(int index, T value) {
        Node<T> current = head;
        int counter = 0;
        int step = 1;
        String iterationVariant = FROM_START_TYPE_OF_ITERATION;
        if (index > size / 2) {
            current = tail;
            counter = size - 1;
            step = -1;
            iterationVariant = FROM_END_TYPE_OF_ITERATION;
        }
        while (current != null) {
            if (counter == index) {
                link(current, value);
            }
            current = iterationVariant.equals(FROM_START_TYPE_OF_ITERATION)
                    ? current.next : current.prev;
            counter += step;
        }
    }

    private void link(Node<T> current, T value) {
        Node<T> prev = current.prev;
        Node<T> newElem = new Node<>(prev, value, current);
        prev.next = newElem;
        current.prev = newElem;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T elem : list) {
                add(elem);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        int counter = 0;
        int step = 1;
        String iterationVariant = FROM_START_TYPE_OF_ITERATION;
        if (index > size / 2) {
            current = tail;
            counter = size - 1;
            step = -1;
            iterationVariant = FROM_END_TYPE_OF_ITERATION;
        }
        while (current != null) {
            if (counter == index) {
                return current.value;
            }
            current = iterationVariant.equals(FROM_START_TYPE_OF_ITERATION)
                    ? current.next : current.prev;
            counter += step;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = null;
        Node<T> current = head;
        int counter = 0;
        int step = 1;
        String iterationVariant = FROM_START_TYPE_OF_ITERATION;
        if (index > size / 2) {
            current = tail;
            counter = size - 1;
            step = -1;
            iterationVariant = FROM_END_TYPE_OF_ITERATION;
        }
        while (current != null) {
            if (counter == index) {
                oldValue = current.value;
                current.value = value;
            }
            current = iterationVariant.equals(FROM_START_TYPE_OF_ITERATION)
                    ? current.next : current.prev;
            counter += step;
        }
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (head == null || isEmpty()) {
            return false;
        }
        int index = indexOf(object);
        if (index != -1) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T deletedValue = removeFromMiddle(index);
        size--;
        return deletedValue;
    }

    private T removeFromMiddle(int index) {
        Node<T> current = head;
        int counter = 0;
        int step = 1;
        String iterationVariant = FROM_START_TYPE_OF_ITERATION;
        if (index > size / 2) {
            current = tail;
            counter = size - 1;
            step = -1;
            iterationVariant = FROM_END_TYPE_OF_ITERATION;
        }
        while (current != null) {
            if (counter == index) {
                break;
            }
            current = iterationVariant.equals(FROM_START_TYPE_OF_ITERATION)
                    ? current.next : current.prev;
            counter += step;
        }
        unlink(current);
        return current.value;
    }

    private void unlink(Node<T> current) {
        if (current != null) {
            Node<T> newNext = current.next;
            Node<T> newPrev = current.prev;
            if (newNext != null) {
                newNext.prev = newPrev;
            } else {
                tail = tail.prev;
            }
            if (newPrev != null) {
                newPrev.next = newNext;
            } else {
                head = head.next;
            }
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

    private int indexOf(T value) {
        int counter = 0;
        Node<T> current = head;
        while (current != null) {
            if (equals(current.value, value)) {
                return counter;
            }
            counter++;
            current = current.next;
        }
        return -1;
    }

    private boolean equals(T first, T second) {
        return (first == null && second == null) || first.equals(second);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OF_BOUND_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(LEFT_BRACKET);
        int counter = 0;
        for (int i = 0; i < size; i++) {
            counter++;
            if (counter == size) {
                res.append(get(i));
            } else {
                res.append(get(i)).append(COMMA).append(SPACE);
            }
        }
        res.append(RIGHT_BRACKET);
        return res.toString();
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
