package com.rmm.api.device;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

@Builder
@Data
public class DeviceRequest {

    private Long operatingSystemId;

    private String type;

    void mergeEditableProperties(Device device) {
        BeanUtils.copyProperties(this, device, getNullPropertyNames(this));
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        String[] nullPropertyNames = Arrays.stream(beanWrapper.getPropertyDescriptors())
                .filter(property -> beanWrapper.getPropertyValue(property.getName()) == null)
                .map(PropertyDescriptor::getName)
                .toArray(String[]::new);
        return nullPropertyNames;
    }
}
