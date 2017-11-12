package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.MagazineCommand;
import matmic.librarymaneger.model.Magazine;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MagazineToMagazineCommand implements Converter<Magazine, MagazineCommand>{



    @Synchronized
    @Nullable
    @Override
    public MagazineCommand convert(Magazine magazine) {
        if(magazine == null) {
            return null;
        }

        final MagazineCommand magazineCommand = new MagazineCommand();

        magazineCommand.setId(magazine.getId());
        magazineCommand.setGenre(magazine.getGenre());
        magazineCommand.setIssn(magazine.getIssn());
        magazineCommand.setMagazineNumber(magazine.getMagazineNumber());
        magazineCommand.setPublisher(magazine.getPublisher());
        magazineCommand.setTitle(magazine.getTitle());
        magazineCommand.setYear(magazine.getYear());
        magazineCommand.setIsAvailable(magazine.getIsAvailable());

        return magazineCommand;
    }
}
