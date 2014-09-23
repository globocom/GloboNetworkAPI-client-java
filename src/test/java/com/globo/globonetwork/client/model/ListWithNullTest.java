package com.globo.globonetwork.client.model;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;
import com.google.api.client.xml.Xml;
import com.google.api.client.xml.XmlNamespaceDictionary;

public class ListWithNullTest {
    
    @Test
    public void testMarshallNullValue() {
        X x = new X();
        x.setList(null);
        assertEquals("<?xml version=\"1.0\"?><x xmlns=\"http://unknown/\"><tag><tag_per_element /></tag></x>", x.toString());
    }

    @Test
    public void testMarshall1Element() {
        X x = new X();
        x.setList(Arrays.asList(1));
        assertEquals("<?xml version=\"1.0\"?><x xmlns=\"http://unknown/\"><tag><tag_per_element>1</tag_per_element></tag></x>", x.toString());
    }

    @Test
    public void testMarshall2Elements() {
        X x = new X();
        x.setList(Arrays.asList(1, 5));
        assertEquals("<?xml version=\"1.0\"?><x xmlns=\"http://unknown/\"><tag><tag_per_element>1</tag_per_element><tag_per_element>5</tag_per_element></tag></x>", x.toString());
    }
    
    @Test
    public void testUnmarshallNullValue() {
        String xml = "<?xml version=\"1.0\"?><x xmlns=\"http://unknown/\"><tag><tag_per_element>1</tag_per_element><tag_per_element>5</tag_per_element></tag></x>";
        X x = read(xml);
        assertEquals(Arrays.asList(1, 5), x.getList());
    }

    private X read(String value) {
        try {
            Reader in = new StringReader(value);
            XmlPullParser xmlPullParser = Xml.createParser();
            xmlPullParser.setInput(in);
            X x = new X();
            Xml.parseElement(xmlPullParser, x, new XmlNamespaceDictionary(), null);
            return x;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static class X extends GenericXml {
        
        public X() {
            super.name = "x";
        }
        
        @Key("tag")
        private TagPerElement list = new TagPerElement();
        
        public void setList(List<Integer> list) {
            this.list.setValues(list);
        }
        
        public List<Integer> getList() {
            return this.list.getValues();
        }
        
        public static class TagPerElement extends ListWithNullTag<Integer> {
            public TagPerElement() {
                super("tag_per_element");
            }
        }
    }
    
}
