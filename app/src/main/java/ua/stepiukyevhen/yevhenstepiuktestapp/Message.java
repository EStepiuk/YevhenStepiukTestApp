package ua.stepiukyevhen.yevhenstepiuktestapp;


public class Message {
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String author;
    private String text;

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
    }
}
