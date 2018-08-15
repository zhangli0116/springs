package com.frame.springmvc.bean;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.Converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Administrator on 2018/8/15
 *
 * @author admin
 */
public class UserDateDeserialize extends JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(var1.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }
}
