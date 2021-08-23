package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int CONSTANT_FOR_ADD_FUNCTION = 1;
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node(null, value, null);
            last = first;
            size++;
        } else if (size > 0) {
            last = new Node(getNodeByIndex(size - 1), value, null);
            getNodeByIndex(size - 1).next = last;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            first = new Node(null, value, first);
            getNodeByIndex(1).prev = first;
            size++;
        } else {
            Node<T> newNode = new Node(getNodeByIndex(index).prev, value, getNodeByIndex(index - 1).next);
            // try after test change getNodeByIndex(index - 1).next -->  (Node) getNodeByIndex(index).item
            getNodeByIndex(index - 1).next = newNode;
            getNodeByIndex(index + 1).prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list)  {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index + CONSTANT_FOR_ADD_FUNCTION); // перевірка чи не дорівнює розміру і чи не дорів нулю
        indexCheck(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index + CONSTANT_FOR_ADD_FUNCTION);
        indexCheck(index);
        T buffer = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return buffer;
    }

    @Override
    public T remove(int index) {
        indexCheck(index + CONSTANT_FOR_ADD_FUNCTION);
        indexCheck(index);
        T buffer = getNodeByIndex(index).item;
        if (size == 1) {
            first.item = null;
        } else if (index == 0) {
            first = getNodeByIndex(index + 1);
            getNodeByIndex(index + 1).prev = null;
        } else if (index == size - 1) {
            getNodeByIndex(index - 1).next = null;
            last = getNodeByIndex(index - 1);
        } else {
            getNodeByIndex(index + 1).prev = getNodeByIndex(index).prev;
            getNodeByIndex(index - 1).next = getNodeByIndex(index).next;
        }
        size--;
        return buffer;
    }

    @Override
    public boolean remove(T object) {
        Node<T> bufferNode = first;
        int index = 0;
        if (object == null) {
            if (object == null && first.item == null) {
                first = getNodeByIndex(index + 1);
                getNodeByIndex(index + 1).prev = null;
                size--;
                return true;
            }
            while (bufferNode.item != null && bufferNode != last) {
                bufferNode = bufferNode.next;
                index++;
            }
            if (index == size - 1) {
                return false;
            }
        } else {
            while (!object.equals(bufferNode.item)) {
                if (bufferNode.next == null) {
                    return false;
                }
                bufferNode = bufferNode.next;
                index++;
            }
        }
        if (size == 1) {
            first.item = null;
        } else if (index == 0) {
            first = getNodeByIndex(index + 1);
            getNodeByIndex(index + 1).prev = null;
        } else if (index == size - 1) {
            getNodeByIndex(index - 1).next = null;
            last = getNodeByIndex(index - 1);
        } else {
            getNodeByIndex(index + 1).prev = getNodeByIndex(index).prev;
            getNodeByIndex(index - 1).next = getNodeByIndex(index).next;
        }
        size--;
        return  true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> bufferNode = first;
        for (int i = 0; i < index; i++) {
            bufferNode = bufferNode.next;
        }
        return bufferNode;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void indexCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
    }
}
