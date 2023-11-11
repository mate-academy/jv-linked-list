package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int listSize;
    private Node<T> first;
    private Node<T> last;

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    @Override
    public void add(T value) {
        if (getListSize() == 0) {
            setListSize(getListSize() + 1);
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
        } else {
            setListSize(getListSize() + 1);
            Node<T> newNode = new Node<>(last, value,null);
            last.next = newNode;
            last = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index == getListSize()) {
            add(value);
        } else {
            checkIndex(index);
            setListSize(getListSize() + 1);
            Node<T> testNode = nodesIterate(index);
            Node<T> newNode = new Node<>(testNode.prev,value,testNode);
            if (testNode.prev != null) {
                testNode.prev.next = newNode;
            }
            if (index == 0) {
                first = newNode;
            }
            newNode.next.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listValue : list) {
            add(listValue);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (nodesIterate(index) == null ? null : nodesIterate(index).item);
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldItem;
        oldItem = nodesIterate(index).item;
        nodesIterate(index).item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldItem;
        Node<T> testNode = nodesIterate(index);
        oldItem = testNode.item;
        if (testNode.prev != null) {
            testNode.prev.next = testNode.next;
        }
        if (testNode.next != null) {
            testNode.next.prev = testNode.prev;
        }
        unlink(testNode);
        setListSize(getListSize() - 1);
        return oldItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> testNode = first;
        for (int i = 0; i < getListSize(); i++) {
            if (equals(object, testNode.item)) {
                remove(i);
                return true;
            }
            testNode = testNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return getListSize();
    }

    @Override
    public boolean isEmpty() {
        return getListSize() == 0;
    }

    private Node<T> nodesIterate(int index) {
        Node<T> testNode;
        if (index <= getListSize() / 2) {
            testNode = first;
            for (int i = 0; i < index; i++) {
                testNode = testNode.next;
            }
        } else {
            testNode = last;
            for (int i = getListSize() - 1; i > index; i--) {
                testNode = testNode.prev;
            }
        }
        return testNode;
    }

    private void unlink(Node<T> testNode) {
        Node<T> next = testNode.next;
        Node<T> prev = testNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            testNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            testNode.next = null;
        }
        testNode.item = null;
    }

    private boolean equals(T objOne, T objTwo) {
        return (objOne == objTwo || objOne != null && objOne.equals(objTwo));
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T value, MyLinkedList.Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index >= getListSize() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
    }
}
