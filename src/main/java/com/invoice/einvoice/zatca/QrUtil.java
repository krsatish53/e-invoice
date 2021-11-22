package com.invoice.einvoice.zatca;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by satish.
 */

public class QrUtil {

    public static String generateQrCode(String name, String vat, String date, BigDecimal total, BigDecimal tax) {
        List<TLVMessage> msgs = new ArrayList<>();
        msgs.add(new TLVMessage(1, "sellerName", name));
        msgs.add(new TLVMessage(2, "vatRegistrationNumber", vat));
        msgs.add(new TLVMessage(3, "timeStamp", date));
        msgs.add(new TLVMessage(4, "invoiceTotal", total.toString()));
        msgs.add(new TLVMessage(5, "vatTotal", tax.toString()));
        //todo next phase
        //msgs.add(new TLVMessage(6, "hashedXml", hashedXml));
        //msgs.add(new TLVMessage(7, "key", key));
        //msgs.add(new TLVMessage(8, "signature", signature));
        QrCodeGenerator generator = new QrCodeGeneratorBasicImpl();
        try {
            return generator.generateQrCode(msgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String qrCode = QrUtil.generateQrCode("Jeddah Tower", "31011111311113", LocalDateTime.now().toString(), BigDecimal.valueOf(115), BigDecimal.valueOf(15));
        System.out.println("Qr code: " + qrCode);
    }

}
