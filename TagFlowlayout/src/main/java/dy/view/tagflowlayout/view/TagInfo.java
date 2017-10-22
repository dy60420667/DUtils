package dy.view.tagflowlayout.view;

public class TagInfo {
    private String id;
    private String text;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TagInfo(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
