package com.beiming.demo;

import com.zx.sms.BaseMessage;
import com.zx.sms.codec.cmpp.msg.CmppSubmitRequestMessage;
import com.zx.sms.codec.cmpp.wap.LongMessageFrame;
import com.zx.sms.common.util.CMPPCommonUtil;
import org.marre.sms.SmsException;
import org.marre.sms.SmsMessage;
import org.marre.sms.SmsPdu;
import org.marre.sms.SmsUdhElement;
import org.marre.sms.SmsUdhIei;
import org.marre.sms.SmsUserData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Client
 */
public class Client {
    private final static Pattern pattern = Pattern.compile("^【(.*?)】");

    public static void main(String[] args) throws Exception {
        String str = "【长一路】一二三四五六七八九1一二三四五六七八九2一二三四五六七八九3一二三四五六七八九4一二三四五六七八九1一二三四五六七八九2一二三四五六七八九3一二三四五六七八九4一二三四五六七八九1一二三四五六七八九2一二三四五六七八九3一二三四五六七八九4一二三四五六七八九1一二三四五六七八九2一二三四五六七八九3一二三四五六七八九4";
//        String str = "【长一路】一二三四五六七八九1一二三四五六七八九2一二三四五六七八九3一二三四五六七八九4";
        CmppSubmitRequestMessage msg = new CmppSubmitRequestMessage();
        msg.setMsgContent(str);
        SmsMessage smsMessage = msg.getSmsMessage();

        List<LongMessageFrame> frameList = splitmsgcontent(smsMessage);

        //保证同一条长短信，通过同一个tcp连接发送
        List<BaseMessage> msgs = new ArrayList<BaseMessage>();
        for (LongMessageFrame frame : frameList) {

            BaseMessage basemsg = (BaseMessage) msg.generateMessage(frame);
            msgs.add(basemsg);
        }

        System.out.println(msgs);
    }


    public static List<LongMessageFrame> splitmsgcontent(SmsMessage content) throws SmsException {

        List<LongMessageFrame> result = new ArrayList<LongMessageFrame>();
        SmsPdu[] pdus = content.getPdus();
        int i = 1;
        for (SmsPdu aMsgPdu : pdus) {
            if (i == 1) {
                SmsUserData userData = aMsgPdu.getUserData();
                byte[] data = userData.getData();
                String newData = pattern.matcher(new String(data, CMPPCommonUtil.switchCharset(userData.getDcs().getAlphabet()))).replaceFirst("");
                aMsgPdu.setUserData(new SmsUserData(newData.getBytes(CMPPCommonUtil.switchCharset(userData.getDcs().getAlphabet())), userData.getDcs()));
            }
            byte[] udh = aMsgPdu.getUserDataHeaders();
            LongMessageFrame frame = new LongMessageFrame();

            SmsUdhElement[] udhe = aMsgPdu.getUdhElements_();
            short pkseq = 1;
            byte pktot = 1;
            byte pknum = 1;
            if (udhe != null && udhe.length > 0) {
                SmsUdhElement firstudh = udhe[0];

                if (SmsUdhIei.CONCATENATED_8BIT.equals(firstudh.getUdhIei_())) {
                    byte[] udhdata = firstudh.getUdhIeiData();
                    pkseq = (short) (udhdata[0] & 0xff);
                    pktot = udhdata[1];
                    pknum = udhdata[2];
                } else if (SmsUdhIei.CONCATENATED_16BIT.equals(firstudh.getUdhIei_())) {
                    byte[] udhdata = firstudh.getUdhIeiData();
                    pkseq = (short) ((((udhdata[0] & 0xff) << 8) | (udhdata[1] & 0xff)) & 0xffff);
                    pktot = udhdata[2];
                    pknum = udhdata[3];
                }
            }

            frame.setPkseq(pkseq);
            frame.setPktotal(pktot);
            frame.setPknumber(pknum);
            frame.setMsgfmt(aMsgPdu.getDcs());

            frame.setTpudhi(udh != null ? (short) 1 : (short) 0);

            ByteArrayOutputStream btos = new ByteArrayOutputStream(200);
            frame.setMsgLength((short) encodeOctetPdu(aMsgPdu, btos));
            byte[] byteArray = btos.toByteArray();
            frame.setMsgContentBytes(byteArray);
            result.add(frame);
            i++;
        }

        return result;
    }

    private static int encodeOctetPdu(SmsPdu pdu, OutputStream baos) throws SmsException {
        SmsUserData userData = pdu.getUserData();
        byte[] ud = userData.getData();
        byte[] udh = pdu.getUserDataHeaders();
        int length = 0;
        try {
            int nUdBytes = userData.getLength();
            int nUdhBytes = (udh == null) ? 0 : udh.length;

            // 1 octet/ 7 octets
            // TP-VP - Optional

            // UDH?
            if (nUdhBytes == 0) {
                // 1 Integer
                // TP-UDL
                // UDL includes the length of UDH
                length = nUdBytes;

                // n octets
                // TP-UD
                baos.write(ud);
            } else {

                // TP-UDL includes the length of UDH
                // +1 is for the size header...
                length = nUdBytes + nUdhBytes;
                // TP-UDH (including user data header length)
                baos.write(udh);
                // TP-UD
                baos.write(ud);

            }
            baos.close();
        } catch (IOException ex) {
            throw new SmsException(ex);
        }

        return length;
    }

}
