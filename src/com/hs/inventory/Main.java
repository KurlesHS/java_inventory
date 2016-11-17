package com.hs.inventory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

/* test cases:
add --type MONITOR --name "ViewSonic VA2223WM" --quantity 1 --date 01.11.2016 --color true --kind LCD
add --type MONITOR --sku 44 --date 11.21.2011 --name "monitor one" --kind LCD --size 22 --color true
 */

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf.xml");

        Collection collection = (Collection) context.getBean("creators");
        MainHandler handler = (MainHandler) context.getBean("mainHandler");
        for (Object creator : collection) {
            try {
                handler.addCreator((IRecordCreator)creator);
            } catch (ClassCastException e) {
                /* simply ignore wrong values */
            }
        }

        try {
            handler.process(args);
        } catch (InventoryParamsException e) {
            System.err.println(e.getMessage());
        }
    }
}
