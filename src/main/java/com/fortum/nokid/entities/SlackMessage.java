package com.fortum.nokid.entities;

/**
 * Created by Nokid on 06/11/2016.
 */
public class SlackMessage {
    private String username;
    private String icon_emoji;
    private String text;

    public SlackMessage(String username, String icon_emoji, String text) {

        this.username = username;
        this.icon_emoji = icon_emoji;
        this.text = text;
    }

    public SlackMessage() {

    }

    public SlackMessage(String text) {
        this.username = "Server";
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon_emoji() {
        return icon_emoji;
    }

    public void setIcon_emoji(String icon_emoji) {
        this.icon_emoji = icon_emoji;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
