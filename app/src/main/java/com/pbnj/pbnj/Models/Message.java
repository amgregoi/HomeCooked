package com.pbnj.pbnj.Models;

/**
 * Created by amgregoi on 7/21/18.
 */

public class Message
{
    public String senderName;
    public String message;

    public Message(String sender, String msg)
    {
        if (sender == null)
        {
            senderName = "Guest";
        }
        else
        {
            senderName = sender;
        }
        message = msg;
    }
}
