package io.github.dunwu.spring.core.binding;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;

public class BeanWrapperWithError {

    public static void main(String[] args) {
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("num", "10x"); // 10x cannot be converted to int type

        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValues(mpv);
        System.out.println(bw.getWrappedInstance());
    }

}
