package com.homework.HomeWork26.services;

import com.homework.HomeWork26.exception.InvalidFirstNameException;
import com.homework.HomeWork26.exception.InvalidLastNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    public String checkFirstName(String firstName) {
        if (StringUtils.isAlpha(firstName)) {
            return StringUtils.capitalize(firstName.toLowerCase());
        }
        throw new InvalidFirstNameException();
    }

    public String checkLastName(String lastName) {
        String[] lastNames = lastName.split("-");
        for (int i = 0; i < lastNames.length; i++) {
            if (StringUtils.isAlpha(lastNames[i])) {
                lastNames[i] = StringUtils.capitalize(lastNames[i].toLowerCase());
            } else {
                throw new InvalidLastNameException();
            }
        }
        return String.join("-", lastNames);
    }
}