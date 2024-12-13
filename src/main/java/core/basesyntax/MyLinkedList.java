package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T element) {
            this.item = element;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> elementToAdd = new Node<>(value);

        if (size == 0) {
            head = elementToAdd;
            tail = elementToAdd;
        } else {
            tail.next = elementToAdd;
            elementToAdd.prev = tail;
            tail = elementToAdd;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }

        Node<T> elementToAdd = new Node<>(value);

        if (size == 0) {
            head = elementToAdd;
            tail = elementToAdd;
        } else if (size - 1 == index) {
            Node<T> elPrevTail = tail.prev;
            elPrevTail.next = elementToAdd;
            elementToAdd.prev = elPrevTail;

            tail.prev = elementToAdd;
            elementToAdd.next = tail;
        } else if (size == index) {
            tail.next = elementToAdd;
            elementToAdd.prev = tail;
            tail = elementToAdd;
        } else if (index == 0 && size != 0) {
            Node<T> currentElement = head;
            head = elementToAdd;
            elementToAdd.next = currentElement;
            currentElement.prev = elementToAdd;
        } else {
            int countOfIndex = 0;
            Node<T> element = head;

            while (countOfIndex != index) {
                element = element.next;
                countOfIndex++;
            }

            Node<T> elPrevElement = element.prev;
            elPrevElement.next = elementToAdd;
            elementToAdd.prev = elPrevElement;

            elementToAdd.next = element;
            element.prev = elementToAdd;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (T element : list) {
                Node<T> newElement = new Node<>(element);
                tail.next = newElement;
                newElement.prev = tail;
                tail = newElement;
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }

        if (size == 1) {
            return head.item;
        } else {
            int countOfIndex = 0;
            Node<T> elementToReturn = head;

            while (countOfIndex != index) {
                elementToReturn = elementToReturn.next;
                countOfIndex++;
            }

            return elementToReturn.item;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }

        Node<T> elementToSet = new Node<>(value);
        Node<T> elementToReturn = null;

        if (size == 1) {
            elementToReturn = head;
            head = elementToSet;
            tail = elementToSet;
        } else if (index == size - 1) {
            elementToReturn = tail;
            Node<T> elPrevTail = tail.prev;
            elPrevTail.next = elementToSet;
            elementToSet.prev = elPrevTail;
            tail = elementToSet;
        } else if (index == 0) {
            elementToReturn = head;
            Node<T> elNextHead = head.next;
            elNextHead.prev = elementToSet;
            elementToSet.next = elNextHead;
            head = elementToSet;
        } else {
            int countOfIndex = 0;
            Node<T> element = head;

            while (countOfIndex != index) {
                element = element.next;
                countOfIndex++;
            }

            elementToReturn = element;

            Node<T> elPrevElement = elementToReturn.prev;
            Node<T> elNextElement = elementToReturn.next;

            elPrevElement.next = elementToSet;
            elementToSet.prev = elPrevElement;

            elNextElement.prev = elementToSet;
            elementToSet.next = elNextElement;
        }

        return elementToReturn.item;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }

        Node<T> elementToRemove = null;

        if (size == 1) {
            elementToRemove = head;
            head = null;
            tail = null;
        } else if (index == size - 1) {
            elementToRemove = tail;
            Node<T> elPrevElementToRemove = tail.prev;
            elPrevElementToRemove.next = null;
            tail = elPrevElementToRemove;
        } else if (index == 0) {
            elementToRemove = head;
            Node<T> elNextElementToRemove = head.next;
            elNextElementToRemove.prev = null;
            head = elNextElementToRemove;
        } else {
            int countOfIndex = 0;
            Node<T> element = head;

            while (countOfIndex != index) {
                element = element.next;
                countOfIndex++;
            }

            elementToRemove = element;
            Node<T> elPrevElementToRemove = elementToRemove.prev;
            Node<T> elNextElementToRemove = elementToRemove.next;

            elPrevElementToRemove.next = null;
            elNextElementToRemove.prev = null;

            elPrevElementToRemove.next = elNextElementToRemove;
            elNextElementToRemove.prev = elPrevElementToRemove;
        }

        size--;
        return elementToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        boolean isExist = false;

        if (size == 0) {
            isExist = false;
        } else if (size == 1 && object.equals(head.item)) {
            head = null;
            tail = null;
            isExist = true;
        } else if (size > 1) {
            int countOfIndex = 0;
            Node<T> element = head;

            while (countOfIndex != size) {
                if (element.item == null ? object == null : element.item.equals(object)) {
                    isExist = true;
                    break;
                }
                element = element.next;
                countOfIndex++;
            }

            if (isExist) {
                if (countOfIndex == size - 1) {
                    Node<T> elPrevElement = element.prev;
                    elPrevElement.next = null;
                    tail = elPrevElement;
                } else if (countOfIndex == 0) {
                    Node<T> elNextElement = element.next;
                    elNextElement.prev = null;
                    head = elNextElement;
                } else {
                    Node<T> elPrevElement = element.prev;
                    Node<T> elNextElement = element.next;

                    elPrevElement.next = null;
                    elPrevElement.next = elNextElement;

                    elNextElement.prev = null;
                    elNextElement.prev = elPrevElement;
                }
            }
        }

        if (isExist) {
            size--;
            return true;
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
