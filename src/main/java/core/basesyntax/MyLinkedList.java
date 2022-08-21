package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tailNode;
    private Node<T> headNode;
    private Node<T> currentNode;
    private int currentNodeIndex;

    private Node<T> newNode;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node newNode = new Node(null, value, null);
            headNode = newNode;
            tailNode = newNode;
        } else {
            Node newNode = new Node(tailNode, value, null);
            tailNode.next = newNode;
            tailNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        goToNodeByIndex(index);
        if (index == 0) {
            newNode = new Node(null, value, currentNode);
            size++;
            headNode = newNode;
            return;
        }
        newNode = new Node(currentNode.prev, value, currentNode);
        size++;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    @Override
    public void addAll(List<T> myList) {
        if (!myList.isEmpty()) {
            for (T itemFromList:myList) {
                add(itemFromList);
            }
        }
    }

    @Override
    public T get(int index) {
        goToNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        goToNodeByIndex(index);
        Node<T> oldNode = new Node(null, currentNode.value,null);
        currentNode.value = value;
        return oldNode.value;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfSize(index);
        Node<T> oldNode;
        if (index == 0 && size > 1) {
            oldNode = new Node(null, headNode.value,null);
            headNode.next.prev = null;
            headNode = headNode.next;
        } else if (index == 0 && size == 1) {
            oldNode = new Node(null, headNode.value,null);
            headNode = null;
            tailNode = null;
        } else if (index == size - 1) {
            oldNode = new Node(null, tailNode.value,null);
            tailNode.prev.next = null;
            tailNode = tailNode.prev;
        } else {
            goToNodeByIndex(index);
            oldNode = new Node(null, currentNode.value, null);
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            goToNodeByIndex(i);
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                remove(i);
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
        return size == 0;
    }

    private void checkIndexOutOfSize(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void goToNodeByIndex(int index) {
        checkIndexOutOfSize(index);
        currentNodeIndex = 0;
        currentNode = headNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
            currentNodeIndex++;
        }
    }
}
