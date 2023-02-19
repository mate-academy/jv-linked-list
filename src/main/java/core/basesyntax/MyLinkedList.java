package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        addLastItem(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirstItem(value);
        } else if (index == size) {
            addLastItem(value);
        } else {
            MyLinkedList.Node<T> findNodeAfterToAdd = findNodeByIndex(index).prev;
            MyLinkedList.Node<T> nextNode = findNodeAfterToAdd.next;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(findNodeAfterToAdd,
                    value, findNodeAfterToAdd.next);
            findNodeAfterToAdd.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            addLastItem(item);
        }
    }

    @Override
    public T get(int index) {
        MyLinkedList.Node<T> findNodeToGet = findNodeByIndex(index);;
        return findNodeToGet.item;
    }

    @Override
    public T set(T value, int index) {
        MyLinkedList.Node<T> findNodeToSet = findNodeByIndex(index);;
        T oldValue = findNodeToSet.item;
        findNodeToSet.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        MyLinkedList.Node<T> nodeToRemove = findNodeByIndex(index);
        return unlink(nodeToRemove, index);
    }

    @Override
    public boolean remove(T object) {
        MyLinkedList.Node<T> findNode = first;
        for (int i = 0; i < size; i++) {
            if (findNode.item == object || findNode.item != null && findNode.item.equals(object)) {
                unlink(findNode, i);
                return true;
            }
            findNode = findNode.next;
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

    private void addFirstItem(T value) {
        if (isEmpty()) {
            addNodeToEmptyArray(value);
        } else {
            final MyLinkedList.Node<T> l = first;
            final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, value, first);
            if (l == null) {
                last = newNode;
            } else {
                l.prev = newNode;
            }
            first = newNode;
            size++;
        }
    }

    private void addLastItem(T value) {
        if (isEmpty()) {
            addNodeToEmptyArray(value);
        } else {
            final MyLinkedList.Node<T> l = last;
            final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(l, value, null);
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            last = newNode;
            size++;
        }
    }

    private T removeFirstNode() {
        T oldItem = first.item;
        if (size > 1) {
            first.next.prev = null;
            first = first.next;
        } else {
            first = null;
        }
        size--;
        return oldItem;
    }

    private T removeLastNode() {
        final T oldItem = last.item;
        last.prev.next = null;
        last = last.prev;
        size--;
        return oldItem;
    }

    private MyLinkedList.Node<T> findNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index * 2 < size) {
                MyLinkedList.Node<T> findNode = first;
                for (int i = 0; i < index; i++) {
                    findNode = findNode.next;
                }
                return findNode;
            } else {
                MyLinkedList.Node<T> findNode = last;
                for (int i = size - 1; i > index; i--) {
                    findNode = findNode.prev;
                }
                return findNode;
            }
        }
    }

    private void addNodeToEmptyArray(T value) {
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, value, null);
        first = newNode;
        last = newNode;
        size++;
    }

    private T unlink(Node<T> nodeToUnlink, int index) {
        if (index == 0) {
            return removeFirstNode();
        } else if (index == size - 1) {
            return removeLastNode();
        } else {
            final T oldItem = nodeToUnlink.item;
            nodeToUnlink.next.prev = nodeToUnlink.prev;
            nodeToUnlink.prev.next = nodeToUnlink.next;
            size--;
            return oldItem;
        }
    }

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

