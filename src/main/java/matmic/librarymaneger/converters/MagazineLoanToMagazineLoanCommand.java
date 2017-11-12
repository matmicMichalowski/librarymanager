package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.MagazineLoanCommand;
import matmic.librarymaneger.model.MagazineLoan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MagazineLoanToMagazineLoanCommand implements Converter<MagazineLoan, MagazineLoanCommand>{

    private final MagazineToMagazineCommand magazineConverter;


    public MagazineLoanToMagazineLoanCommand(MagazineToMagazineCommand magazineConverter) {
        this.magazineConverter = magazineConverter;

    }

    @Synchronized
    @Nullable
    @Override
    public MagazineLoanCommand convert(MagazineLoan magazineLoan) {
        if(magazineLoan == null) {
            return null;
        }

        final MagazineLoanCommand magazineLoanCommand = new MagazineLoanCommand();

        magazineLoanCommand.setId(magazineLoan.getId());
        magazineLoanCommand.setLoanDate(magazineLoan.getLoanDate());
        magazineLoanCommand.setMagazine(magazineConverter.convert(magazineLoan.getMagazine()));

        return magazineLoanCommand;
    }
}
