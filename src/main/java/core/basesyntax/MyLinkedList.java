package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> nextNode;
        private Node<T> previousNode;

        public Node(Node<T> previousNode, T value, Node<T> nextNode) {
            this.nextNode = nextNode;
            this.value = value;
            this.previousNode = previousNode;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, null, null);
            tail.nextNode = newNode;
            tail = newNode;
            newNode.value = value;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdd(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> insertingNode = new Node<>(null, value, head);
            head = insertingNode;
            size++;
        } else {
            Node<T> insertingNode;
            Node newNode = head;
            for (int i = 0; i < index - 1; i++) {
                newNode = newNode.nextNode;
            }
            Node afteradditingNode = newNode.nextNode;

            insertingNode = new Node<>(newNode, value, newNode.nextNode);
            newNode.nextNode = insertingNode;
            insertingNode.previousNode = newNode;
            afteradditingNode.previousNode = insertingNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int j = 0; j < list.size(); j++) {
            add((T) list.get(j));
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node getteadNote=whatHalf(index);
        return (T) getteadNote.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode=whatHalf(index);
        T valueReplacing = (T) currentNode.value;
        currentNode.value = value;
        return valueReplacing;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode;
        T removedValue = null;
        if (size / 2 - 1 < index) {
            currentNode = new Node<>(tail, null, null);
            for (int i = size - 1; i >= index; i--) {
                currentNode = currentNode.previousNode;
            }
            removedValue = (T) currentNode.value;
         //   Node<T> temp = currentNode.previousNode;//was Node<T> temp = currentNode.previousNode;
            currentNode.nextNode = currentNode.previousNode;//was currentNode.nextNode = temp;

        } else {
            currentNode = new Node<>(null, null, head);
            for (int i = 0; i <= index; i++) {
                currentNode = currentNode.nextNode;
            }
            removedValue = (T) currentNode.value;
            Node<T> temp = currentNode.nextNode;//was Node<T> temp = currentNode.nextNode;
            if (index == 0) {
                head = currentNode.nextNode;
            }
            currentNode.previousNode = currentNode.nextNode;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = new Node<>(null, null, head);
        for (int i = 0; i < size; i++) {
            currentNode = currentNode.nextNode;
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                Node<T> afterDeleteNode = currentNode.nextNode;
                Node<T> beforeDeleteNode = currentNode.previousNode;
                if (currentNode.equals(head) && size > 1) {
                    head = currentNode.nextNode;
                    currentNode.nextNode.previousNode = null;
                    size--;
                    return true;
                } else if (currentNode.equals(head) && size == 1) {
                    size--;
                    return true;
                } else {
                    afterDeleteNode.previousNode = beforeDeleteNode;
                    beforeDeleteNode.nextNode = afterDeleteNode;
                    size--;
                    return true;
                }
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

    private void indexCheck(int index) {
        if (size <= index || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index is out of bound");
        }
    }

    private void indexCheckForAdd(int index) {
        if (size < index || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index is out of bound");
        }
    }

    private Node whatHalf(int index){
        Node currentNode = null;
        if(size/2-1 <index){
            currentNode = new Node<>(tail, null, null);
            for (int i = size - 1; i >= index; i--) {
                currentNode = currentNode.previousNode;
            }
        }
        else{
            currentNode = new Node<>(null, null, head);
            for (int i = 0; i <= index; i++) {
                currentNode = currentNode.nextNode;
            }
        }
        return currentNode;
        }


    }
