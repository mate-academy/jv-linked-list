package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_INDEX = 0;
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
        if (size() == 1) {
            secondElementAsLast(value);
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
            first = new Node<>(null, value, null);
            first.nextElement = current;
            current.prevElement = first;
            size++;
            return;
        }
        if (size() == 1) {
            secondElementAsLast(value);
            return;
        }
        if (index == size()) {
            addLast(value);
            return;
        }
        counter = DEFAULT_INDEX;
        current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        temp = new Node<>(null, value, null);
        secondTemp = current.prevElement;
        temp.prevElement = secondTemp;
        temp.nextElement = current;
        secondTemp.nextElement = temp;
        current.prevElement = temp;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            current = last;
            last = new Node<>(null,list.get(i),null);
            last.prevElement = current;
            current.nextElement = last;
            size++;
        }
    }

    private void addFirst(T value) {
        first = new Node<>(null,value,null);
        size++;
    }

    private void secondElementAsLast(T value) {
        last = new Node<>(null, value, null);
        last.prevElement = first;
        first.nextElement = last;
        size++;
    }

    private void addLast(T value) {
        current = last;
        last = new Node<>(null,value,null);
        current.nextElement = last;
        last.prevElement = current;
        size++;
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        if (size() == 1) {
            return first.value;
        }
        counter = DEFAULT_INDEX;
        current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        indexChecker(index);
        counter = DEFAULT_INDEX;
        current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        current.value = value;
        return value;
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
        counter = DEFAULT_INDEX;
        current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        removeInTheMiddle();
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        current = first;
        counter = DEFAULT_INDEX;
        while (current != null) {
            if ((current.value == null && object == null) || (current.value.equals(object))) {
                if (counter == DEFAULT_INDEX) {
                    removeFirst();
                } else {
                    if (counter == size() - 1) {
                        removeLast();
                    } else {
                        removeInTheMiddle();
                    }
                }
                return true;
            }
            counter++;
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

    private void removeInTheMiddle() {
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

    private static class Node<T> {
        private T value;
        private Node<T> prevElement;
        private Node<T> nextElement;

        private Node(Node<T> prevElement,T value, Node<T> nextElement) {
            this.value = value;
            this.prevElement = prevElement;
            this.nextElement = nextElement;
        }
    }
}
