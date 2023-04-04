package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, tail);
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
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        Node<T> nodeBeforeIndex = nodeAtIndex.getPrevElement();
        Node<T> newNode = new Node<>(nodeAtIndex, value, nodeBeforeIndex);
        if (nodeBeforeIndex == null) {
            head = newNode;
        } else {
            nodeBeforeIndex.setNextElement(newNode);
        }
        nodeAtIndex.setPrevElement(newNode);
        size++;
    }

    private Node<T> getNodeAtIndex(int index) {
        checkIndexException(index);
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

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexException(index);
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        return nodeAtIndex.getItemIndex();
    }

    @Override
    public T set(T value, int index) {
        checkIndexException(index);
        Node<T> temp = getNodeAtIndex(index);
        T oldItem = temp.getItemIndex();
        temp.setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
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
        return deleteNode.getItemIndex();
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (Objects.equals(temp.getItemIndex(), object)) {
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

    private void checkIndexException(int index) {
        if (index < 0 || index > size || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}
