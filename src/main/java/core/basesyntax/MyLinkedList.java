package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyNode<T> first;
    private MyNode<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (first == null) {
            MyNode<T> newNode = new MyNode<>(null, value, null);
            first = newNode;
            last = newNode;
        } else {
            MyNode<T> prevNode = last;
            MyNode<T> newNode = new MyNode<>(prevNode, value, null);
            last = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        MyNode<T> oldNode = findNodeByIndex(index);
        if (oldNode == first) {
            MyNode<T> nextNode = first;
            MyNode<T> newNode = new MyNode<>(null, value, nextNode);
            first = newNode;
            nextNode.prev = newNode;
        } else {
            MyNode<T> prevNode = oldNode.prev;
            MyNode<T> newNode = new MyNode<>(prevNode, value, oldNode);
            oldNode.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        MyNode<T> resultNode = findNodeByIndex(index);
        return resultNode.item;
    }

    @Override
    public T set(T value, int index) {
        MyNode<T> currentNode = findNodeByIndex(index);
        T result = currentNode.item;
        currentNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        MyNode<T> nodeToRemove = findNodeByIndex(index);
        unlinkNode(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> currentNode = first;
        while (currentNode != null) {
            if ((currentNode.item == object)
                    || (currentNode.item != null && currentNode.item.equals(object))) {
                unlinkNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void unlinkNode(MyNode<T> t) {
        if (t == first) {
            first = t.next;
            t.next = null;
        } else if (t == last) {
            last = t.prev;
            t.prev = null;
        } else {
            t.next.prev = t.prev;
            t.prev.next = t.next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " invalid for this size: " + size);
        }
    }

    private MyNode<T> findNodeByIndex(int index) {
        checkIndex(index);
        MyNode<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class MyNode<T> {
        private MyNode<T> prev;
        private MyNode<T> next;
        private T item;

        public MyNode(MyNode<T> prev, T item, MyNode<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
