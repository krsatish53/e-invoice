package com.invoice.einvoice.zatca;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class QrCodeEncoder {

   public static byte[] encode(String... parameters) throws Exception {
      int length = getBufferLength(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4], parameters[5], parameters[6], parameters[7]);
      byte[] buffer = new byte[length];
      int i = 0;

      for(int j = 0; i < length; ++j) {
         buffer[i] = (byte)(j + 1);
         int len = parameters[j].getBytes().length;
         buffer[i + 1] = (byte)len;
         System.arraycopy(parameters[j].getBytes(StandardCharsets.UTF_8), 0, buffer, i + 2, len);
         i += 2 + len;
      }

      return buffer;
   }

   public static byte[] encode(List<TLVMessage> msgs) throws Exception {
      int length = getBufferLength(msgs);
      byte[] buffer = new byte[length];
      int i = 0;
      int j = 0;

      while(j < msgs.size()) {
         if (((TLVMessage)msgs.get(j)).getTagName().equals("signature")) {
            byte[] r = ECDSAUtil.extractR(((TLVMessage)msgs.get(j)).getValue());
            byte[] s = ECDSAUtil.extractS(((TLVMessage)msgs.get(j)).getValue());
            buffer[i] = (byte)((TLVMessage)msgs.get(j)).getTag();
            buffer[i + 1] = (byte)r.length;
            System.arraycopy(r, 0, buffer, i + 2, r.length);
            i += 2 + r.length;
            buffer[i] = (byte)(((TLVMessage)msgs.get(j)).getTag() + 1);
            buffer[i + 1] = (byte)s.length;
            System.arraycopy(s, 0, buffer, i + 2, s.length);
            i += 2 + s.length;
            ++j;
         } else {
            buffer[i] = (byte)((TLVMessage)msgs.get(j)).getTag();
            int len = ((TLVMessage)msgs.get(j)).getValue().getBytes().length;
            buffer[i + 1] = (byte)len;
            System.arraycopy(((TLVMessage)msgs.get(j)).getValue().getBytes(StandardCharsets.UTF_8), 0, buffer, i + 2, len);
            i += 2 + len;
            ++j;
         }
      }

      return buffer;
   }

   private static int getBufferLength(String sellerName, String vatRegistrationNumber, String timeStamp, String invoiceTotal, String vatTotal, String hashedXml, String key, String signature) throws Exception {
      int sellerNameLength = sellerName.getBytes().length;
      int vatRegistrationNumberLength = vatRegistrationNumber.getBytes().length;
      int timeStampLength = timeStamp.getBytes().length;
      int invoiceTotalLength = invoiceTotal.getBytes().length;
      int vatTotalLength = vatTotal.getBytes().length;
      int hashedXmlLength = hashedXml.getBytes().length;
      int keyLength = key.getBytes().length;
      int rLength = ECDSAUtil.extractR(signature).length;
      int sLength = ECDSAUtil.extractS(signature).length;
      int totalLenth = sellerNameLength + vatRegistrationNumberLength + timeStampLength + invoiceTotalLength + vatTotalLength + hashedXmlLength + keyLength + rLength + sLength + 18;
      return totalLenth;
   }

   private static int getBufferLength(List<TLVMessage> msgs) throws Exception {
      int valuesLenth = (Integer)msgs.stream().filter((msg) -> {
         return !msg.getTagName().equals("signature");
      }).map((msg) -> {
         return msg.getValue().getBytes().length;
      }).reduce(0, Integer::sum);
      int totalLenth = valuesLenth + msgs.size() * 2 + 64 + 2;
      return totalLenth;
   }
}
