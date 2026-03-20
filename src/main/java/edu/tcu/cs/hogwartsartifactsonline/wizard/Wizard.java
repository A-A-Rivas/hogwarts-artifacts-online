package edu.tcu.cs.hogwartsartifactsonline.wizard;

import java.io.Serializable;
import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List <Artifact> artifacts = new ArrayList<>();

    public Wizard() {}

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public List<Artifact> getArtifacts() {
        return this.artifacts;
    }

    public void setArtifacts(List <Artifact> newArtifacts) {
        this.artifacts = newArtifacts;
    }

    public void addArtifact(Artifact newArtifact) {
        newArtifact.setOwner(this);
        this.artifacts.add(newArtifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.artifacts.size();
    }

    public void removeAllArtifacts() {
        this.artifacts.stream().forEach(artifact -> artifact.setOwner(null));
        this.artifacts = null;
    }
}
