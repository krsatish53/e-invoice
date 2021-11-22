package com.invoice.einvoice.zatca;

import java.util.Base64;
import java.util.List;

public class QrCodeGeneratorBasicImpl implements QrCodeGenerator {
   public String generateQrCode(List<TLVMessage> msgs) throws Exception {
      byte[] bytes = QrCodeEncoder.encode(msgs);
      return Base64.getEncoder().encodeToString(bytes);
   }
}
