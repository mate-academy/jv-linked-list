package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int count;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        Node<T> temp = new Node<>(value, null, lastNode);
        if (count == 0) {
            firstNode = temp;
        } else {
            lastNode.next = temp;
        }
        lastNode = temp;
        count++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == count) {
                add(value);
            } else {
                Node<T> nextNode = getNodeByIndex(index);
                Node<T> prewNode = nextNode.prev;
                Node<T> newNode = new Node<>(value, nextNode, prewNode);
                if (prewNode == null) {
                    firstNode = newNode;
                } else {
                    prewNode.next = newNode;
                }
                nextNode.prev = newNode;
                count++;
            }
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (indexIsExist(index)) {
            Node<T> temp = getNodeByIndex(index);
            return temp.item;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(T value, int index) {
        if (indexIsExist(index)) {
            Node<T> temp = getNodeByIndex(index);
            T oldValue = temp.item;
            temp.item = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T remove(int index) {
        if (indexIsExist(index)) {
            Node<T> temp = getNodeByIndex(index);
            unlink(temp);
            return temp.item;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < count; i++) {
            if (getNodeByIndex(i).item == t || getNodeByIndex(i) != null
                    && getNodeByIndex(i).item.equals(t)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public void unlink(Node<T> tempNode) {
        if (tempNode.prev == null) {
            firstNode = tempNode.next;
        } else {
            tempNode.prev.next = tempNode.next;
        }
        if (tempNode.next == null) {
            lastNode = tempNode.prev;
        } else {
            tempNode.next.prev = tempNode.prev;
        }
        count--;
    }

    private boolean indexIsExist(int index) {
        return index >= 0 && index < count;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> temp;
        int i;
        if ((count - index) < index) {
            temp = lastNode;
            i = count - 1;
            while (i != index) {
                temp = temp.prev;
                i--;
            }
        } else {
            temp = firstNode;
            i = 0;
            while (i != index) {
                temp = temp.next;
                i++;
            }
        }
        return temp;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

    }
}
