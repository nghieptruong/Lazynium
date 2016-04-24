package com.malski.core.web.elements;

import com.malski.core.cucumber.TestContext;
import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.factory.ElementHandler;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.factory.LazyLocatorImpl;
import org.openqa.selenium.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ElementsImpl<E extends Element> implements Elements<E> {
    private LazyLocator locator;
    private List<E> elements;
    private Class<E> elementInterface;

    public ElementsImpl(LazyLocator locator, final Class<E> elementInterface) {
        this.locator = locator;
        this.elementInterface = elementInterface;
    }

    public ElementsImpl(By by, SearchContext context, final Class<E> elementInterface) {
        this.locator = new LazyLocatorImpl(context, by);
        this.elementInterface = elementInterface;
    }

    @Override
    public LazyLocator getLocator() {
        return this.locator;
    }

    @Override
    public void refresh() {
        elements = new ArrayList<>();
        List<WebElement> webElements = getLocator().findElements();
        ElementHandler handler = new ElementHandler(elementInterface, getLocator());
        try {
            webElements.forEach(webElement -> {
                try {
                    E element = handler.getElementImplementation(webElement);
                    getWrappedElements().add(element);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return getWrappedElements().size();
    }

    @Override
    public boolean isEmpty() {
        return getWrappedElements().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getWrappedElements().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return getWrappedElements().iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        getWrappedElements().forEach(action);
    }

    @Override
    public Object[] toArray() {
        return getWrappedElements().toArray();
    }

    @Override
    public <W> W[] toArray(W[] a) {
        return getWrappedElements().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return getWrappedElements().add(e);
    }

    @Override
    public boolean remove(Object o) {
        return getWrappedElements().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getWrappedElements().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return getWrappedElements().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return getWrappedElements().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getWrappedElements().removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return getWrappedElements().removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getWrappedElements().retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        getWrappedElements().replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        getWrappedElements().sort(c);
    }

    @Override
    public void clear() {
        getWrappedElements().clear();
    }

    @Override
    public E get(int index) {
        return getWrappedElements().get(index);
    }

    @Override
    public E set(int index, E element) {
        return getWrappedElements().set(index, element);
    }

    @Override
    public void add(int index, E element) {
        getWrappedElements().add(index, element);
    }

    @Override
    public E remove(int index) {
        return getWrappedElements().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return getWrappedElements().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getWrappedElements().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return getWrappedElements().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return getWrappedElements().listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return getWrappedElements().subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<E> spliterator() {
        return getWrappedElements().spliterator();
    }

    @Override
    public Stream<E> stream() {
        return getWrappedElements().stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return getWrappedElements().parallelStream();
    }

    public List<String> getTexts() {
        return getWrappedElements().stream().map(Element::getText).collect(Collectors.toList());
    }

    public List<String> getValues() {
        return elements.stream().map(Element::getValue).collect(Collectors.toList());
    }

    public List<String> getIds() {
        return elements.stream().map(Element::getId).collect(Collectors.toList());
    }

    public List<E> getWrappedElements() {
        TestContext.getBrowser().waitForPageToLoad();
        if (elements == null) {
            refresh();
        }
        return elements;
    }

    @Override
    public void waitUntilAllPresent() {
        TestContext.getBrowser().getWait().until(presenceOfAllElementsLocatedBy(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyPresent() {
        TestContext.getBrowser().getWait().until(presenceOfElementLocated(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAllVisible() {
        TestContext.getBrowser().getWait().until(visibilityOfAllElementsLocatedBy(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyVisible() {
        TestContext.getBrowser().getWait().until(visibilityOfElementLocated(getLocator().getSelector().getBy()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void waitUntilAllDisappear() {
        TestContext.getBrowser().getWait().until(WaitConditions.invisibilityOfAllElements(getWrappedElements()));
    }

    @Override
    public void waitUntilAnyDisappear() {
        TestContext.getBrowser().getWait().until(invisibilityOfElementLocated(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAllEnabled() {
        TestContext.getBrowser().getWait().until(WaitConditions.elementsToBeClickable(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAnyEnabled() {
        TestContext.getBrowser().getWait().until(elementToBeClickable(getLocator().getSelector().getBy()));
    }

    @Override
    public void waitUntilAllDisabled() {
        TestContext.getBrowser().getWait().until(not(WaitConditions.elementsToBeClickable(getLocator().getSelector().getBy())));
    }

    @Override
    public void waitUntilAnyDisabled() {
        TestContext.getBrowser().getWait().until(not(elementToBeClickable(getLocator().getSelector().getBy())));
    }

//    @Override
//    public void waitUntilAllSelected() {
//        TestContext.getBrowser().getWait().until(elementToBeSelected(this));
//    }
//
//    @Override
//    public void waitUntilAnySelected() {
//        TestContext.getBrowser().getWait().until(elementToBeSelected(this));
//    }
//
//    @Override
//    public void waitUntilAllUnselected() {
//        TestContext.getBrowser().getWait().until(elementSelectionStateToBe(this, false));
//    }
//
//    @Override
//    public void waitUntilAnyUnselected() {
//        TestContext.getBrowser().getWait().until(elementSelectionStateToBe(this, false));
//    }

    @Override
    public boolean areAllVisible() {
        for (E element : getWrappedElements()) {
            if (!element.isVisible()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyVisible() {
        for (E element : getWrappedElements()) {
            if (element.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllPresent() {
        for (E element : getWrappedElements()) {
            if (!element.isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyPresent() {
        for (E element : getWrappedElements()) {
            if (element.isPresent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllEnabled() {
        for (E element : getWrappedElements()) {
            if (!element.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyEnabled() {
        for (E element : getWrappedElements()) {
            if (element.isEnabled()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean hasAnyFocus() {
        for (E element : getWrappedElements()) {
            if (element.hasFocus()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areAllSelected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnySelected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean areAllUnselected() {
        for (E element : getWrappedElements()) {
            if (element.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAnyUnselected() {
        for (E element : getWrappedElements()) {
            if (!element.isSelected()) {
                return true;
            }
        }
        return true;
    }
}