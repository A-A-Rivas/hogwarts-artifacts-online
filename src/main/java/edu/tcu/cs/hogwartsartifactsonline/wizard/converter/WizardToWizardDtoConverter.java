package edu.tcu.cs.hogwartsartifactsonline.wizard.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.hogwartsartifactsonline.wizard.dto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;

@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {

    @Override
    public WizardDto convert(Wizard source) {
        WizardDto wizardDto = new WizardDto(source.getId(), 
                                            source.getName(), 
                                            source.getNumberOfArtifacts());
        return wizardDto;
    }

}
