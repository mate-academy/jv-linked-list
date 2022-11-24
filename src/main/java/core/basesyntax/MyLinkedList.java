package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexAddValidatorForAddElement(index);

        if (index == 0 && size == 0) {
            addElementToEmptyList(value, index);
            return;
        }

        if (index == 0) {
            addElementToStart(value);
            return;
        }

        if (index == size) {
            add(value);
            return;
        }
        addElementToMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        Node<T> currentNode = first;
        if (index == 0) {
            return currentNode.item;
        }

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T temp = currentNode.item;
        currentNode.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);

        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        Node<T> temp = currentNode;
        if (currentNode == first) {
            return deleteFirstElementInList(currentNode, temp);
        }

        if (currentNode == last) {
            return removeElementFromEnd(currentNode);
        }
        return deleteElementFromMiddleOfList(currentNode);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            return deleteWhenElementIsNull(object);
        }

        if (findIndex(object) < 0) {
            return false;
        }

        if (remove(findIndex(object)).equals(object)) {
            return true;
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

    private void addElementToMiddle(T value, int index) {
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        Node<T> prev = currentNode.prev;
        prev.next = new Node<>(prev, value, currentNode);
        currentNode.prev = prev.next;
        prev.next.prev = currentNode;
        size++;
    }

    private void addElementToStart(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.prev = newNode;
        first = newNode;
        size++;
        return;
    }

    private void addElementToEmptyList(T value, int index) {
        add(value);
        indexValidation(index);
        return;
    }

    private void indexAddValidatorForAddElement(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds List");
        }
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds List");
        }
    }

    private int findIndex(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object.equals(currentNode.item)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    private T deleteElementFromMiddleOfList(Node<T> currentNode) {
        Node<T> nextNode = currentNode.next;
        Node<T> prevNode = currentNode.prev;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        return currentNode.item;
    }

    private T deleteFirstElementInList(Node<T> currentNode, Node<T> temp) {
        if (currentNode.next == null) {
            first = last = null;
            size--;
            return currentNode.item;
        }
        Node nextNode = currentNode.next;
        nextNode.prev = null;
        first = nextNode;
        size--;
        return temp.item;
    }

    private T removeElementFromEnd(Node<T> currentNode) {
        Node prevNode = currentNode.prev;
        prevNode.next = null;
        last = prevNode;
        size--;
        return currentNode.item;
    }

    private boolean deleteWhenElementIsNull(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
