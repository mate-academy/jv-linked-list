package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, tail, value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNextElement(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        Node<T> nodeBeforeIndex = nodeAtIndex.getPrevElement();
        Node<T> newNode = new Node<>(nodeAtIndex, nodeBeforeIndex, value);

        if (nodeBeforeIndex == null) {
            head = newNode;
        } else {
            nodeBeforeIndex.setNextElement(newNode);
        }
        nodeAtIndex.setPrevElement(newNode);
        size++;
    }

    private Node<T> getNodeAtIndex(int index) {
        checkElementIndex(index);
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNextElement();
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.getPrevElement();
            }
        }
        return temp;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        return nodeAtIndex.getItem();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> temp = getNodeAtIndex(index);
        T oldItem = temp.getItem();
        temp.setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> deleteNode = getNodeAtIndex(index);
        if (deleteNode == null) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> nextNode = deleteNode.getNextElement();
        Node<T> prevNode = deleteNode.getPrevElement();
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.setNextElement(nextNode);
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.setPrevElement(prevNode);
        }
        size--;
        return deleteNode.getItem();
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (Objects.equals(temp.getItem(), object)) {
                remove(index);
                return true;
            }
            temp = temp.getNextElement();
            index++;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
