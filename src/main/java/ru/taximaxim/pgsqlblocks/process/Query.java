/*
 * ========================LICENSE_START=================================
 * pgSqlBlocks
 * *
 * Copyright (C) 2017 "Technology" LLC
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package ru.taximaxim.pgsqlblocks.process;

public class Query {
    private final String queryString;
    private final boolean slowQuery;
    private final String backendStart;
    private final String queryStart;
    private final String xactStart;

    Query(String queryString, String backendStart, String queryStart, String xactStart, boolean slowQuery) {
        this.queryString = queryString == null ? "" : queryString;
        this.backendStart = backendStart == null ? "" : backendStart;
        this.queryStart = queryStart == null ? "" : queryStart;
        this.xactStart = xactStart == null ? "" : xactStart;
        this.slowQuery = slowQuery;
    }

    public String getQueryString() {
        return queryString;
    }

    public boolean isSlowQuery() {
        return slowQuery;
    }

    public String getBackendStart() {
        return backendStart;
    }

    public String getQueryStart() {
        return queryStart;
    }

    public String getXactStart() {
        return xactStart;
    }

    @Override
    public String toString() {
        return "Query{" +
                "queryString='" + queryString + '\'' +
                ", slowQuery=" + slowQuery +
                ", backendStart='" + backendStart + '\'' +
                ", queryStart='" + queryStart + '\'' +
                ", xactStart='" + xactStart + '\'' +
                '}';
    }
}