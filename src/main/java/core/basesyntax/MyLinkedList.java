package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private static final int START_ARRAY_LENGTH = 10;
    private Node [] elements;
    private int size;

    public MyLinkedList() {
        elements = new Node[START_ARRAY_LENGTH];
        elements[0] = new Node(null, null,null);
        size = 0;
    }

    @Override
    public void add(T value) {
        doNeedGrowth();
        elements[size] = new Node<>(null, value, null);
        if (size > 0) {
            elements[size - 1].next = elements[size];
            elements[size].prev = elements[size - 1];
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index) {
            throw new IndexOutOfBoundsException("Such index does not exist [" + index + "]");
        }
        doNeedGrowth();
        Node node = null;
        if (size == 0 && index == 0) {
            node = new Node(null, value, null);
        } else if (size == index && index > 0) {
            node = new Node(elements[index - 1], value, null);
            elements[index - 1].next = node;
        } else if (index != 0 && index != size) {
            node = new Node(elements[index].prev, value, elements[index].next);
            System.arraycopy(elements, index, elements, index + 1, size - index);
            elements[index - 1].next = node;
            elements[index + 1].prev = node;
        } else if (size > 0 && index == 0) {
            node = new Node(null, value, elements[index]);
            System.arraycopy(elements, index, elements, index + 1, size);
            elements[index + 1].prev = node;
        }
        elements[index] = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validate(index);
        return (T) elements[index].value;
    }

    @Override
    public T set(T value, int index) {
        validate(index);
        T replaced = (T) elements[index].value;
        elements[index].value = value;
        return replaced;
    }

    @Override
    public T remove(int index) {
        validate(index);
        if (index > 0) {
            elements[index - 1].next = elements[index];
            if (index - 1 == size) {
                elements[index + 1].prev = elements[index - 1];
            }
        }
        T removed = (T) elements[index].value;
        System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        elements[--size] = null;
        return removed;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == elements[i].value
                    || (object != null && object.equals(elements[i].value))) {
                remove(i);
                return true;
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

    private void doNeedGrowth() {
        if (elements.length == size) {
            grow();
        }
    }

    private void grow() {
        Node[] copied = new Node[(int) (elements.length * 1.5)];
        System.arraycopy(elements, 0, copied, 0, size);
        elements = copied;
    }

    private void validate(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Such index does not exist [" + index + "]");
        }
    }
}

