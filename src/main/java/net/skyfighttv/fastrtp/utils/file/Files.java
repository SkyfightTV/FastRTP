package net.skyfighttv.fastrtp.utils.file;

public enum Files {
    Config("config"),
    Lang("lang");

    private final String name;

    Files(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
