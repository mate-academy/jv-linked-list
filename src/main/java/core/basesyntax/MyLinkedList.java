package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    public MyLinkedList() {
        this.size = 0;
    }

    private static class Node<T> {
        T element;
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index == 0) {
            return first;
        } else if (index == size - 1) {
            return last;
        }
        int count = 0;
        MyLinkedList.Node<T> currentNode = this.first;
        while (true) {
            if (count == index) {
                break;
            }
            currentNode = currentNode.next;
            count++;
        }
        return currentNode;
    }

    private Node<T> findNodeByValue(T t) {
        if (size == 1) {
            return first;
        }
        MyLinkedList.Node<T> currentNode = this.first;
        int count = 0;
        boolean found = false;
        while (count < size - 1) {
            if (currentNode.element == t || currentNode.element.equals(t)) {
                found = true;
                break;
            }
            currentNode = currentNode.next;
            count++;
        }
        if (found) {
            return currentNode;
        }
        return null;
    }

    private void linkFirst(T t) {
        MyLinkedList.Node<T> first = this.first;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(null, t, first);
        if (first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            first.previous = newNode;
            this.first = newNode;
        }
        size++;
    }

    private void unLinkFirst() {
        if (this.first.next == null) {
            this.first = null;
            this.last = null;
            size = 0;
            return;
        }
        this.first = this.first.next;
        size--;
    }

    private void linkLast(T t) {
        MyLinkedList.Node<T> last = this.last;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(last, t, null);
        if (last == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            last.next = newNode;
            this.last = newNode;
        }
        size++;
    }

    private void unLinkLast() {
        if (this.last.previous == null) {
            this.first = null;
            this.last = null;
            size = 0;
            return;
        }
        this.last = this.last.previous;
        size--;
    }

    private void unlink(Node<T> node) {
        MyLinkedList.Node<T> leftNode = node.previous;
        MyLinkedList.Node<T> rightNode = node.next;
        leftNode.next = rightNode;
        rightNode.previous = leftNode;
        size--;
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
            return;
        } else if (index == size) {
            linkLast(value);
            return;
        }
        checkIndex(index);
        MyLinkedList.Node<T> newPrevioustNode = findNodeByIndex(index - 1);
        MyLinkedList.Node<T> newNexttNode = findNodeByIndex(index);
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(newPrevioustNode,
                value,
                newNexttNode);
        newPrevioustNode.next = newNode;
        newNexttNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T returnValue = findNodeByIndex(index).element;
        findNodeByIndex(index).element = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            T t = first.element;
            unLinkFirst();
            return t;
        } else if (index == size - 1) {
            T t = last.element;
            unLinkLast();
            return t;
        }
        MyLinkedList.Node<T> currentNode = findNodeByIndex(index);
        unlink(currentNode);
        return currentNode.element;
    }

    @Override
    public boolean remove(T t) {
        MyLinkedList.Node<T> currentNode = findNodeByValue(t);
        if (currentNode == null) {
            return false;
        }
        if (currentNode.previous == null) {
            unLinkFirst();
            return true;
        }
        if (currentNode.next == null) {
            unLinkLast();
            return true;
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
