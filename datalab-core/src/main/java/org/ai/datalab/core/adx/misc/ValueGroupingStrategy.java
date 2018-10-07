/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.misc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mohan Purushothaman
 */
public enum ValueGroupingStrategy {

    SINGLE {

        @Override
        public Object group(Object[] obj) {
            if (obj == null || obj.length == 0) {
                return null;
            }
            if (obj.length == 1) {
                return obj[0];
            }

            throw new RuntimeException("Expected " + name() + " found many");
        }
    },
    FIRST_OCCURENCE {
        @Override
        public Object group(Object[] obj) {
            if (obj == null || obj.length == 0) {
                return null;
            }
            return obj[0];
        }
    },
    DISTINCT_SINGLE {
        @Override
        public Object group(Object[] obj) {
            if (obj == null || obj.length == 0) {
                return null;
            }
            if (obj.length == 1) {
                return obj[0];
            }

            Set<Object> set = new HashSet<>(Arrays.asList(obj));
            Object[] arr = set.toArray();
            if (arr.length == 1) {
                return arr[0];
            }
         
            throw new RuntimeException("Expected only one distinct value in " + name() + " but found many " + set);
        }
    },
    // Array
    DISTINCT_ARRAY {
        @Override
        public Object[] group(Object[] obj) {
            if (obj == null) {
                return null;
            }
            if (obj.length <= 1) {
                return obj;
            }
            Set<Object> set = new HashSet<>(Arrays.asList(obj));
            return set.toArray();

        }
    },
    NONE {
        @Override
        public Object[] group(Object[] obj) {
            return obj;
        }
    };

    public abstract Object group(Object[] obj);

}
