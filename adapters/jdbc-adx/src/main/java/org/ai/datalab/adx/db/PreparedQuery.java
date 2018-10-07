/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class PreparedQuery {

    private final List<String> varList;

    private final StrSubstitutor substitutor;

    private final String preparedQuery;

    public PreparedQuery(String query) {
        varList = new LinkedList<>();
        substitutor = new StrSubstitutor(new StrLookup() {
            @Override
            public String lookup(String key) {
                varList.add(key);
                return "?";
            }
        });
        preparedQuery = substitutor.replace(query);
    }

    public String getPreparedQuery() {
        return preparedQuery;
    }

    public void populateParams(PreparedStatement p, Data d) throws SQLException {
        int index = 1;
        for (String var : varList) {
            p.setObject(index++, d.getValue(var));
        }

    }

}
