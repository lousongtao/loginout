package com.zheng.upms.server;

import com.zheng.upms.dao.model.McSchedulePlan;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DateValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return McSchedulePlan.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof McSchedulePlan){
            McSchedulePlan msPlan = (McSchedulePlan)o;
            ValidationUtils.rejectIfEmpty(errors, "schedulingDate", "", "Scheduling date is null.");
        }
    }
}
