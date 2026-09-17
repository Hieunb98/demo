/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package com.practiceCRM.utils;

import com.practiceCRM.helpers.SystemHelpers;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * To construct the map by the reading the config values from JSON. Not used in this framework but can be leveraged
 * instead of property file based on the requirements
 */
public class JsonUtils {

    private static BufferedReader bufferedReader;
    private static StringBuffer stringBuffer;
    private static DocumentContext jsonContext;
    private static String lines;

    private JsonUtils() {
        super();
    }

    public static StringBuffer readJsonFile(String jsonPath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(SystemHelpers.getCurrentDir() + jsonPath));
            stringBuffer = new StringBuffer();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public static void setJsonFile(String jsonPath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(SystemHelpers.getCurrentDir() + jsonPath));
            stringBuffer = new StringBuffer();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
            jsonContext = JsonPath.parse(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getJsonDataSourceString() {
        return stringBuffer != null ? stringBuffer.toString() : "";
    }

    public static Object getData(String key) {
        if (jsonContext == null) {
            return null;
        }
        return jsonContext.read(key);
    }

    public static Object getData(String jsonPath, String key) {
        setJsonFile(jsonPath);
        return getData(key);
    }
}
