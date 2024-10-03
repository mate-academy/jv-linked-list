package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private T[] arr;
    private T[] newArr;
    private int size = 0;

    public MyLinkedList() {
        arr = (T[]) new Object[1];
    }

    @Override
    public void add(T value) {
        if (size == arr.length) {
            resize();
        }
        arr[size] = value;
        size++;
    }

    @Override
    public void add(T value, int index) {
        newArr = (T[]) new Object[arr.length + 1];
        if (size == arr.length) {
            resize();
        }
        check(index);
        System.arraycopy(arr, 0, newArr, 0, index);
        newArr[index] = value;
        System.arraycopy(arr, index, newArr, index + 1, size - index);
        System.arraycopy(newArr, 0, arr, 0, newArr.length);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        check(index);
        return arr[index];
    }

    @Override
    public T set(T value, int index) {
        check(index);
        arr[index] = value;
        return value;
    }

    @Override
    public T remove(int index) {
        T result = null;
        check(index);
        result = arr[index];
        newArr = (T[]) new Object[arr.length];
        System.arraycopy(arr, 0, newArr, 0, index);
        System.arraycopy(arr, index + 1, newArr, index, arr.length - index - 1);
        System.arraycopy(newArr, 0, arr, 0, arr.length);
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) throws NoSuchElementException {
        boolean isRemoved = false;
        for (int i = 0; i < size; i++) {
            if (arr[i] == object || (arr[i] != null && arr[i].equals(object))) {
                isRemoved = true;
                remove(i);
                return isRemoved;
            }
        }
        throw new NoSuchElementException("Element " + object + " was not found");
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

    public void resize() {
        int size = arr.length + 1; // or indexOfLastCharacter >> 1
        T[] newArr = (T[]) new Object[size];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = (T[]) new Object[size];
        System.arraycopy(newArr, 0, arr, 0, size);
    }

    public void check(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
}
