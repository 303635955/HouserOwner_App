package com.yunguo.broadcast;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver{
	
    private static MessageListener mMessageListener;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	public SMSBroadcastReceiver() {
        super();
    }

	@Override
	public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
	            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
	            for(Object pdu:pdus) {
	                @SuppressWarnings("deprecation")
					SmsMessage smsMessage = SmsMessage.createFromPdu((byte [])pdu);
	                String sender = smsMessage.getDisplayOriginatingAddress();
	                //短信内容
	                String content = smsMessage.getDisplayMessageBody();
	                content = content.substring(content.indexOf(":"), content.length());
	                String code = "";
	                for(int i = 1;i <= 4; i++){
	                	code+=content.charAt(i);
	                }
	                long date = smsMessage.getTimestampMillis();
	                Date tiemDate = new Date(date);
	                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                String time = simpleDateFormat.format(tiemDate);

	                //过滤不需要读取的短信的发送号码
	                if ("10690583014123048".equals(sender)) {
	                    mMessageListener.onReceived(code);
	                    abortBroadcast();
	                }
	            }
	        }
	    
	}
	
	//回调接口
    public interface MessageListener {
        public void onReceived(String message);
    }
    
    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.mMessageListener = messageListener;
    }
}
