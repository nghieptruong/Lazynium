package com.malski.core.web.control;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.factory.*;
import com.malski.core.web.view.Frame;
import com.malski.core.web.view.Module;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface LazySearchContext extends SearchContext {

    SearchContext getContext();

    default Element getElement(By by) {
        return getElement(by, Element.class);
    }

    default <T extends Element> T getElement(By by, Class<T> clazz) {
        return new ElementHandler<>(clazz, by, this).getImplementation();
    }

    default Element getElement(LazyLocator locator) {
        return getElement(locator, Element.class);
    }

    default <T extends Element> T getElement(LazyLocator locator, Class<T> clazz) {
        return new ElementHandler<>(clazz, locator).getImplementation();
    }

    default Element getElement(Selector selector) {
        return getElement(selector, Element.class);
    }

    default <T extends Element> T getElement(Selector selector, Class<T> clazz) {
        return new ElementHandler<>(clazz, selector, this).getImplementation();
    }

    default Element $(String css) {
        return getElement(By.cssSelector(css));
    }

    default <T extends Element> T $(String css, Class<T> clazz) {
        return getElement(By.cssSelector(css), clazz);
    }

    default Element $i(String id) {
        return getElement(By.id(id));
    }

    default <T extends Element> T $i(String id, Class<T> clazz) {
        return getElement(By.id(id), clazz);
    }

    default Element $n(String name) {
        return getElement(By.name(name));
    }

    default <T extends Element> T $n(String name, Class<T> clazz) {
        return getElement(By.name(name), clazz);
    }

    default Element $x(String xpath) {
        return getElement(By.xpath(xpath));
    }

    default <T extends Element> T $x(String xpath, Class<T> clazz) {
        return getElement(By.xpath(xpath), clazz);
    }

    default Element $t(String tagName) {
        return getElement(By.tagName(tagName));
    }

    default <T extends Element> T $t(String tagName, Class<T> clazz) {
        return getElement(By.tagName(tagName), clazz);
    }

    default Element $c(String className) {
        return getElement(By.className(className));
    }

    default <T extends Element> T $c(String className, Class<T> clazz) {
        return getElement(By.className(className), clazz);
    }

    default <T extends Element> Elements<T> getEmptyElementsList() {
        return new Elements<>();
    }

    default Elements<Element> getElements(By by) {
        return getElements(by, Element.class);
    }

    default <T extends Element> Elements<T> getElements(By by, Class<T> clazz) {
        return new ElementListHandler<>(clazz, by, this).getImplementation();
    }

    default Elements<Element> getElements(LazyLocator locator) {
        return getElements(locator, Element.class);
    }

    default <T extends Element> Elements<T> getElements(LazyLocator locator, Class<T> clazz) {
        return new ElementListHandler<>(clazz, locator).getImplementation();
    }

    default Elements<Element> getElements(Selector selector) {
        return getElements(selector, Element.class);
    }

    default <T extends Element> Elements<T> getElements(Selector selector, Class<T> clazz) {
        return new ElementListHandler<>(clazz, selector, this).getImplementation();
    }

    default Elements<Element> $$(String css) {
        return getElements(By.cssSelector(css));
    }

    default <T extends Element> Elements<T> $$(String css, Class<T> clazz) {
        return getElements(By.cssSelector(css), clazz);
    }

    default Elements<Element> $$i(String id) {
        return getElements(By.id(id));
    }

    default <T extends Element> Elements<T> $$i(String id, Class<T> clazz) {
        return getElements(By.id(id), clazz);
    }

    default Elements<Element> $$n(String name) {
        return getElements(By.name(name));
    }

    default <T extends Element> Elements<T> $$n(String name, Class<T> clazz) {
        return getElements(By.name(name), clazz);
    }

    default Elements<Element> $$x(String xpath) {
        return getElements(By.xpath(xpath));
    }

    default <T extends Element> Elements<T> $$x(String xpath, Class<T> clazz) {
        return getElements(By.xpath(xpath), clazz);
    }

    default Elements<Element> $$t(String tagName) {
        return getElements(By.tagName(tagName));
    }

    default <T extends Element> Elements<T> $$t(String tagName, Class<T> clazz) {
        return getElements(By.tagName(tagName), clazz);
    }

    default Elements<Element> $$c(String className) {
        return getElements(By.className(className));
    }

    default <T extends Element> Elements<T> $$c(String className, Class<T> clazz) {
        return getElements(By.className(className), clazz);
    }

    @Override
    default List<WebElement> findElements(By by) {
        try {
            return getContext().findElements(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getContext().findElements(by);
        }
    }

    @Override
    default WebElement findElement(By by) {
        try {
            return getContext().findElement(by);
        } catch (StaleElementReferenceException ignore) {
            refresh();
            return getContext().findElement(by);
        }
    }

    default <T extends Module> T getModule(Class<T> iface) {
        return getModule(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    default <T extends Module> T getModule(Class<T> iface, By by) {
        return getModule(iface, new LazyLocator(this, by));
    }

    default <T extends Module> T getModule(Class<T> iface, Selector selector) {
        return getModule(iface, new LazyLocator(this, selector));
    }

    default <T extends Module> T getModule(Class<T> iface, LazyLocator locator) {
        ModuleHandler<T> handler = new ModuleHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    default <T extends Frame> T getFrame(Class<T> iface) {
        return getFrame(iface, new LazyLocator(this, new LazyAnnotations(iface)));
    }

    default <T extends Frame> T getFrame(Class<T> iface, By by) {
        return getFrame(iface, new LazyLocator(this, by));
    }

    default <T extends Frame> T getFrame(Class<T> iface, Selector selector) {
        return getFrame(iface, new LazyLocator(this, selector));
    }

    default <T extends Frame> T getFrame(Class<T> iface, LazyLocator locator) {
        FrameHandler<T> handler = new FrameHandler<>(iface, locator);
        try {
            return handler.getImplementation();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    boolean refresh();
}
