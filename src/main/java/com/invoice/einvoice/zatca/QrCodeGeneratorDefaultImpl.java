package com.invoice.einvoice.zatca;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.payneteasy.tlv.BerTag;
import com.payneteasy.tlv.BerTlvBuilder;

public class QrCodeGeneratorDefaultImpl implements QrCodeGenerator {

   @Override
   public String generateQrCode(List<TLVMessage> msgs) throws Exception {
      BerTlvBuilder builder = new BerTlvBuilder();
      msgs.forEach((msg) -> {
         if (msg.tagName.equals("signature")) {
            try {
               byte[] r = ECDSAUtil.extractR(msg.getValue());
               System.out.println("r: " + r);
               byte[] s = ECDSAUtil.extractS(msg.getValue());
               System.out.println("s: " + s);
               builder.addBytes(new BerTag(msg.tag), r);
               builder.addBytes(new BerTag(msg.tag + 1), s);
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         } else {
            builder.addText(new BerTag(msg.tag), msg.value, StandardCharsets.UTF_8);
         }

      });
      return Base64.getEncoder().encodeToString(builder.buildArray());
   }
}
