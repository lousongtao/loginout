package com.zheng.common.validator;

import java.math.BigDecimal;

import com.zheng.common.util.Money;
import org.apache.commons.lang.StringUtils;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * Created by zw on 2018/7/3
 * 钱的校验
 */
public class MoneyValidator extends ValidatorHandler<String> implements Validator<String> {

    private String fieldName;

    public MoneyValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String value) {
        try {
            if (StringUtils.isNotEmpty(value)) {
                new Money(value).compareTo(new Money(0));
                return true;
            }
        } catch (Exception e) {

        }
        context.addError(ValidationError.create(String.format("%s传入值不符合规则！", fieldName))
            .setErrorCode(-1).setField(fieldName).setInvalidValue(value));
        return false;
    }

    public static void main(String[] args) {
        Money money = new Money("33.25");
        String dump = money.dump();
        money.compareTo(new Money(0));
        System.out.println(dump);
        long cent = money.getCent();
        System.out.println(cent);
        if (StringUtils.isNotEmpty("dsa ")) {
            new Money("dsa").compareTo(new Money(0));
            System.out.println(1);
        }
    }
}
