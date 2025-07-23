package com.example.Youtube.Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumFeel {
   LIKE, DISLIKE;


   @JsonCreator
   public static EnumFeel from(String value){
      return EnumFeel.valueOf(value.toUpperCase());
   }
}
