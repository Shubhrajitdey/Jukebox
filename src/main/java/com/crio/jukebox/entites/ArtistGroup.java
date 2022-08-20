package com.crio.jukebox.entites;

public class ArtistGroup extends BaseEntity{
    private final String name;

    public ArtistGroup() {
        this.name = "";
    }

    public ArtistGroup(String id,String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArtistGroup other = (ArtistGroup) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ArtistGroup [id=" + id +"name=" + name + "]";
    }
    
    
}
