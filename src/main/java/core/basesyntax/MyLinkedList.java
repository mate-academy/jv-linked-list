package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T>[] linkedList;
    private int defaultCapacity = 1;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    public MyLinkedList() {
        linkedList = new Node[defaultCapacity];
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            linkedList[0] = (Node<T>) new Node<>(null, value, null);
        }
        if (size > 0) {
            linkedList[size] = (Node<T>) new Node<>(linkedList[size - 1], value, null);
            linkedList[size - 1].next = linkedList[size];
        }
        size++;
        updateCapacity();
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        if (index >= 0 && size >= index) {
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0 && size == 0) {
                linkedList[index] = newNode;
            }
            if (index < size) {
                Node<T>[] arrayCopy = linkedList;
                linkedList = new Node[defaultCapacity];
                if (index == 0) {
                    for (int i = index + 1; i <= size; i++) {
                        linkedList[i] = arrayCopy[i - 1];
                    }
                }
                if (index > 0) {
                    for (int i = 0; i < index; i++) {
                        linkedList[i] = arrayCopy[i];
                    }
                    for (int i = index + 1; i <= size; i++) {
                        linkedList[i] = arrayCopy[i - 1];
                    }
                }
                linkedList[index] = newNode;
                newNode.next = linkedList[index + 1];
                newNode = linkedList[index + 1].prev;
                if (index != 0) {
                    newNode.prev = linkedList[index - 1];
                    linkedList[index - 1].next = newNode;
                }
            }
            if (index == size && size > 0) {
                linkedList[index] = newNode;
                newNode.prev = linkedList[index - 1];
                linkedList[index - 1].next = newNode;
            }
        }
        size++;
        updateCapacity();
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            linkedList[size] = new Node<>(linkedList[size - 1], list.get(i), null);
            linkedList[size - 1].next = linkedList[size];
            size++;
            updateCapacity();
        }
    }

    @Override
    public T get(int index) {
        if (size > index && index >= 0) {
            return linkedList[index].item;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public T set(T value, int index) {
        if (size > index && index >= 0) {
            final T replacedElement = get(index);
            Node<T> newNode = new Node<>(null, value, null);
            if (index != 0) {
                linkedList[index - 1].next = newNode;
                newNode.prev = linkedList[index].prev;
            }
            if (index != size - 1) {
                linkedList[index + 1].prev = newNode;
                newNode.next = linkedList[index].next;
            }
            linkedList[index] = newNode;
            return replacedElement;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public T remove(int index) {
        if (size > index && index >= 0) {
            T removedElement = get(index);
            if (index < size - 1) {
                Node<T>[] arrayCopy = linkedList;
                linkedList = new Node[defaultCapacity];
                for (int i = 0; i < index; i++) {
                    linkedList[i] = arrayCopy[i];
                }
                for (int j = index; j < size; j++) {
                    linkedList[j] = arrayCopy[j + 1];
                }
            }
            size--;
            return removedElement;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (linkedList[i].item == null && object == null
                    || object != null && object.equals(linkedList[i].item)) {
                object = linkedList[i].item;
                if (i < size - 1) {
                    Node<T>[] arrayCopy = linkedList;
                    linkedList = new Node[defaultCapacity];
                    for (int k = 0; k < i; k++) {
                        linkedList[k] = arrayCopy[k];
                    }
                    for (int j = i; j < size; j++) {
                        linkedList[j] = arrayCopy[j + 1];
                    }
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private void updateCapacity() {
        defaultCapacity = defaultCapacity + 1;
        Node<T>[] arrayLinkedListCopy = linkedList;
        linkedList = new Node[defaultCapacity];
        for (int i = 0; i < defaultCapacity - 1; i++) {
            linkedList[i] = arrayLinkedListCopy[i];
        }
    }
}
