package com.malski.core.web.factory;

import com.malski.core.web.Browser;
import com.malski.core.web.page.Page;
import com.malski.core.web.page.WebView;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LazyPageFactory extends org.openqa.selenium.support.PageFactory {

    public static <T extends Page> T initElements(Browser browser, Class<T> pageClassToProxy) {
        T page = instantiatePage(browser, pageClassToProxy);
        initElements(browser, page);
        return page;
    }

//    public static <T extends WebModuleImpl> T initElements(Browser browser, Class<T> moduleClassToProxy, Element rootElement) {
//        T module = instantiateModule(browser, moduleClassToProxy, rootElement);
//        initElements(browser, module);
//        return module;
//    }

    public static <T extends WebView> void initElements(WebDriver driver, T webComponent) {
        initElements(new LazyLocatorFactory(driver), webComponent);
    }

    public static <T extends WebView> T initElements(SearchContext searchContext, T webComponent) {
        initElements(new ElementDecorator(new LazyLocatorFactory(searchContext)), webComponent);
        return webComponent;
    }

    public static <T extends WebView> void initElements(final ElementLocatorFactory factory, T webComponent) {
        initElements(new ElementDecorator(factory), webComponent);
    }

    public static <T extends WebView> void initElements(FieldDecorator decorator, T webComponent) {
//        for(Class proxyIn = webComponent.getClass(); proxyIn != Object.class; proxyIn = proxyIn.getSuperclass()) {
//            proxyFields(decorator, webComponent, proxyIn);
//        }
        PageFactory.initElements(decorator, webComponent);
    }

    private static <T extends Page> T instantiatePage(Browser browser, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(Browser.class);
                return constructor.newInstance(browser);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

//    private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
//        for(Field field : proxyIn.getDeclaredFields()) {
//            Object value = decorator.decorate(page.getClass().getClassLoader(), field);
//            if(value != null) {
//                try {
//                    field.setAccessible(true);
//                    field.set(page, value);
//                } catch (IllegalAccessException var10) {
//                    throw new RuntimeException(var10);
//                }
//            }
//        }
//    }
}