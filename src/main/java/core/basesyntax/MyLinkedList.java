package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyNode<T> first;
    private MyNode<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private static class MyNode<E> {
        MyNode<E> prev;
        E item;
        MyNode<E> next;

        public MyNode(MyNode<E> prev, E item, MyNode<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong number of index");
        }
    }

    private MyNode<T> searchNodeByIndex(int index) {
        MyNode<T> currentNode;
        if (index > size / 2) {
            currentNode = last;
            for (int i = 1; i < size - index; i++) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void addToBegining(T value) {
        MyNode<T> currentNode;
        if (size == 0) {
            currentNode = new MyNode<T>(null, value, null);
            last = currentNode;
        } else {
            currentNode = new MyNode<>(null, value, first);
            first.prev = currentNode;

        }
        first = currentNode;
        size++;
    }

    private void removeNode(MyNode<T> currentNode) {
        if (currentNode.prev == null && currentNode.next == null) {
            size--;
            first = null;
            last = null;
            return;
        }
        if (currentNode.prev == null) {
            currentNode.next.prev = null;
            first = currentNode.next;
            size--;
            return;
        }
        if (currentNode.next == null) {
            currentNode.prev.next = null;
            last = currentNode.prev;
            size--;
            return;
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
    }

    @Override
    public boolean add(T value) {
        MyNode<T> currentNode;
        if (size == 0) {
            currentNode = new MyNode<T>(null, value, null);
            first = currentNode;
        } else {
            currentNode = new MyNode<>(last, value, null);
            last.next = currentNode;
        }
        last = currentNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong number of index");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addToBegining(value);
            return;
        }
        MyNode<T> memNode = searchNodeByIndex(index);
        MyNode<T> currentNode = new MyNode<>(memNode.prev, value, memNode);
        memNode.prev.next = currentNode;
        memNode.prev = currentNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        MyNode<T> currentNode = searchNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyNode<T> currentNode = searchNodeByIndex(index);
        T memItem = currentNode.item;
        currentNode.item = value;
        return memItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        MyNode<T> currentNode = searchNodeByIndex(index);
        T value = currentNode.item;
        removeNode(currentNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.item == null && object == null || currentNode.item.equals(object)) {
                removeNode(currentNode);
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
}
