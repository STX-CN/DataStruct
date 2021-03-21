package com.stx.java.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable{

    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认数组大小
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 空数组
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 默认空元素数组
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存放元素的数组
     * 该属性不能进行序列化操作
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * ArrayList内元素的个数
     * @serial
     */
    private int size;

    /**
     * 扩容最大尺寸
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 构造函数
     * 根据传入长度，初始化数组
     * @param initialCapacity
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            int elementSize = initialCapacity == 0 ? DEFAULT_CAPACITY : initialCapacity;
            this.elementData = new Object[elementSize];
        }else{
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /**
     * 默认构造函数，数组长度为空
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 在新增数组元素，确保数组元素是足够的
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                ? 0
                : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; i ++){
            action.accept((E)elementData[i]);
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
    }

    @Override
    public void sort(Comparator<? super E> c) {
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * 根据元素值查找索引
     * @param o
     * @return
     * 大于0，表示查找到；-1，表示没有找到
     */
    public int indexOf(Object o) {
        for (int index = 0; index < size; index ++){
            if (o == elementData[index]){
                return index;
            }
        }
        return -1;
    }

    /**
     * 根据元素值查找在数组中最后出现的index值
     * @param o
     * @return
     * 大于0，表示查找到；-1，表示没有找到
     */
    public int lastIndexOf(Object o) {
        for (int index = size-1; index >=0; index--){
            if (o == elementData[index]){
                return index;
            }
        }
        return -1;
    }

    public Object clone() {
        try {
            ArrayList<?> newArrayList = (ArrayList<?>)super.clone();
            newArrayList.elementData = Arrays.copyOf(elementData, size);
            return newArrayList;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] toArray(){
        if (size == 0){
            return null;
        }
        return Arrays.copyOf(elementData, size);
    }

    public <T> T[] toArray(T[] a){
        if (elementData.length <= a.length){
            System.arraycopy(elementData, 0, a, 0, elementData.length);
        }else{
            a = (T[]) Arrays.copyOf(elementData, elementData.length);
            a[size] = null;
        }
        return a;
    }

    E elementData(int index){
        return (E)elementData(index);
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    public E set(int index, E element) {
        if (!(index>=0 && index<size)){
            throw new IllegalArgumentException("IllegalArgument index: " + index);
        }
        E oldValue = (E)elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(E e) {
        ensureCapacityInternal(size+1);
        elementData[size++] = e;
        return true;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size+1);
        System.arraycopy(elementData, index, elementData, index+1, size - index);
        elementData[index] = element;
        size++;
    }

    public E remove(int index){
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        //计算需要移动元素的个数
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData,index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    public boolean remove(Object o){
        int index = indexOf(o);
        if (index>=0 && index < size){
            fastRemove(index);
            return true;
        }
        return false;
    }

    private void fastRemove(int index) {
        rangeCheck(index);
        int numMoved = size - index - 1;
        if (numMoved > 0){
            System.arraycopy(elementData, index+1, elementData,index, numMoved);
        }
        elementData[--size] = null;
    }

    public void clear() {
        for (int i = 0; i < size; i++){
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * 将一个集合添加到尾部
     * @param c
     * @return
     */
    public boolean addAll(Collection<? extends E> c) {
        Object[] a =  c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size+numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        Object[] a =  c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size+numNew);
        int numMoved = size-index-1;
        if (numMoved > 0){
            System.arraycopy(elementData, index, elementData, index+numNew, numMoved);
        }
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }


    /**
     * 动态扩容
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + oldCapacity << 1;
        if (newCapacity < minCapacity){
            newCapacity = minCapacity;
        }
        if (newCapacity > MAX_ARRAY_SIZE){
            newCapacity = hugeCapacity(minCapacity);
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        return Math.max(elementData.length, minCapacity);
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));

    }

    private void ensureExplicitCapacity(int minCapacity){
        if (minCapacity - elementData.length > 0){
            grow(minCapacity);
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    /**
     * 检查数组范围是否越界
     * index 大于size, 抛出越界异常
     * @param index
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 检查数组是否越界
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 越界提示
     * @param index
     * @return
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
}
