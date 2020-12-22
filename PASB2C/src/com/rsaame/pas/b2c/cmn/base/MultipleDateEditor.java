/**
 * 
 */
package com.rsaame.pas.b2c.cmn.base;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

/**
 * This is to bind date attribute of page to/from object in spring MVC.
 * This is required as there can be dates in different formats
 * @author Sarath
 * @since Phase 3
 *
 */
public class MultipleDateEditor extends PropertyEditorSupport{

    public final static String DEFAULT_OUTPUT_FORMAT = "dd/MM/yyyy";

    public final static String[] DEFAULT_INPUT_FORMATS = {
    	"dd-MM-yyyy HH:mm:ss",
        "dd-MM-yy HH:mm:ss",
        "dd-MM-yy HH:mm:ss.SSS",
        "yyyy-MM-dd HH:mm:ss.SSS",
        "dd/MM/yyyy",
        "dd-MM-yyyy",
        "dd/MM/yy",
        "dd-MM-yy",
    };
    
    //"dd/MM/yyyy HH:mm:ss",
    // "MM/dd/yyyy HH:mm:ss",
    //"dd/MM/yy HH:mm:ss",
    // "MM/dd/yy HH:mm:ss",
    //"MM-dd-yyyy HH:mm:ss",
    //"MM-dd-yy HH:mm:ss",
    //"MM/dd/yyyy",
    //"MM-dd-yyyy",
    // "MM/dd/yy",
    //"MM-dd-yy"
    /** The format used to convert a Date into a String */
    private String outputFormat;
    /** An array of date formats used to convert a String into a Date */
    private String[] inputFormats;
    /** Allow empty strings to be parsed instead of treated as null */
    private boolean allowEmpty;

    public MultipleDateEditor() {
        outputFormat = MultipleDateEditor.DEFAULT_OUTPUT_FORMAT;
        inputFormats = MultipleDateEditor.DEFAULT_INPUT_FORMATS;
        allowEmpty = false;
    }

    /**
     * Create instance using the given date input and output formats.
     * @param outputFormat The format used to convert a Date into a String
     * @param inputFormats An array of date formats used to convert a String into a Date
     */
    public MultipleDateEditor(String outputFormat, String[] inputFormats) {
        this.outputFormat = outputFormat;
        this.inputFormats = inputFormats;
        allowEmpty = false;
    }

    /**
     * Create instance using the given date input and output formats.
     * @param outputFormat The format used to convert a Date into a String
     * @param inputFormats An array of date formats used to convert a String into a Date
     * @param allowEmpty Allow empty strings to be parsed instead of treated as null
     */
    public MultipleDateEditor(String outputFormat, String[] inputFormats,
            boolean allowEmpty) {
        this.outputFormat = outputFormat;
        this.inputFormats = inputFormats;
        this.allowEmpty = allowEmpty;
    }
    
    /**
     * Create instance using the given date output format and empty check.
     * @param outputFormat The format used to convert a Date into a String
     * @param allowEmpty Allow empty strings to be parsed instead of treated as null
     */
    public MultipleDateEditor(String outputFormat, boolean allowEmpty) {
        this.outputFormat = outputFormat;
        this.inputFormats = MultipleDateEditor.DEFAULT_INPUT_FORMATS;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Format the Date as String, using the specified outputFormat.
     * @return The string value of the Date
     */
    @Override
    public String getAsText() {
        if (allowEmpty && getValue() == null) {
            return "";
        }
        return DateFormatUtils.format((Date) getValue(), outputFormat);
    }

    /**
     * Parse the Date from the given text
     * @param text the text to convert into a java.util.Date
     * @throws IllegalArgumentException thrown if no matching format is found
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            if (StringUtils.hasText(text)) {
                setValue(DateUtils.parseDateStrictly( text, inputFormats ) );
            } else {
                if (allowEmpty) {
                    setValue(null);
                } else {
                    throw new IllegalArgumentException(
                            "The text specified for parsing is null");
                }
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse text ["
                    + text + "] into any available date input formats", ex);
        }
    }

    /**
     * @return whether empty strings are allowed
     */
    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    /**
     * @param allowEmpty whether empty strings should be allowed
     */
    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    /**
     * @return The date formats used to parse a String into a Date
     */
    public String[] getInputFormats() {
        return inputFormats;
    }

    /**
     * @param inputFormats The date formats used to parse a String into a Date
     */
    public void setInputFormats(String[] inputFormats) {
        this.inputFormats = inputFormats;
    }

    /**
     * @return The format used to convert a Date into a String
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    /**
     * @param outputFormat The format used to convert a Date into a String
     */
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }
}
