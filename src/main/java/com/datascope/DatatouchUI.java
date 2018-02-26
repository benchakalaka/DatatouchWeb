package com.datascope;

import com.datascope.components.ui.LoginView;
import com.datascope.components.ui.MenuView;
import com.datascope.test.components.*;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@Push
@SpringUI
@PushStateNavigation
@Title("Datatouch")
@StyleSheet("valo-theme-ui.css")
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
public class DatatouchUI extends UI{

    private LoginView loginView;
    private MenuView menuView;
    private boolean testMode = false;

    private ValoMenuLayout root = new ValoMenuLayout();
    private CssLayout menu = new CssLayout();
    private CssLayout menuItemsLayout = new CssLayout();
    private LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();
    private Navigator navigator;
    private ComponentContainer viewDisplay = root.getContentContainer();

    private static LinkedHashMap<String, String> themeVariants = new LinkedHashMap<>();

    static {
        themeVariants.put(ValoTheme.THEME_NAME, "Valo");
        themeVariants.put("midsummer-night", "midsummer-night");
        themeVariants.put("tests-valo-blueprint", "tests-valo-blueprint");
        themeVariants.put("tests-valo-dark", "tests-valo-dark");
        themeVariants.put("tests-valo-facebook", "tests-valo-facebook");
        themeVariants.put("tests-valo-flatdark", "tests-valo-flatdark");
        themeVariants.put("tests-valo-flat", "tests-valo-flat");
        themeVariants.put("tests-valo-light", "tests-valo-light");
        themeVariants.put("tests-valo-metro", "tests-valo-metro");
        themeVariants.put("tests-valo-reindeer", "tests-valo-reindeer");
    }

    @Autowired
    SpringViewProvider viewProvider;

    public DatatouchUI(LoginView loginView, MenuView menuView)
    {
        this.loginView = loginView;
        this.menuView = menuView;
    }

    @Override
    protected void init(VaadinRequest request) {
        //initNavigator();
        //setContent(loginView);

        if (request.getParameter("test") != null) {
            testMode = true;

            if (browserCantRenderFontsConsistently()) {
                getPage().getStyles().add(
                        ".v-app.v-app.v-app {font-family: Sans-Serif;}");
            }
        }

        if (getPage().getWebBrowser().isIE()
                && getPage().getWebBrowser().getBrowserMajorVersion() == 9) {
            menu.setWidth("320px");
        }

        if (!testMode) {
            Responsive.makeResponsive(this);
        }

        getPage().setTitle("Valo Theme Test");
        setContent(root);
        root.setWidth("100%");

        root.addMenu(buildMenu());
        addStyleName(ValoTheme.UI_WITH_MENU);

        navigator = new Navigator(this, viewDisplay);

        navigator.addView("common", CommonParts.class);
        navigator.addView("labels", Labels.class);
        navigator.addView("buttons-and-links", ButtonsAndLinks.class);
        navigator.addView("textfields", TextFields.class);
        navigator.addView("datefields", DateFields.class);
        navigator.addView("comboboxes", ComboBoxes.class);
        navigator.addView("checkboxes", CheckBoxes.class);
        navigator.addView("sliders", Sliders.class);
        navigator.addView("menubars", MenuBars.class);
        navigator.addView("panels", Panels.class);
        navigator.addView("trees", Trees.class);
      //  navigator.addView("tables", Tables.class);
        navigator.addView("splitpanels", SplitPanels.class);
        navigator.addView("tabs", Tabsheets.class);
        navigator.addView("accordions", Accordions.class);
        navigator.addView("colorpickers", ColorPickers.class);
        navigator.addView("selects", NativeSelects.class);
       // navigator.addView("calendar", CalendarTest.class);
        navigator.addView("forms", Forms.class);
        navigator.addView("popupviews", PopupViews.class);
        navigator.addView("dragging", Dragging.class);

        String f = Page.getCurrent().getUriFragment();

        if (f == null || f.equals("")) {
            navigator.navigateTo("common");
        }

        navigator.setErrorView(CommonParts.class);

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                for (Iterator<Component> it = menuItemsLayout.iterator(); it
                        .hasNext();) {
                    it.next().removeStyleName("selected");
                }
                for (Map.Entry<String, String> item : menuItems.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (Iterator<Component> it = menuItemsLayout
                                .iterator(); it.hasNext();) {
                            Component c = it.next();
                            if (c.getCaption() != null
                                    && c.getCaption().startsWith(
                                    item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                menu.removeStyleName("valo-menu-visible");
            }
        });



        //navigator.navigateTo(MenuView.NAME);
    }

    private boolean browserCantRenderFontsConsistently() {
        // PhantomJS renders font correctly about 50% of the time, so
        // disable it to have consistent screenshots
        // https://github.com/ariya/phantomjs/issues/10592

        // IE8 also has randomness in its font rendering...

        return getPage().getWebBrowser().getBrowserApplication()
                .contains("PhantomJS")
                || (getPage().getWebBrowser().isIE() && getPage()
                .getWebBrowser().getBrowserMajorVersion() <= 9);
    }

    private CssLayout buildMenu() {
        // Add items
        menuItems.put("common", "Common UI Elements");
        menuItems.put("labels", "Labels");
        menuItems.put("buttons-and-links", "Buttons & Links");
        menuItems.put("textfields", "Text Fields");
        menuItems.put("datefields", "Date Fields");
        menuItems.put("comboboxes", "Combo Boxes");
        menuItems.put("selects", "Selects");
        menuItems.put("checkboxes", "Check Boxes & Option Groups");
        menuItems.put("sliders", "Sliders & Progress Bars");
        menuItems.put("colorpickers", "Color Pickers");
        menuItems.put("menubars", "Menu Bars");
        menuItems.put("trees", "Trees");
        menuItems.put("tables", "Tables & Grids");
        menuItems.put("dragging", "Drag and Drop");
        menuItems.put("panels", "Panels");
        menuItems.put("splitpanels", "Split Panels");
        menuItems.put("tabs", "Tabs");
        menuItems.put("accordions", "Accordions");
        menuItems.put("popupviews", "Popup Views");
        if (getPage().getBrowserWindowWidth() >= 768) {
            menuItems.put("calendar", "Calendar");
        }
        menuItems.put("forms", "Forms");

        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        menu.addComponent(top);
        menu.addComponent(createThemeSelect());

        Button showMenu = new Button("Menu", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (menu.getStyleName().contains("valo-menu-visible")) {
                    menu.removeStyleName("valo-menu-visible");
                } else {
                    menu.addStyleName("valo-menu-visible");
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(FontAwesome.LIST);
        menu.addComponent(showMenu);

        Label title = new Label("<h3>Vaadin <strong>Valo Theme</strong></h3>",
                ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);

        MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        StringGenerator sg = new StringGenerator();
        MenuBar.MenuItem settingsItem = settings.addItem(sg.nextString(true)
                        + " " + sg.nextString(true) + sg.nextString(false),
                new ClassResource("profile-pic-300px.jpg"),
                null);
        settingsItem.addItem("Edit Profile", null);
        settingsItem.addItem("Preferences", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", null);
        menu.addComponent(settings);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);

        Label label = null;
        int count = -1;
        for (final Map.Entry<String, String> item : menuItems.entrySet()) {
            if (item.getKey().equals("labels")) {
                label = new Label("Components", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            if (item.getKey().equals("panels")) {
                label.setValue(label.getValue()
                        + " <span class=\"valo-menu-badge\">" + count
                        + "</span>");
                count = 0;
                label = new Label("Containers", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            if (item.getKey().equals("calendar")) {
                label.setValue(label.getValue()
                        + " <span class=\"valo-menu-badge\">" + count
                        + "</span>");
                count = 0;
                label = new Label("Other", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            Button b = new Button(item.getValue(), (Button.ClickListener) event -> navigator.navigateTo(item.getKey()));
            if (count == 2) {
                b.setCaption(b.getCaption()
                        + " <span class=\"valo-menu-badge\">123</span>");
            }
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName(ValoTheme.MENU_ITEM);
           // b.setIcon(testIcon.get());
            menuItemsLayout.addComponent(b);
            count++;
        }
        label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
                + count + "</span>");

        return menu;
    }

    private Component createThemeSelect() {
        NativeSelect<String> ns = new NativeSelect<>();
        ns.setId("themeSelect");
        List<String> content = new ArrayList<>();
        for (String identifier : themeVariants.keySet()) {
            content.add(themeVariants.get(identifier));
        }
        ns.setItems(content);

        ns.setValue(ValoTheme.THEME_NAME);
        ns.addValueChangeListener((HasValue.ValueChangeListener) event -> setTheme(ns.getValue()));
        return ns;
    }
}
