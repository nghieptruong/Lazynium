package com.malski.core.web.elements;

public interface ElementWait {

    void waitUntilPresent();

    void waitUntilPresent(long timeout);

    void waitUntilVisible();

    void waitUntilVisible(long timeout);

    void waitUntilDisappear();

    void waitUntilDisappear(long timeout);

    void waitUntilEnabled();

    void waitUntilEnabled(long timeout);

    void waitUntilDisabled();

    void waitUntilDisabled(long timeout);
}