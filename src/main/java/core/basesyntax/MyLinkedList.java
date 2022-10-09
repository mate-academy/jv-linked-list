package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_INDEX = 0;
    private static final int DIVIDER = 2;
    private Node<T> first;
    private Node<T> last;
    private Node<T> current;
    private Node<T> temp;
    private Node<T> secondTemp;
    private int size = 0;
    private int counter = 0;

    @Override
    public void add(T value) {
        if (size() == DEFAULT_INDEX) {
            addFirst(value);
            return;
        }
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        indexCheckerForAdding(index);
        if (size() == DEFAULT_INDEX) {
            addFirst(value);
            return;
        }
        if (index == DEFAULT_INDEX) {
            current = first;
            first = new Node<>(value);
            first.nextElement = current;
            current.prevElement = first;
            size++;
            return;
        }
        if (index == size()) {
            addLast(value);
            return;
        }
        current = getNodeByIndex(index);
        temp = new Node<>(value);
        secondTemp = current.prevElement;
        temp.prevElement = secondTemp;
        temp.nextElement = current;
        secondTemp.nextElement = temp;
        current.prevElement = temp;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addLast(t);
        }
    }

    private void addFirst(T value) {
        first = new Node<>(value);
        last = first;
        size++;
    }

    private void addLast(T value) {
        current = last;
        last = new Node<>(value);
        current.nextElement = last;
        last.prevElement = current;
        size++;
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexChecker(index);
        current = getNodeByIndex(index);
        T removedValue = current.value;
        current.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        indexChecker(index);
        if (index == DEFAULT_INDEX) {
            removeFirst();
            return temp.value;
        }
        if (index == size() - 1) {
            removeLast();
            return temp.value;
        }
        current = getNodeByIndex(index);
        removeInTheMiddle(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        current = first;
        while (current != null) {
            if ((current.value == null && object == null) || (current.value.equals(object))) {
                if (current == first) {
                    removeFirst();
                } else if (current == last) {
                    removeLast();
                } else {
                    removeInTheMiddle(current);
                }
                return true;
            }
            current = current.nextElement;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == DEFAULT_INDEX;
    }

    private void removeFirst() {
        temp = first;
        first = temp.nextElement;
        temp.nextElement = null;
        size--;
    }

    private void removeLast() {
        temp = last;
        last = temp.prevElement;
        temp.prevElement = null;
        size--;
    }

    private void removeInTheMiddle(Node<T> current) {
        temp = current.prevElement;
        secondTemp = current.nextElement;
        temp.nextElement = current.nextElement;
        secondTemp.prevElement = current.prevElement;
        current.prevElement = null;
        current.nextElement = null;
        size--;
    }

    private void indexChecker(int index) {
        if (index < DEFAULT_INDEX || index >= size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds for size "
                    + size() + " with element " + index);
        }
    }

    private void indexCheckerForAdding(int index) {
        if (index < DEFAULT_INDEX || index > size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds for size "
                    + size() + " with element " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index <= size() / DIVIDER) {
            return countFromFirst(index);
        } else {
            return countFromLast(index);
        }
    }

    private Node<T> countFromFirst(int index) {
        counter = DEFAULT_INDEX;
        current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        return current;
    }

    private Node<T> countFromLast(int index) {
        counter = size() - 1;
        current = last;
        while (counter > index) {
            current = current.prevElement;
            counter--;
        }
        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prevElement;
        private Node<T> nextElement;

        Node(T value) {
            this.value = value;
        }
    }
}
