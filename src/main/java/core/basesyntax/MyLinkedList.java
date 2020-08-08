package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private T value;
    private MyLinkedList<T> next;
    private MyLinkedList<T> prev;

    private int size;
    private int counter;
    private MyLinkedList<T> first;
    private MyLinkedList<T> lastElement;

    private void firstElement(T value) {
        MyLinkedList<T> newElement = new MyLinkedList<>();
        newElement.value = value;
        first = newElement;
        lastElement = newElement;
        size++;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            firstElement(value);
        } else {
            MyLinkedList<T> newElement = new MyLinkedList<>();
            newElement.value = value;
            newElement.prev = lastElement;
            lastElement.next = newElement;
            if (lastElement.prev == null) {
                first.next = newElement;
            }
            lastElement = newElement;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        if (size == 0 || index == size) {
            add(value);
        } else {
            MyLinkedList<T> newElement = new MyLinkedList<>();
            newElement.value = value;
            newElement.next = getElement(index);
            getElement(index).prev = newElement;

            if (index > 0) {
                getElement(index - 1).next = newElement;
                newElement.prev = getElement(index - 1);
            } else {
                first = newElement;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        for (int i = 0; i < list.size(); i++) {
            add((T) list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldItem = getElement(index).value;
        getElement(index).value = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        T removedItemValue = getElement(index).value;

        if (size == 1) {
            first = null;
            lastElement = null;
        } else {
            if (getElement(index).next == null) {
                lastElement = getElement(index - 1);
            } else {
                getElement(index + 1).prev = getElement(index).prev;
            }
            if (getElement(index).prev == null) {
                first = getElement(index + 1);
            } else {
                getElement(index - 1).next = getElement(index).next;
            }
        }
        size--;
        return removedItemValue;
    }

    @Override
    public boolean remove(T t) {
        counter = 0;
        while (counter < size) {
            if (getElement(counter).value == t
                    || (t != null && t.equals(getElement(counter).value))) {
                remove(counter);
                return true;
            }
            counter++;
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

    private MyLinkedList<T> getElement(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        MyLinkedList<T> element = first;
        counter = 0;
        while (counter < index) {
            element = element.next;
            counter++;
        }
        return element;
    }
}
