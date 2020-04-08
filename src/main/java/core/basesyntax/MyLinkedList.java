package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        this.checkPosition(index);
        if (index == this.size) {
            this.addLast(value);
        } else {
            this.addInside(value, this.getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).currE;
    }

    @Override
    public T set(T value, int index) {
        this.checkPosition(index);
        checkIndex(index);
        Node<T> oldNode = getNodeByIndex(index);
        T returnValue = oldNode.currE;
        oldNode.currE = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNodeByIndex(index);
        Node<T> next = removed.nextE;
        Node<T> prev = removed.prevE;

        if (prev == null) {
            this.first = next;
        } else {
            prev.nextE = next;
            removed.prevE = null;
        }
        if (next == null) {
            this.last = prev;
        } else {
            next.prevE = prev;
            removed.nextE = null;
        }
        size--;
        return removed.currE;
    }

    @Override
    public boolean remove(T t) {
        int index = -1;
        Node<T> checked = first;
        for (int i = 0; i < size; i++) {
            if (checked.currE == t || checked.currE != null && checked.currE.equals(t)) {
                index = i;
                break;
            }
            checked = checked.nextE;
        }
        if (index == -1) {
            return false;
        } else {
            remove(index);
        }
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

    private void checkPosition(int index) {
        if (!(index >= 0 && index <= this.size)) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of Size: " + this.size);
        }
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of Size: " + this.size);
        }
        return true;
    }

    private void addLast(T element) {
        Node<T> last = this.last;
        Node<T> newNode = new Node(last, element, (Node) null);
        this.last = newNode;
        if (last == null) {
            this.first = newNode;
        } else {
            last.nextE = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> x;
        int i;
        if (index < this.size >> 1) {
            x = this.first;

            for (i = 0; i < index; ++i) {
                x = x.nextE;
            }
        } else {
            x = this.last;

            for (i = this.size - 1; i > index; --i) {
                x = x.prevE;
            }
        }
        return x;
    }

    private void addInside(T value, Node<T> nodeBefore) {
        Node<T> pred = nodeBefore.prevE;
        Node<T> newNode = new Node<T>(pred, value, nodeBefore);
        nodeBefore.prevE = newNode;
        if (pred == null) {
            this.first = newNode;
        } else {
            pred.nextE = newNode;
        }
        ++this.size;
    }

    private class Node<T> {
        private T currE;
        private Node<T> prevE;
        private Node<T> nextE;

        private Node(Node<T> prev, T curr, Node<T> next) {
            currE = curr;
            prevE = prev;
            nextE = next;
        }
    }
}
