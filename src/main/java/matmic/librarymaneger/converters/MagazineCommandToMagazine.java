package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.MagazineCommand;
import matmic.librarymaneger.model.Magazine;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MagazineCommandToMagazine implements Converter<MagazineCommand, Magazine>{


    @Synchronized
    @Nullable
    @Override
    public Magazine convert(MagazineCommand magazineCommand) {
        if(magazineCommand == null) {
            return null;
        }

        final Magazine magazine = new Magazine();

        magazine.setId(magazineCommand.getId());
        magazine.setGenre(magazineCommand.getGenre());
        magazine.setIssn(magazineCommand.getIssn());
        magazine.setMagazineNumber(magazineCommand.getMagazineNumber());
        magazine.setPublisher(magazineCommand.getPublisher());
        magazine.setTitle(magazineCommand.getTitle());
        magazine.setYear(magazineCommand.getYear());
        magazine.setIsAvailable(magazineCommand.getIsAvailable());

        return magazine;
    }
}
