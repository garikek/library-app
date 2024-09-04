package com.example.BookService.utility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IsbnValidator {
    private static final String ISBN_10_PATTERN ="^(?:ISBN(?:-10)?:? )?(?=[-0-9X ]{13}$|[0-9X]{10}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private static final String ISBN_13_PATTERN ="^(?:ISBN(?:-1[03])?:? )?(?=[-0-9 ]{17}|[0-9X]{10}$|97[89][-0-9 ]{13}$)97[89][-0-9 ]{1,5}[- ]?[0-9 ]{1,7}[- ]?[0-9 ]{1,6}[- ]?[0-9X]$";

    public static boolean isValidIsbn(String isbn) {
        if (isbn == null) {
            return false;
        }

        boolean matchesIsbn10 = isbn.matches(ISBN_10_PATTERN);
        boolean matchesIsbn13 = isbn.matches(ISBN_13_PATTERN);

        return matchesIsbn10 || matchesIsbn13;
    }
}
