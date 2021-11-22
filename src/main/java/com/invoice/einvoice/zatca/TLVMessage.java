package com.invoice.einvoice.zatca;

public class TLVMessage {
   int tag;
   String tagName;
   String value;

   public int getTag() {
      return this.tag;
   }

   public void setTag(int tag) {
      this.tag = tag;
   }

   public String getTagName() {
      return this.tagName;
   }

   public void setTagName(String tagName) {
      this.tagName = tagName;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public TLVMessage(int tag, String tagName, String value) {
      this.tag = tag;
      this.tagName = tagName;
      this.value = value;
   }

   public TLVMessage() {
   }
}
