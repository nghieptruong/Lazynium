package com.malski.google.web.modules;

import com.malski.core.web.page.WebModule;
import org.openqa.selenium.support.FindBy;

public interface SearchForm extends WebModule {
    SearchForm searchFor(String phrase);
}
