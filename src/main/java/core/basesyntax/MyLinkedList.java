package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int currentAmount = 0;
    private transient Node<T> first;
    private transient Node<T> last;

    public void check(int index) {
        if (index < 0 || index >= currentAmount) {
            throw new ArrayIndexOutOfBoundsException("Non-valid index");
        }
    }

    @Override
    public boolean add(T value) {
        int previousAmount = currentAmount;
        addElement(value);
        return currentAmount - previousAmount != 0;
    }

    @Override
    public void add(T value, int index) {
        if (index != currentAmount) {
            check(index);
        }
        if (index == 0) {
            addFirst(value);
        } else if (index == currentAmount) {
            addLast(value);
        } else {
            Node<T> nodeIndex = nodeBy(index);
            nodeIndex.prev = new Node<>(nodeIndex.prev, value, nodeIndex);
            currentAmount++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        int previousAmount = currentAmount;
        for (T value : list) {
            addElement(value);
        }
        return currentAmount - previousAmount == list.size();
    }

    @Override
    public T get(int index) {
        check(index);
        return nodeBy(index).getData();
    }

    @Override
    public T set(T value, int index) {
        check(index);
        Node<T> nodeIndex = nodeBy(index);
        T oldData = nodeIndex.getData();
        nodeIndex.setData(value);
        return oldData;
    }

    @Override
    public T remove(int index) {
        check(index);
        Node<T> removableNode = nodeBy(index);
        T data = removableNode.data;
        if (index == 0) {
            if (currentAmount == 1) {
                first = null;
                last = null;
            } else {
                first = removableNode.next;
                removableNode.next.prev = null;
            }
        } else if (index > 0 && index < currentAmount - 1) {
            removableNode.prev.next = removableNode.next;
            removableNode.next.prev = removableNode.prev;
        } else if (index == currentAmount - 1) {
            last = removableNode.prev;
            last.next = null;
        }
        currentAmount--;
        return data;
    }

    @Override
    public boolean remove(T t) {
        for (int index = 0; index < currentAmount; index++) {
            Node<T> removableNode = nodeBy(index);
            if (t == removableNode.data
                    || t != null && t.equals(removableNode.data)) {
                if (index != 0) {
                    removableNode.prev.next = removableNode.next;
                }
                if (index != currentAmount - 1) {
                    removableNode.next.prev = removableNode.prev;
                }
                currentAmount--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return currentAmount;
    }

    @Override
    public boolean isEmpty() {
        return currentAmount == 0;
    }

    private static class Node<T> {

        T data;
        Node<T> next;
        Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.data = element;
            this.next = next;
        }

        private void setData(T value) {
            data = value;
        }

        private T getData() {
            return data;
        }
    }

    private Node<T> nodeBy(int index) {
        check(index);
        Node<T> resultNode = last;
        while (index != currentAmount - 1) {
            resultNode = resultNode.prev;
            index++;
        }
        return resultNode;
    }

    private void addElement(T value) {
        if (currentAmount == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    private void addFirst(T value) {
        Node<T> newFirst = new Node<>(null, value, null);
        if (currentAmount != 0) {
            newFirst.next = first;
            first.prev = newFirst;
        } else {
            last = newFirst;
        }
        first = newFirst;
        currentAmount++;
    }

    private void addLast(T value) {
        Node<T> newLast = new Node<>(null, value, null);
        last.next = newLast;
        newLast.prev = last;
        last = newLast;
        currentAmount++;
    }
}
