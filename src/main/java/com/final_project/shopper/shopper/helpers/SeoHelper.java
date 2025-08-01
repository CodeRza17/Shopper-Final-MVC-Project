package com.final_project.shopper.shopper.helpers;

import java.util.List;

public class SeoHelper {

    public static String createSeoUrl(String title){
        String result = title.toLowerCase()
                .replace("ç", "c")
                .replace("ə", "e")
                .replace("ğ", "g")
                .replace("ı", "i")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ü", "u")
                .replace(" ","-");
        return result;
    }

    public static List<String> createKeywords(){
        return null;
    }
}
