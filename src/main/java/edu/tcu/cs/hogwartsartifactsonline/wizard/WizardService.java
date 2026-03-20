package edu.tcu.cs.hogwartsartifactsonline.wizard;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import edu.tcu.cs.hogwartsartifactsonline.artifact.ArtifactRepository;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;

@Service
@Transactional
public class WizardService {

    private final WizardRepository wizardRepository;

    private final ArtifactRepository artifactRepository;

    public WizardService(WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }

    public List<Wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }

    public Wizard save(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId)
            .map(oldWizard -> {
                oldWizard.setName(update.getName());

                return this.wizardRepository.save(oldWizard);
            })
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // before delete, we will unassign this wizard's owned artifacts
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

    public void assignArtifact(Integer wizardId, String artifactId) {

        // find artifact by id from database
        Artifact artifactTobeAssigned = this.artifactRepository.findById(artifactId)
            .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));

        // find this wizard by id from database
        Wizard wizard = this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // artifact assignment
        // we need to see if the artifact is already owned by some wizard
        if (artifactTobeAssigned.getOwner() != null) {
            artifactTobeAssigned.getOwner().removeArtifact(artifactTobeAssigned);
        }

        wizard.addArtifact(artifactTobeAssigned);
    }

}
