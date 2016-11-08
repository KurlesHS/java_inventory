package com.hs.inventory;

import java.util.Collection;
import java.util.Iterator;


class StringHelpers {
    /* удивлён, что данной фичи "из коробки" нет до java 8 */
    static String join(String str, Collection<String> strings) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = strings.iterator();
        boolean first = true;
        while (iterator.hasNext()) {
            if (!first) {
                builder.append(str);
            } else {
                first = false;
            }
            builder.append(iterator.next());
        }
        return builder.toString();
    }

    static boolean toBool(String str) throws ClassCastException {
        switch (str) {
            case "false":
                return false;
            case "true":
                return true;
            default:
                throw new ClassCastException();
        }
    }

    static String getSuffixForNumber(int number, String one, String twoThreeFour, String other) {
        String suffix = other;
        /* x10 .. x19 - штук / дюймов ... */
        if (number % 100 < 10 || number % 100 > 20) {
            switch (number % 10) {
                case 1: /* штука, дюйм ... */
                    suffix = one;
                    break;
                case 2: /* штуки, дюйма */
                case 3:
                case 4:
                    suffix = twoThreeFour;
                    break;
                default:
                    break;
            }
        }
        return suffix;
    }
}
